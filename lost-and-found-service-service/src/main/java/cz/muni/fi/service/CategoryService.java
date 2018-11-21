package cz.muni.fi.service;

import cz.muni.fi.persistence.entity.Category;
import cz.muni.fi.service.exceptions.ServiceException;
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
    void addCategory(Category category) throws ServiceException;

    /**
     * Update category in DB
     * @param category object to update
     * @throws DataAccessException if category or category.id is null
     */
    void updateCategory(Category category) throws ServiceException;

    /**
     * Delete category from db
     * @param category object to delete
     * @throws DataAccessException if category or category.id is null
     */
    void deleteCategory(Category category) throws ServiceException;

    /**
     * Get category by given id
     * @param id id of category
     * @return Category if found by id otherwise null
     * @throws DataAccessException if id is null
     */
    Category getCategoryById(Long id) throws ServiceException;

    /**
     * Get a list of all categories in DB
     * @return lits of all categories in DB
     */

    List<Category> getAllCategories() throws ServiceException;
}
