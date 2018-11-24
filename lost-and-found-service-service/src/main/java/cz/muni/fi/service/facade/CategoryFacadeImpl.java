package cz.muni.fi.service.facade;

import cz.muni.fi.dto.CategoryCreateDTO;
import cz.muni.fi.dto.CategoryDTO;
import cz.muni.fi.facade.CategoryFacade;
import cz.muni.fi.persistence.entity.Category;
import cz.muni.fi.service.BeanMappingService;
import cz.muni.fi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CategoryFacadeImpl implements CategoryFacade {

    @Autowired
    BeanMappingService beanMappingService;

    @Autowired
    CategoryService categoryService;


    @Override
    public void addCategory(CategoryCreateDTO categoryDTO) {
        categoryService.addCategory(beanMappingService.mapTo(categoryDTO, Category.class));
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        categoryService.updateCategory(beanMappingService.mapTo(categoryDTO, Category.class));
    }

    @Override
    public void deleteCategory(CategoryDTO categoryDTO) {
        categoryService.deleteCategory(beanMappingService.mapTo(categoryDTO, Category.class));
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return beanMappingService.mapTo(categoryService.getCategoryById(id), CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return beanMappingService.mapTo(categoryService.getAllCategories(), CategoryDTO.class);
    }
}
