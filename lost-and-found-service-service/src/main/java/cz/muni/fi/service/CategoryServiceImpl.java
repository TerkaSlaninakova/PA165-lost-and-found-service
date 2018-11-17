package cz.muni.fi.service;

import cz.muni.fi.dao.CategoryDao;
import cz.muni.fi.entity.Category;
import cz.muni.fi.service.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Category
 * @author Terezia Slaninakova (445526)
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public void addCategory(Category category) throws IllegalArgumentException {
        if (category == null) {
            throw new IllegalArgumentException("Category");
        }
        try {
            categoryDao.addCategory(category);
        } catch (Throwable e) {
            throw new ServiceException("Could not add category.", e);
        }
    }

    @Override
    public void updateCategory(Category category) throws IllegalArgumentException {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Category or id null");
        }
        try {
            categoryDao.updateCategory(category);
        } catch (Throwable e) {
            throw new ServiceException("Could not update category.", e);
        }
    }

    @Override
    public void deleteCategory(Category category) throws IllegalArgumentException {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Category or id null");
        }
        try {
            categoryDao.deleteCategory(category);
        } catch (Throwable e) {
            throw new ServiceException("Could not delete category.", e);
        }
    }

    @Override
    public Category getCategoryById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Null id");
        }
        try {
            return categoryDao.getCategoryById(id);
        } catch (Throwable e) {
            throw new ServiceException("Could not get category by id.", e);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return categoryDao.getAllCategories();
        } catch (Throwable e) {
            throw new ServiceException("Could not get all categories.", e);
        }
    }
}
