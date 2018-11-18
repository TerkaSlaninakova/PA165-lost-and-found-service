package cz.muni.fi.facade;

import cz.muni.fi.dto.CategoryDTO;

import java.util.List;

public interface CategoryFacade {

    CategoryDTO findCategoryById(Long userId);

    /**
     * Create a given category
     */
    void createCategory(CategoryDTO c);

    /**
     * Get all categories
     */
    List<CategoryDTO> getAllCategories();
}
