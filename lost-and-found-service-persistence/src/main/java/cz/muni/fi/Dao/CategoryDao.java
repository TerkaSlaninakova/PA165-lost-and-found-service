package cz.muni.fi.Dao;

import cz.muni.fi.Entity.CategoryEntity;

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
    public void addCategory(CategoryEntity category);

    /**
     *
     * @param category
     */
    public void updateCategory(CategoryEntity category);

    /**
     *
     * @param category
     */
    public void deleteCategory(CategoryEntity category);

    /**
     *
     * @param id
     * @return
     */
    public CategoryEntity getCategoryById(Long id);

    /**
     * 
     * @return
     */
    public List<CategoryEntity> getAllCategories();

}
