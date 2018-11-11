package cz.muni.fi;

import cz.muni.fi.dao.ItemDao;
import cz.muni.fi.entity.Item;
import cz.muni.fi.enums.Status;
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

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;


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
        phone.setPhoto("photo");
        phone.setStatus(Status.CLAIM_RECEIVED);

        notebook = new Item();
        notebook.setName("notebook");
        notebook.setCharacteristics("white, macbook");
        notebook.setPhoto("photo");
        notebook.setStatus(Status.IN_PROGRESS);
    }


    @Test
    public void shouldReturn0ItemsWhenEmpty() {
        assertEquals(itemDao.getAllItems().size(), 0);
        assertNull(itemDao.getItembyId(0L));
    }

    @Test
    public void shouldAddItem() {
        itemDao.addItem(phone);
        assertEquals(em.createQuery("select i from Item i", Item.class)
                .getResultList().size(), 1);
    }

    @Test
    public void shouldNotCreateAdditionalItemIfTheSameOneAdded() {
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
    public void ShouldUpdateItem() {
        em.persist(phone);
        String newCharacteristics = "Huawei";
        Status newStatus = Status.FOUND;
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
    public void ShouldUpdateItemWhenNoChange() {
        em.persist(phone);
        itemDao.updateItem(phone);

        Item updatedItem = em.find(Item.class, phone.getId());

        assertEquals(phone, updatedItem);
    }

    @Test
    public void ShouldDeleteItem() {
        em.persist(phone);
        em.persist(notebook);
        itemDao.deleteItem(phone);
        assertEquals((em.createQuery("select i from Item i", Item.class)
                .getResultList().size()), 1);

        assertNull(em.find(Item.class, phone.getId()));
        assertEquals(em.find(Item.class, notebook.getId()), notebook);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldFailOnAddNullItem() {
        itemDao.addItem(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldFailOnAddNullUpdate() {
        itemDao.updateItem(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldFailOnAddNullDelete() {
        itemDao.deleteItem(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldFailOnAddNullGetById() {
        itemDao.getItembyId(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullIdItem() {
        itemDao.deleteItem(phone);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullIdItem() {
        itemDao.updateItem(phone);
    }
}   