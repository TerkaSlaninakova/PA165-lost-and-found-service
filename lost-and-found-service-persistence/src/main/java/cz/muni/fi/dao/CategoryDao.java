package cz.muni.fi.dao;

import cz.muni.fi.entity.CategoryEntity;

import java.util.List;

/**
 *
 * @author Jakub Polacek
 */
public interface CategoryDao {

    /**
     *
     * @param category
     */
    public void addCategory(CategoryEntity category) throws IllegalArgumentException;

    /**
     *
     * @param category
     */
    public void updateCategory(CategoryEntity category) throws IllegalArgumentException;

    /**
     *
     * @param category
     */
    public void deleteCategory(CategoryEntity category) throws IllegalArgumentException;

    /**
     *
     * @param id
     * @return
     */
    public CategoryEntity getCategoryById(Long id) throws IllegalArgumentException;

    /**
     *
     * @return
     */
    public List<CategoryEntity> getAllCategories();

}
