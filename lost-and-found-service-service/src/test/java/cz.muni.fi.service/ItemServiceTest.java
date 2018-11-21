package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.ItemDao;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Tests for ItemService
 * @author Terezia Slaninakova (445526)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ItemServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private ItemDao itemDao;

    @Autowired
    @InjectMocks
    private ItemService itemService = new ItemServiceImpl();

    private Item itemWallet;
    private Item itemUmbrella;
    private Item itemJacket;

    private Long itemCounter = 10L;
    private Map<Long, Item> items = new HashMap<>();

    @BeforeClass
    public void beforeClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        // mocks
        when(itemDao.addItem(any(Item.class))).then(invoke -> {
            Item mockedItem = invoke.getArgumentAt(0, Item.class);
            if (mockedItem == null) {
                throw new IllegalArgumentException("Item can't be null");
            }
            long id = itemCounter++;
            mockedItem.setId(id);
            items.put(id, mockedItem);
            return mockedItem;
        });
        when(itemDao.updateItem(any(Item.class))).then(invoke -> {
            Item mockedItem = invoke.getArgumentAt(0, Item.class);
            if (mockedItem == null || mockedItem.getId() == null) {
                throw new IllegalArgumentException("Item or item's id can't be null");
            }
            items.replace(mockedItem.getId(), mockedItem);
            return mockedItem;
        });
        when(itemDao.deleteItem(any(Item.class))).then(invoke -> {
            Item mockedItem = invoke.getArgumentAt(0, Item.class);
            if (mockedItem == null || mockedItem.getId() == null) {
                throw new IllegalArgumentException("Item or item's id can't be null");
            }
            items.remove(mockedItem.getId(), mockedItem);
            return mockedItem;
        });
        when(itemDao.getAllItems()).then(invoke -> new ArrayList<>(items.values()));

        when(itemDao.getItembyId(anyLong())).then(invoke -> {
            Long index = invoke.getArgumentAt(0, Long.class);
            if (index == null) {
                throw new IllegalArgumentException("Item can't be null");
            }
            return items.get(index);
        });
    }

    @BeforeMethod
    public void beforeTest() {
        items.clear();
        itemWallet = new Item();
        itemWallet.setName("Leather wallet");
        itemWallet.setId(1L);
        itemUmbrella = new Item();
        itemUmbrella.setName("Red umbrella");
        itemUmbrella.setId(2L);
        itemJacket = new Item();
        itemJacket.setName("Winter black jacket");
        itemJacket.setId(3L);

        itemJacket.setStatus(Status.CLAIM_RECEIVED_LOST);
        itemUmbrella.setStatus(Status.CLAIM_RECEIVED_FOUND);
        itemWallet.setStatus(Status.RESOLVED);
        itemWallet.setFoundDate(LocalDate.now().minusDays(1));

        items.put(1L, itemWallet);
        items.put(2L, itemUmbrella);
        items.put(3L, itemJacket);
    }

    @Test
    public void testAddItem() {
        int originalSize = items.size();
        itemService.addItem(itemWallet);
        assertThat(items.values()).hasSize(originalSize + 1)
                .contains(itemWallet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullItem() {
        itemService.addItem(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNullItem() {
        itemService.updateItem(null);
    }

    @Test
    public void testGetAllItems()  {
        assertThat(itemService.getAllItems()).containsExactlyInAnyOrder(itemJacket, itemUmbrella, itemWallet);
    }

    @Test
    public void testGetItemById()  {
        assertThat(itemService.getItembyId(itemJacket.getId())).isEqualToComparingFieldByField(itemJacket);
    }

    @Test
    public void testDeleteItem() {
        int originalSize = items.size();
        itemService.deleteItem(itemWallet);
        assertThat(items.values()).hasSize(originalSize - 1)
                .doesNotContain(itemWallet);
    }

    @Test
    public void testUpdateItem() {
        String updatedName = "Updated jacket";
        itemJacket.setName(updatedName);
        Item updatedJacket = items.get(itemJacket.getId());
        itemService.updateItem(itemJacket);
        assertThat(updatedJacket.getName()).isEqualTo(updatedName);
        assertThat(updatedJacket).isEqualToComparingFieldByField(itemJacket);
    }

    // TODO: Add more tests (archive, negative, special cases)
}