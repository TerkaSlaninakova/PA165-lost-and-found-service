package cz.muni.fi.facade;

import cz.muni.fi.dto.CategoryDTO;

import java.util.List;

public interface CategoryFacade {

    CategoryDTO getCategoryById(Long userId);

    /**
     * Create a given category
     */
    void addCategory(CategoryDTO c);

    /**
     * Get all categories
     */
    List<CategoryDTO> getAllCategories();
}
