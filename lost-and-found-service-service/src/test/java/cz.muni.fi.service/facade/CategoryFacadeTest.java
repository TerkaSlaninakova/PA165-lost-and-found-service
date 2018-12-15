package cz.muni.fi.service.facade;

import cz.muni.fi.api.dto.CategoryCreateDTO;
import cz.muni.fi.api.dto.CategoryDTO;
import cz.muni.fi.api.facade.CategoryFacade;
import cz.muni.fi.persistence.entity.Category;
import cz.muni.fi.service.BeanMappingService;
import cz.muni.fi.service.BeanMappingServiceImpl;
import cz.muni.fi.service.CategoryService;
import cz.muni.fi.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for categoryFacade
 * @author Jakub Polacek
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CategoryFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryFacade categoryFacade = new CategoryFacadeImpl();

    @Spy
    @Autowired
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    private Category electronics, clothes;
    private CategoryDTO categoryDTO;
    private CategoryCreateDTO categoryCreateDTO;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        electronics = new Category();
        electronics.setId(1L);
        electronics.setAttribute("water resistant = false");
        electronics.setName("electronics");

        clothes = new Category();
        clothes.setId(2L);
        clothes.setAttribute("water resistant = true");
        clothes.setName("clothes");
        
        categoryDTO = beanMappingService.mapTo(electronics, CategoryDTO.class);
        
        categoryCreateDTO = new CategoryCreateDTO();
        categoryCreateDTO.setAttribute("water resistant = maybe");
        categoryCreateDTO.setName("pencils");
    }

    @Test
    public void testAddCategory() {
        categoryFacade.addCategory(categoryCreateDTO);
        verify(categoryService).addCategory(any(Category.class));
    }

    @Test
    public void testUpdateCategory() {
        categoryDTO.setAttribute(categoryDTO.getAttribute() + " expensive = true");
        categoryFacade.updateCategory(categoryDTO);
        verify(categoryService).updateCategory(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        categoryFacade.deleteCategory(categoryDTO);
        verify(categoryService).deleteCategory(any(Category.class));
    }

    @Test
    public void testGetCategoryById() {
        when(categoryService.getCategoryById(1L)).thenReturn(electronics);
        CategoryDTO loadedLocationDto = categoryFacade.getCategoryById(1L);
        verify(categoryService).getCategoryById(1L);
        assertEquals(electronics, beanMappingService.mapTo(loadedLocationDto, Category.class));
    }

    @Test
    public void testGetAllCategorys() {
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(electronics, clothes));
        List<CategoryDTO> locationDtos = categoryFacade.getAllCategories();
        verify(categoryService).getAllCategories();
        List<Category> locations = beanMappingService.mapTo(locationDtos, Category.class);
        assertEquals(2, locations.size());
        assertThat(locations.contains(electronics));
        assertThat(locations.contains(clothes));
    }
}
