package cz.muni.fi.api.facade;

import cz.muni.fi.api.dto.CategoryCreateDTO;
import cz.muni.fi.api.dto.CategoryDTO;

import java.util.List;

/**
 * Facade interface for Category
 * @author Jakub Polacek
 */
public interface CategoryFacade {

    /**
     * Create a given category
     */
    void addCategory(CategoryCreateDTO c);

    /**
     * Update Category
     * @param categoryDTO categoryDTO to update
     */
    void updateCategory(CategoryDTO categoryDTO);

    /**
     * Delete Category
     * @param categoryDTO to be deleted
     */
    void deleteCategory(CategoryDTO categoryDTO);

    /**
     * Get location by id
     * @param id id of location
     * @return Category or null
     */
    CategoryDTO getCategoryById(Long id);

    /**
     * Get all categories
     */
    List<CategoryDTO> getAllCategories();
}
