package cz.muni.fi;

import cz.muni.fi.dao.CategoryDao;
import cz.muni.fi.entity.Category;
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

public class CategoryDaoImplTest {

    private static Context context;
    private static Properties p;

    private static CategoryDao categoryDao;
    private static Category electro, clothes;

    @BeforeClass
    public static void suiteSetup() {
        p = new Properties();
        p.put("categoryDatabase", "new://Resource?type=DataSource");
        p.put("categoryDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("categoryDatabase.JdbcUrl", "jdbc:hsqldb:mem:itemdb");

        context = EJBContainer.createEJBContainer(p).getContext();
    }

    @Before
    public void testSetup() throws Exception {
        categoryDao = (CategoryDao) context.lookup("java:global/lost-and-found-service-persistence/CategoryDaoImpl");

        electro = new Category();
        electro.setName("Electro");
        electro.setAttribute("something");

        clothes = new Category();
        electro.setName("Clothes");
        electro.setAttribute("something");

    }

    @After
    public void testTeardown() {
        // make sure that categoryDao is cleaned after every test (to make tests independent of one another)
        try {
            List<Category> categories = categoryDao.getAllCategories();
            for (Category c : categories) {
                categoryDao.deleteCategory(c);
            }

        } catch (
                NoSuchEJBException ex) {
            // needed after negative test cases, userDao contains thrown exception and needs to be re-created
        }
    }



    @Test
    public void addNullCategory() {
        assertThatThrownBy(() -> categoryDao.addCategory(null)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNullCategory() {
        assertThatThrownBy(() -> categoryDao.updateCategory(null)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNullIdCategory() {
        assertThatThrownBy(() -> categoryDao.updateCategory(electro)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteNullCategory() {
        assertThatThrownBy(() -> categoryDao.deleteCategory(null)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteNullIdCategory() {
        assertThatThrownBy(() -> categoryDao.deleteCategory(electro)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getNullCategoryById() {
        assertThatThrownBy(() -> categoryDao.getCategoryById(null)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldAddCategory() {
        categoryDao.addCategory(electro);
        assertEquals(categoryDao.getAllCategories().size(), 1);
    }

    @Test
    public void shouldDeleteCategory() {
        categoryDao.addCategory(electro);
        categoryDao.addCategory(clothes);
        assertEquals(categoryDao.getAllCategories().size(), 2);
        categoryDao.deleteCategory(electro);
        assertEquals(categoryDao.getAllCategories().size(), 1);

        assertNull(categoryDao.getCategoryById(electro.getId()));
        assertEquals(categoryDao.getCategoryById(clothes.getId()), clothes);
    }

    @Test
    public void shouldUpdateCategory() {
        categoryDao.addCategory(electro);
        String newName = "Electronics";

        electro.setName(newName);
        categoryDao.updateCategory(electro);

        Category updatedCategory = categoryDao.getCategoryById(electro.getId());

        assertEquals(updatedCategory.getName(), newName);
    }

    @Test
    public void shouldReturn0CategoriesWhenEmpty() {
        assertEquals(categoryDao.getAllCategories().size(), 0);
        assertNull(categoryDao.getCategoryById(0L));
    }





}
