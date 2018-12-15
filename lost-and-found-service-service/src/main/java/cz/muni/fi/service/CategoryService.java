package cz.muni.fi.service;

import cz.muni.fi.persistence.entity.Category;
import cz.muni.fi.service.exceptions.ServiceException;
import java.util.List;

/**
 * Service layer interface for Category
 * @author Terezia Slaninakova (445526)
 */
public interface CategoryService {
    /**
     * Save category to DB
     * @param category object to be saved
     * @throws ServiceException when category creation fails
     */
    void addCategory(Category category) throws ServiceException;

    /**
     * Update category in DB
     * @param category object to update
     * @throws ServiceException when category update fails
     */
    void updateCategory(Category category) throws ServiceException;

    /**
     * Delete category from db
     * @param category object to delete
     * @throws ServiceException when deletion fails
     */
    void deleteCategory(Category category) throws ServiceException;

    /**
     * Get category by given id
     * @param id id of category
     * @throws ServiceException when getting category by id fails
     * @return Category if found by id otherwise null
     */
    Category getCategoryById(Long id) throws ServiceException;

    /**
     * Get a list of all categories in DB
     * @throws ServiceException when get categories fails in persistence layer
     * @return lits of all categories in DB
     */

    List<Category> getAllCategories() throws ServiceException;
}
