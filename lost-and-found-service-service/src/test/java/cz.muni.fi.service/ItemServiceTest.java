package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.ItemDao;
import cz.muni.fi.persistence.entity.Category;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.config.ServiceConfiguration;
import cz.muni.fi.service.exceptions.ServiceException;
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
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


/**
 * Tests for ItemService
 * @author Terezia Slaninakova (445526) & Augustin Nemec
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class ItemServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private ItemDao itemDao;

    @Mock
    private UserService userService;

    @Mock
    private LocationService locationService;

    @Mock
    private CategoryService categoryService;

    @Autowired
    @InjectMocks
    private ItemService itemService = new ItemServiceImpl();

    private Item itemWallet, itemUmbrella,itemJacket;
    private LocalDate lostDateMonthAgo, lostDateNow, foundDateDayAgo;
    private Location lostLocation, foundLocation;
    private User owner;
    private Category clothes;

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

        clothes = new Category();
        clothes.setName("Clothes");
        clothes.setId(1L);

        itemWallet = new Item();
        itemWallet.setName("Leather wallet");
        itemWallet.setId(1L);
        itemWallet.setStatus(Status.CLAIM_RECEIVED_LOST);
        itemWallet.setOwner(owner);
        itemWallet.setLostDate(lostDateMonthAgo);
        itemWallet.setLostLocation(lostLocation);
        itemWallet.setCategories(new LinkedList<>());

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
        itemJacket.setCategories(new LinkedList<Category>() {{
            add(clothes);
        }});
        clothes.setItems(new LinkedList<Item>() {{
            add(itemJacket);
        }});

        reset(itemDao);
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

    @Test(expectedExceptions = ServiceException.class)
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

    @Test(expectedExceptions = ServiceException.class)
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
        when(itemDao.getItemById(1L)).thenReturn(itemWallet);
        assertThat(itemService.getItemById(itemWallet.getId())).isEqualToComparingFieldByField(itemWallet);
        verify(itemDao).getItemById(itemWallet.getId());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testGetItemByNullId() {
        doThrow(new IllegalArgumentException()).when(itemDao).getItemById(null);
        itemService.getItemById(null);
    }

    @Test
    public void testDeleteItem() {
        when(itemDao.getItemById(itemUmbrella.getId())).thenReturn(itemUmbrella);
        doNothing().when(itemDao).deleteItem(itemUmbrella);
        itemService.deleteItem(itemUmbrella);
        verify(itemDao).deleteItem(itemUmbrella);
    }

    @Test(expectedExceptions = ServiceException.class)
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
                " 'photo': '" + itemJacket.getImageMimeType() + "'," +
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

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "Item cannot be null")
    public void testArchiveItemNullItem() {
        itemService.archiveItem(null);
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "Item must be in resolved state")
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

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = ".* cannot be null")
    public void testResolveLostItemItemNull() {
        itemService.resolveLostItem(null, LocalDate.now(), new Location());
    }

    @Test(expectedExceptions = ServiceException.class,  expectedExceptionsMessageRegExp = ".* cannot be null")
    public void testResolveLostItemLostDateNull() {
        itemService.resolveLostItem(itemWallet, null, new Location());
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = ".* must be in CLAIM_RECEIVED_LOST state")
    public void testResolveLostItemIncorrectState() {
        itemService.resolveLostItem(itemUmbrella, LocalDate.now(), new Location());
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "Found date must be after lostDate")
    public void testResolveLostItemFoundDateBefore() {
        itemService.resolveLostItem(itemWallet, LocalDate.now().minusYears(1), new Location());
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "Owner must be set")
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

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = ".* cannot be null")
    public void testResolveFoundItemItemNull() {
        itemService.resolveFoundItem(null, lostDateNow, lostLocation, owner);
    }

    @Test(expectedExceptions = ServiceException.class,  expectedExceptionsMessageRegExp = ".* cannot be null")
    public void testResolveFoundItemLostDateNull() {
        itemService.resolveFoundItem(itemUmbrella, null, lostLocation, owner);
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = ".* must be in CLAIM_RECEIVED_FOUND state")
    public void testResolveFoundItemIncorrectState() {
        itemService.resolveFoundItem(itemWallet, lostDateNow, lostLocation, owner);
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "Lost date must be after foundDate")
    public void testResolveFoundItemLostDateBefore() {
        itemService.resolveFoundItem(itemUmbrella, lostDateMonthAgo, lostLocation, owner);
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "Owner cannot be set")
    public void testResolveFoundItemOwnerNull() {
        itemUmbrella.setOwner(owner);
        itemService.resolveFoundItem(itemUmbrella, lostDateNow, lostLocation, owner);
    }

    @Test
    public void testGetAllItemsWithStatusEmpty() {
        Assert.assertEquals(itemService.getAllItemsWithStatus(null).size(), 0);
    }

    @Test
    public void testGetAllItemsWithStatus() {
        when(itemDao.getAllItems()).thenReturn(Arrays.asList(itemUmbrella, itemWallet, itemJacket));
        List<Item> foundItems = itemService.getAllItemsWithStatus(Status.CLAIM_RECEIVED_FOUND);
        Assert.assertEquals(foundItems.size(), 1);
        assertThat(foundItems.get(0)).isEqualToComparingFieldByField(itemUmbrella);

    }

    @Test
    public void testChangeUser() {
        User newOwner = new User();
        newOwner.setName("newOwner");
        newOwner.setId(2L);

        when(userService.getUserById(newOwner.getId())).thenReturn(newOwner);
        when(itemDao.getItemById(itemWallet.getId())).thenReturn(itemWallet);


        itemService.changeUser(itemWallet.getId(), newOwner.getId());

        verify(itemDao).getItemById(itemWallet.getId());
        assertThat(itemWallet.getOwner()).isEqualTo(newOwner);
    }

    @Test
    public void testChangeNullUser() {
        when(itemDao.getItemById(itemWallet.getId())).thenReturn(itemWallet);

        itemService.changeUser(itemWallet.getId(), null);
        assertThat(itemWallet.getOwner()).isEqualTo(null);
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "itemId can't be null")
    public void testChangeUserOnNullItem() {
        User newOwner = new User();
        newOwner.setName("newOwner");
        newOwner.setId(2L);

        when(userService.getUserById(newOwner.getId())).thenReturn(newOwner);

        itemService.changeUser(null, newOwner.getId());
    }

    @Test
    public void testChangeLostLocation() {
        Location newLocation = new Location();
        newLocation.setId(2L);
        newLocation.setDescription("Near park.");

        when(locationService.getLocationById(newLocation.getId())).thenReturn(newLocation);
        when(itemDao.getItemById(itemWallet.getId())).thenReturn(itemWallet);

        itemService.changeLostLocation(itemWallet.getId(), newLocation.getId());
        assertThat(itemWallet.getLostLocation()).isEqualTo(newLocation);
    }

    @Test
    public void testChangeNullLostLocation() {
        when(itemDao.getItemById(itemWallet.getId())).thenReturn(itemWallet);

        itemService.changeLostLocation(itemWallet.getId(), null);
        assertThat(itemWallet.getLostLocation()).isEqualTo(null);
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "itemId can't be null")
    public void testChangeLostLocationOnNullItem() {
        Location newLocation = new Location();
        newLocation.setId(2L);
        newLocation.setDescription("Near park.");

        when(locationService.getLocationById(newLocation.getId())).thenReturn(newLocation);

        itemService.changeLostLocation(null, newLocation.getId());
    }

    @Test
    public void testChangeFoundLocation() {
        Location newLocation = new Location();
        newLocation.setId(2L);
        newLocation.setDescription("Near park.");

        when(locationService.getLocationById(newLocation.getId())).thenReturn(newLocation);
        when(itemDao.getItemById(itemWallet.getId())).thenReturn(itemWallet);

        itemService.changeFoundLocation(itemWallet.getId(), newLocation.getId());
        assertThat(itemWallet.getFoundLocation()).isEqualTo(newLocation);
    }

    @Test
    public void testChangeNullFoundLocation() {
        when(itemDao.getItemById(itemWallet.getId())).thenReturn(itemWallet);

        itemService.changeLostLocation(itemWallet.getId(), null);
        assertThat(itemWallet.getFoundLocation()).isEqualTo(null);
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "itemId can't be null")
    public void testChangeFoundLocationOnNullItem() {
        Location newLocation = new Location();
        newLocation.setId(2L);
        newLocation.setDescription("Near park.");

        when(locationService.getLocationById(newLocation.getId())).thenReturn(newLocation);

        itemService.changeFoundLocation(null, newLocation.getId());
    }

    @Test
    public void testAddCategoryToItem() {
        when(categoryService.getCategoryById(clothes.getId())).thenReturn(clothes);
        when(itemDao.getItemById(itemWallet.getId())).thenReturn(itemWallet);

        itemService.addCategoryToItem(itemWallet.getId(), clothes.getId());
        assertThat(itemWallet.getCategories()).containsOnly(clothes);
        assertThat(clothes.getItems()).contains(itemWallet);
    }

    @Test
    public void testRemoveCategoryToItem() {
        when(categoryService.getCategoryById(clothes.getId())).thenReturn(clothes);
        when(itemDao.getItemById(itemJacket.getId())).thenReturn(itemJacket);

        itemService.removeCategoryFromItem(itemJacket.getId(), clothes.getId());
        assertThat(itemJacket.getCategories()).isEmpty();
        assertThat(clothes.getItems()).isEmpty();
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "Item is not of given category, cannot remove")
    public void testRemoveWrongCategoryToItem() {
        when(categoryService.getCategoryById(clothes.getId())).thenReturn(clothes);
        when(itemDao.getItemById(itemWallet.getId())).thenReturn(itemWallet);

        itemService.removeCategoryFromItem(itemWallet.getId(), clothes.getId());
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "Arguments can't be null")
    public void testRemoveWrongNullCategoryToItem() {
        when(itemDao.getItemById(itemWallet.getId())).thenReturn(itemWallet);

        itemService.removeCategoryFromItem(itemWallet.getId(), null);
    }

    @Test(expectedExceptions = ServiceException.class, expectedExceptionsMessageRegExp = "Arguments can't be null")
    public void testRemoveWrongCategoryToNullItem() {
        when(categoryService.getCategoryById(clothes.getId())).thenReturn(clothes);

        itemService.removeCategoryFromItem(null, clothes.getId());
    }
}