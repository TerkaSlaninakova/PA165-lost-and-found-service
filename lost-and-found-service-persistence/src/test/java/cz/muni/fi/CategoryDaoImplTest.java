package cz.muni.fi;

import cz.muni.fi.dao.CategoryDao;
import cz.muni.fi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

/**
 *
 * @author Augustin Nemec
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CategoryDaoImplTest  extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryDao categoryDao;

    private static Category electro;


    @BeforeMethod
    public void setup() {

        electro = new Category();
        electro.setName("Electro");
        electro.setAttribute("something");

    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addNullCategory() {
        categoryDao.addCategory(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullCategory() {
        categoryDao.updateCategory(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullIdCategory() {
        entityManager.persist(electro);
        electro.setId(null);
        categoryDao.updateCategory(electro);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullCategory() {
        categoryDao.deleteCategory(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullIdCategory() {
        entityManager.persist(electro);
        electro.setId(null);
        categoryDao.deleteCategory(electro);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void getNullCategoryById() {
        categoryDao.getCategoryById(null);
    }

    @Test
    public void testAddCategory() {
        categoryDao.addCategory(electro);
        assertEquals(electro, entityManager.find(Category.class, electro.getId()));
    }

    @Test
    public void testDeleteCategory() {
        entityManager.persist(electro);
        categoryDao.deleteCategory(electro);
        assertNull(entityManager.find(Category.class, electro.getId()));
    }

    @Test
    public void testUpdateCategory() {
        entityManager.persist(electro);
        Category modified = electro;
        modified.setName("Electronics");
        categoryDao.updateCategory(modified);
        assertEquals(electro, modified);

    }

    @Test
    public void testGetAllCategoriesWhenEmpty() {
        assertEquals(categoryDao.getAllCategories().size(), 0);
    }




}
