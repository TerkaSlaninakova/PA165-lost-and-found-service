package cz.muni.fi.persistence.dao;

import cz.muni.fi.persistence.PersistenceApplicationContext;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.testng.AssertJUnit.*;


/**
 * @author Jakub Polacek
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ItemDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ItemDao itemDao;

    @PersistenceContext
    private EntityManager em;

    private static Item phone, notebook;

    @BeforeMethod
    public void testSetup() {
        phone = new Item();
        phone.setName("phone");
        phone.setCharacteristics("black, small, samsung model");
        phone.setImageMimeType("photo");
        phone.setStatus(Status.CLAIM_RECEIVED_LOST);

        notebook = new Item();
        notebook.setName("notebook");
        notebook.setCharacteristics("white, macbook");
        notebook.setImageMimeType("photo");
        notebook.setStatus(Status.CLAIM_RECEIVED_FOUND);
    }


    @Test
    public void testReturn0ItemsWhenEmpty() {
        assertEquals(itemDao.getAllItems().size(), 0);
        assertNull(itemDao.getItemById(0L));
    }

    @Test
    public void testAddItem() {
        itemDao.addItem(phone);
        assertEquals(em.createQuery("select i from Item i", Item.class)
                .getResultList().size(), 1);
    }

    @Test
    public void testNotCreateAdditionalItemIfTheSameOneAdded() {
        itemDao.addItem(phone);
        itemDao.addItem(phone);
        assertEquals((em.createQuery("select i from Item i", Item.class)
                .getResultList().size()), 1);
        em.remove(phone);
        assertEquals((em.createQuery("select i from Item i", Item.class)
                .getResultList().size()), 0);
        itemDao.addItem(notebook);
        assertEquals((em.createQuery("select i from Item i", Item.class)
                .getResultList().size()), 1);
    }

    @Test
    public void testUpdateItem() {
        em.persist(phone);
        String newCharacteristics = "Huawei";
        Status newStatus = Status.RESOLVED;
        String newType = "best type";

        phone.setStatus(newStatus);
        phone.setCharacteristics(newCharacteristics);
        phone.setType(newType);

        itemDao.updateItem(phone);

        Item updatedItem = em.find(Item.class, phone.getId());

        assertEquals(updatedItem.getCharacteristics(), newCharacteristics);
        assertEquals(updatedItem.getStatus(), newStatus);
        assertEquals(updatedItem.getType(), newType);
    }

    @Test
    public void testUpdateItemWhenNoChange() {
        em.persist(phone);
        itemDao.updateItem(phone);

        Item updatedItem = em.find(Item.class, phone.getId());

        assertEquals(phone, updatedItem);
    }

    @Test
    public void testDeleteItem() {
        em.persist(phone);
        em.persist(notebook);
        itemDao.deleteItem(phone);
        assertEquals((em.createQuery("select i from Item i", Item.class)
                .getResultList().size()), 1);

        assertNull(em.find(Item.class, phone.getId()));
        assertEquals(em.find(Item.class, notebook.getId()), notebook);
    }

    @Test
    public void testGetItemById() {
        em.persist(phone);
        em.persist(notebook);
        assertEquals(phone, itemDao.getItemById(phone.getId()));
        assertEquals(notebook, itemDao.getItemById(notebook.getId()));
    }

    @Test
    public void testGetAllItems() {
        em.persist(phone);
        em.persist(notebook);
        List<Item> itemList = itemDao.getAllItems();
        assertEquals(itemList.size(), 2);
        assertTrue(itemList.contains(phone));
        assertTrue(itemList.contains(notebook));
    }


    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void testAddItemWithNullStatus() {
        phone.setStatus(null);
        itemDao.addItem(phone);
        assertEquals(em.createQuery("select i from Item i", Item.class)
                .getResultList().size(), 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullItem() {
        itemDao.addItem(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullUpdate() {
        itemDao.updateItem(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullDelete() {
        itemDao.deleteItem(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullGetById() {
        itemDao.getItemById(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNullIdItem() {
        itemDao.deleteItem(phone);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNullIdItem() {
        itemDao.updateItem(phone);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNullIdLookup() { itemDao.getItemById(null); }
}