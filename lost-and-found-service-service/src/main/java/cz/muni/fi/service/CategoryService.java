package cz.muni.fi.service;

import cz.muni.fi.persistence.entity.Category;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Service layer interface for Category
 * @author Terezia Slaninakova (445526)
 */
public interface CategoryService {
    /**
     * Save category to DB
     * @param category object to be saved
     * @throws DataAccessException if category is null
     */
    public void addCategory(Category category) throws DataAccessException;

    /**
     * Update category in DB
     * @param category object to update
     * @throws DataAccessException if category or category.id is null
     */
    public void updateCategory(Category category) throws DataAccessException;

    /**
     * Delete category from db
     * @param category object to delete
     * @throws DataAccessException if category or category.id is null
     */
    public void deleteCategory(Category category) throws DataAccessException;

    /**
     * Get category by given id
     * @param id id of category
     * @return Category if found by id otherwise null
     * @throws DataAccessException if id is null
     */
    public Category getCategoryById(Long id) throws DataAccessException;

    /**
     * Get a list of all categories in DB
     * @return lits of all categories in DB
     */

    public List<Category> getAllCategories();
}
