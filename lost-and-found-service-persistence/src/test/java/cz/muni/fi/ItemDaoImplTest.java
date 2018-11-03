package cz.muni.fi;

import cz.muni.fi.dao.ItemDao;
import cz.muni.fi.entity.Item;
import cz.muni.fi.enums.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.NoSuchEJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
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
     *
     * @ when something goes horribly wrong
     */
    @Before
    public void testSetup() throws NamingException {
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
    public void testTeardown() {
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
    public void shouldReturn0ItemsWhenEmpty() {
        assertEquals(itemDao.getAllItems().size(), 0);
        assertNull(itemDao.getItembyId(0L));
    }

    @Test
    public void shouldAddItem() {
        itemDao.addItem(phone);
        assertEquals(itemDao.getAllItems().size(), 1);
    }

    @Test
    public void shouldNotCreateAdditionalItemIfTheSameOneAdded() {
        itemDao.addItem(phone);
        itemDao.addItem(phone);
        assertEquals(itemDao.getAllItems().size(), 1);
        itemDao.deleteItem(phone);
        assertEquals(itemDao.getAllItems().size(), 0);
        itemDao.addItem(new Item());
        assertEquals(itemDao.getAllItems().size(), 1);
    }

    @Test
    public void shouldUpdateItem() {
        itemDao.addItem(phone);
        String newCharacteristics = "Huawei";
        Status newStatus = Status.FOUND;
        String newType = "best type";

        phone.setStatus(newStatus);
        phone.setCharacteristics(newCharacteristics);
        phone.setType(newType);

        itemDao.updateItem(phone);

        Item updatedItem = itemDao.getItembyId(phone.getId());

        assertEquals(updatedItem.getCharacteristics(), newCharacteristics);
        assertEquals(updatedItem.getStatus(), newStatus);
        assertEquals(updatedItem.getType(), newType);
    }

    @Test
    public void shouldUpdateItemWhenNoChange() {
        itemDao.addItem(phone);
        itemDao.updateItem(phone);

        Item updatedItem = itemDao.getItembyId(phone.getId());

        assertEquals(phone, updatedItem);
    }

    @Test
    public void shouldDeleteItem() {
        itemDao.addItem(phone);
        itemDao.addItem(notebook);
        assertEquals(itemDao.getAllItems().size(), 2);
        itemDao.deleteItem(phone);
        assertEquals(itemDao.getAllItems().size(), 1);

        assertNull(itemDao.getItembyId(phone.getId()));
        assertEquals(itemDao.getItembyId(notebook.getId()), notebook);
    }

    @Test
    public void shouldFailOnAddNullItem() {
        assertThatThrownBy(() -> itemDao.addItem(null)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailOnAddNullUpdate() {
        assertThatThrownBy(() -> itemDao.updateItem(null))
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailOnAddNullDelete() {
        assertThatThrownBy(() -> itemDao.deleteItem(null))
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailOnAddNullGetById() {
        assertThatThrownBy(() -> itemDao.getItembyId(null))
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteNullIdItem() {
        assertThatThrownBy(() -> itemDao.deleteItem(phone)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNullIdItem() {
        assertThatThrownBy(() -> itemDao.updateItem(phone)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

}   