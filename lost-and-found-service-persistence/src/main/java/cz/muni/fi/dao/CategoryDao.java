package cz.muni.fi.dao;

import cz.muni.fi.entity.Category;

import java.util.List;

/**
 *
 * @author Jakub Polacek
 */
public interface CategoryDao {

    /**
     * Save category to DB
     * @param category object to be saved
     * @throws IllegalArgumentException if category is null or category already exists
     */
    public void addCategory(Category category) throws IllegalArgumentException;

    /**
     * Update category in DB
     * @param category object to update
     * @throws IllegalArgumentException if category or category.id is null
     */
    public void updateCategory(Category category) throws IllegalArgumentException;

    /**
     * Delete category from db
     * @param category object to delete
     * @throws IllegalArgumentException if category or category.id is null
     */
    public void deleteCategory(Category category) throws IllegalArgumentException;

    /**
     * Get category by given id
     * @param id id of category
     * @return Category if found by id otherwise null
     * @throws IllegalArgumentException if id is null
     */
    public Category getCategoryById(Long id) throws IllegalArgumentException;

    /**
     * Get a list of all categories in DB
     * @return lits of all categories in DB
     */

    public List<Category> getAllCategories();

}
