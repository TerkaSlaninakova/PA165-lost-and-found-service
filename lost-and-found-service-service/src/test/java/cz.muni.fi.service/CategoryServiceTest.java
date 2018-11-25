package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.CategoryDao;
import cz.muni.fi.persistence.entity.Category;
import cz.muni.fi.service.config.ServiceConfiguration;
import cz.muni.fi.service.exceptions.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertNotNull;

/**
 * Tests for CategoryService
 * @author Terezia Slaninakova (445526)
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class CategoryServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CategoryDao categoryDao;

    @Autowired
    @InjectMocks
    private CategoryService categoryService = new CategoryServiceImpl();


    private Category categoryElectro;
    private Category categoryClothes;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createCategories() {

        categoryElectro = new Category();
        categoryElectro.setId(1L);
        categoryElectro.setAttribute("smartphone");
        categoryElectro.setName("Electro");

        categoryClothes = new Category();
        categoryClothes.setId(2L);
        categoryClothes.setAttribute("winter jacket");
        categoryClothes.setName("Clothes");
    }

    @Test
    public void testAddCategory() {

        doAnswer(invocationOnMock -> {
            Category category = (Category) invocationOnMock.getArguments()[0];
            category.setId(1L);
            return null;
        }).when(categoryDao).addCategory(categoryElectro);

        categoryService.addCategory(categoryElectro);

        assertNotNull(categoryElectro.getId());
        verify(categoryDao).addCategory(categoryElectro);

    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddNullCategory() {
        doThrow(new IllegalArgumentException()).when(categoryDao).addCategory(null);
        categoryService.addCategory(null);
    }

    @Test
    public void testUpdateCategory() {
        doNothing().when(categoryDao).updateCategory(categoryElectro);
        categoryService.updateCategory(categoryElectro);
        verify(categoryDao).updateCategory(categoryElectro);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateNullCategory() {
        doThrow(new IllegalArgumentException()).when(categoryDao).updateCategory(null);
        categoryService.updateCategory(null);
    }

    @Test
    public void testGetCategoryById()  {
        when(categoryDao.getCategoryById(1L)).thenReturn(categoryElectro);
        assertThat(categoryDao.getCategoryById(categoryElectro.getId())).isEqualToComparingFieldByField(categoryElectro);
        verify(categoryDao).getCategoryById(categoryElectro.getId());
    }

    @Test
    public void testGetAllCategories()  {
        when(categoryDao.getAllCategories()).thenReturn(Arrays.asList(categoryClothes, categoryElectro));
        assertThat(categoryService.getAllCategories()).containsExactlyInAnyOrder(categoryClothes, categoryElectro);
        verify(categoryDao).getAllCategories();
    }
}
