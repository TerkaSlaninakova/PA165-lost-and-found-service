package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.ItemDao;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


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

    private Item itemWallet, itemUmbrella,itemJacket;
    private LocalDate lostDateMonthAgo, lostDateNow, foundDateDayAgo;
    private Location lostLocation, foundLocation;
    private User owner;

    @BeforeClass
    public void beforeClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void beforeTest() {
        lostDateMonthAgo = LocalDate.now().minusMonths(1);
        lostDateNow = LocalDate.now();
        foundDateDayAgo = LocalDate.now().minusDays(1);
        lostLocation = new Location();
        lostLocation.setDescription("at a house party");
        foundLocation = new Location();
        lostLocation.setDescription("near the train station");
        owner = new User();
        owner.setName("owner");

        itemWallet = new Item();
        itemWallet.setName("Leather wallet");
        itemWallet.setId(1L);
        itemWallet.setStatus(Status.CLAIM_RECEIVED_LOST);
        itemWallet.setOwner(owner);
        itemWallet.setLostDate(lostDateMonthAgo);
        itemWallet.setLostLocation(lostLocation);

        itemUmbrella = new Item();
        itemUmbrella.setName("Red umbrella");
        itemUmbrella.setId(2L);
        itemUmbrella.setStatus(Status.CLAIM_RECEIVED_FOUND);
        itemUmbrella.setFoundDate(foundDateDayAgo);
        itemUmbrella.setFoundLocation(foundLocation);

        itemJacket = new Item();
        itemJacket.setName("Black jacket");
        itemJacket.setId(3L);
        itemJacket.setStatus(Status.RESOLVED);
        itemJacket.setFoundDate(lostDateMonthAgo);
        itemJacket.setOwner(owner);
        itemJacket.setLostDate(lostDateMonthAgo);
        itemJacket.setLostLocation(lostLocation);
        itemJacket.setFoundLocation(foundLocation);
    }

    @Test
    public void testAddItem() {

        doAnswer(invocationOnMock -> {
            Item item = (Item) invocationOnMock.getArguments()[0];
            item.setId(1L);
            return null;
        }).when(itemDao).addItem(itemWallet);

        itemService.addItem(itemWallet);
        assertThat(itemWallet.getId() == 0L);
        verify(itemDao).addItem(itemWallet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullItem() {
        doThrow(new IllegalArgumentException()).when(itemDao).addItem(null);
        itemService.addItem(null);
    }

    @Test
    public void testUpdateItem() {
        doNothing().when(itemDao).updateItem(itemWallet);
        itemService.updateItem(itemWallet);
        verify(itemDao).updateItem(itemWallet);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNullItem() {
        doThrow(new IllegalArgumentException()).when(itemDao).updateItem(null);
        itemService.updateItem(null);
    }

    @Test
    public void testGetAllItems()  {
        when(itemDao.getAllItems()).thenReturn(Arrays.asList(itemUmbrella, itemWallet, itemJacket));
        assertThat(itemService.getAllItems()).containsExactlyInAnyOrder(itemUmbrella, itemWallet, itemJacket);
        verify(itemDao).getAllItems();
    }

    @Test
    public void testGetItemById()  {
        when(itemDao.getItembyId(1L)).thenReturn(itemWallet);
        assertThat(itemService.getItembyId(itemWallet.getId())).isEqualToComparingFieldByField(itemWallet);
        verify(itemDao).getItembyId(itemWallet.getId());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetItemByNullId() {
        doThrow(new IllegalArgumentException()).when(itemDao).getItembyId(null);
        itemService.getItembyId(null);
    }

    @Test
    public void testDeleteItem() {
        when(itemDao.getItembyId(itemUmbrella.getId())).thenReturn(itemUmbrella);
        doNothing().when(itemDao).deleteItem(itemUmbrella);
        itemService.deleteItem(itemUmbrella);
        verify(itemDao).deleteItem(itemUmbrella);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteItemByNull() {
        doThrow(new IllegalArgumentException()).when(itemDao).deleteItem(null);
        itemService.deleteItem(null);
    }

    @Test
    public void testArchiveItem() {
        itemService.archiveItem(itemJacket);
        String expectedArchive = "{ 'Item': {" +
                " 'name': '" + itemJacket.getName() +"'," +
                " 'characteristics': '" + itemJacket.getCharacteristics() + "'," +
                " 'photo': '" + itemJacket.getPhoto() + "'," +
                " 'type': '" + itemJacket.getType() + "'," +
                " 'foundDate': '" + itemJacket.getFoundDate().toString() + "'," +
                " 'id': '" + itemJacket.getId() + "'," +
                " 'categories' " + itemJacket.getCategories() + "'," +
                " 'lostLocation': '" + itemJacket.getLostLocation() + "'," +
                " 'foundLocation': '" + itemJacket.getFoundLocation() + "'," +
                " 'owner': '" + itemJacket.getOwner().getName() + "'," +
                " 'status': '" + itemJacket.getStatus() + "'," +
                "}}";

        String archive = itemJacket.getArchive();
        Assert.assertEquals(archive, expectedArchive);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Item cannot be null")
    public void testArchiveItemNullItem() {
        itemService.archiveItem(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "Item must be in resolved state")
    public void testArchiveItemNotResolved() {
        itemService.archiveItem(itemWallet);
    }

    @Test
    public void testResolveLostItem() {
        Item resolvedItem = itemService.resolveLostItem(itemWallet, foundDateDayAgo, foundLocation);
        itemWallet.setFoundDate(foundDateDayAgo);
        itemWallet.setFoundLocation(foundLocation);
        itemWallet.setStatus(Status.RESOLVED);
        assertThat(itemWallet).isEqualToComparingFieldByField(resolvedItem);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".* cannot be null")
    public void testResolveLostItemItemNull() {
        itemService.resolveLostItem(null, LocalDate.now(), new Location());
    }

    @Test(expectedExceptions = IllegalArgumentException.class,  expectedExceptionsMessageRegExp = ".* cannot be null")
    public void testResolveLostItemLostDateNull() {
        itemService.resolveLostItem(itemWallet, null, new Location());
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = ".* must be in CLAIM_RECEIVED_LOST state")
    public void testResolveLostItemIncorrectState() {
        itemService.resolveLostItem(itemUmbrella, LocalDate.now(), new Location());
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Found date must be after lostDate")
    public void testResolveLostItemFoundDateBefore() {
        itemService.resolveLostItem(itemWallet, LocalDate.now().minusYears(1), new Location());
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Owner must be set")
    public void testResolveLostItemOwnerNull() {
        itemWallet.setOwner(null);
        itemService.resolveLostItem(itemWallet, LocalDate.now(), new Location());
    }

    @Test
    public void testResolveFoundItem() {
        Item resolvedItem = itemService.resolveFoundItem(itemUmbrella, lostDateNow, lostLocation, owner);
        itemUmbrella.setLostDate(lostDateNow);
        itemUmbrella.setLostLocation(lostLocation);
        itemUmbrella.setStatus(Status.RESOLVED);
        assertThat(itemUmbrella).isEqualToComparingFieldByField(resolvedItem);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = ".* cannot be null")
    public void testResolveFoundItemItemNull() {
        itemService.resolveFoundItem(null, lostDateNow, lostLocation, owner);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,  expectedExceptionsMessageRegExp = ".* cannot be null")
    public void testResolveFoundItemLostDateNull() {
        itemService.resolveFoundItem(itemUmbrella, null, lostLocation, owner);
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = ".* must be in CLAIM_RECEIVED_FOUND state")
    public void testResolveFoundItemIncorrectState() {
        itemService.resolveFoundItem(itemWallet, lostDateNow, lostLocation, owner);
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Lost date must be after foundDate")
    public void testResolveFoundItemLostDateBefore() {
        itemService.resolveFoundItem(itemUmbrella, lostDateMonthAgo, lostLocation, owner);
    }

    @Test(expectedExceptions = IllegalStateException.class, expectedExceptionsMessageRegExp = "Owner cannot be set")
    public void testResolveFoundItemOwnerNull() {
        itemUmbrella.setOwner(owner);
        itemService.resolveFoundItem(itemUmbrella, lostDateNow, lostLocation, owner);
    }
}