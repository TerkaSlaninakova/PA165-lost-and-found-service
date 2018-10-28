package cz.muni.fi;

import cz.muni.fi.dao.ItemDao;
import cz.muni.fi.entity.Item;
import cz.muni.fi.entity.Status;
import cz.muni.fi.exceptions.ItemDaoException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.NoSuchEJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import java.util.List;
import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Jakub Polacek
 */
public class ItemDaoImplTest {

    private static Context context;
    private static Properties p;

    private static ItemDao itemDao;
    private static Item phone, notebook;

    /**
     *
     * Create database connection
     */
    @BeforeClass
    public static void suiteSetup() {
        p = new Properties();
        p.put("itemDatabase", "new://Resource?type=DataSource");
        p.put("itemDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("itemDatabase.JdbcUrl", "jdbc:hsqldb:mem:itemdb");

        context = EJBContainer.createEJBContainer(p).getContext();
    }

    /**
     * Create mock objects for testing
     * @throws Exception when something goes horribly wrong
     */
    @Before
    public void testSetup() throws Exception {
        itemDao = (ItemDao) context.lookup("java:global/lost-and-found-service-persistence/ItemDaoImpl");

        phone = new Item();
        phone.setName("phone");
        phone.setCharacteristics("black, small, samsung model");
        phone.setPhoto("photo");
        phone.setStatus(Status.CLAIM_RECEIVED);

        notebook = new Item();
        notebook.setName("notebook");
        notebook.setCharacteristics("white, macbook");
        notebook.setPhoto("photo");
        notebook.setStatus(Status.IN_PROGRESS);
    }

    @After
    public void testTeardown() throws ItemDaoException {
        // make sure that itemDao is cleaned after every test (to make tests independent of one another)
        try {
            List<Item> items = itemDao.getAllItems();
            for (Item item : items) {
                itemDao.deleteItem(item);
            }

        } catch (
                NoSuchEJBException ex) {
            // needed after negative test cases, userDao contains thrown exception and needs to be re-created
        }
    }

    @Test
    public void shouldReturn0ItemsWhenEmpty() throws Exception {
        assertEquals(itemDao.getAllItems().size(), 0);
        assertNull(itemDao.findItembyId(0L));
    }

    @Test
    public void shouldAddItem() throws Exception {
        itemDao.createItem(phone);
        assertEquals(itemDao.getAllItems().size(), 1);
    }

    @Test
    public void shouldNotCreateAdditionalItemIfTheSameOneAdded() throws Exception {
        itemDao.createItem(phone);
        itemDao.createItem(phone);
        assertEquals(itemDao.getAllItems().size(), 1);
        itemDao.deleteItem(phone);
        assertEquals(itemDao.getAllItems().size(), 0);
        itemDao.createItem(new Item());
        assertEquals(itemDao.getAllItems().size(), 1);
    }

    @Test
    public void ShouldUpdateItem() throws Exception {
        itemDao.createItem(phone);
        String newCharacteristics = "Huawei";
        Status newStatus = Status.FOUND;
        String newType = "best type";

        phone.setStatus(newStatus);
        phone.setCharacteristics(newCharacteristics);
        phone.setType(newType);

        itemDao.updateItem(phone);

        Item updatedItem = itemDao.findItembyId(phone.getId());

        assertEquals(updatedItem.getCharacteristics(), newCharacteristics);
        assertEquals(updatedItem.getStatus(), newStatus);
        assertEquals(updatedItem.getType(), newType);
    }

    @Test
    public void ShouldUpdateItemWhenNoChange() throws Exception {
        itemDao.createItem(phone);
        itemDao.updateItem(phone);

        Item updatedItem = itemDao.findItembyId(phone.getId());

        assertEquals(phone, updatedItem);
    }

    @Test
    public void ShouldDeleteItem() throws Exception {
        itemDao.createItem(phone);
        itemDao.createItem(notebook);
        assertEquals(itemDao.getAllItems().size(), 2);
        itemDao.deleteItem(phone);
        assertEquals(itemDao.getAllItems().size(), 1);

        assertNull(itemDao.findItembyId(phone.getId()));
        assertEquals(itemDao.findItembyId(notebook.getId()), notebook);
    }

    @Test
    public void shouldFailOnAddNullItem() throws Exception {
        assertThatThrownBy(() -> itemDao.createItem(null)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailOnAddNullUpdate() throws Exception {
        assertThatThrownBy(() -> itemDao.updateItem(null))
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailOnAddNullDelete() throws Exception {
        assertThatThrownBy(() -> itemDao.deleteItem(null))
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailOnAddNullGetById() throws Exception {
        assertThatThrownBy(() -> itemDao.findItembyId(null))
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteNullIdItem() throws Exception {
        assertThatThrownBy(() -> itemDao.deleteItem(phone)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNullIdItem() throws Exception {
        assertThatThrownBy(() -> itemDao.updateItem(phone)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

}   