package cz.muni.fi.dao;

import cz.muni.fi.entity.CategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager em;

    public void addCategory(CategoryEntity category) throws IllegalArgumentException {
        if (category == null) {
            throw new IllegalArgumentException("Category");
        }
        em.persist(category);
    }

    public void updateCategory(CategoryEntity category) throws IllegalArgumentException {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Category or id null");
        }
        em.merge(category);
    }

    public void deleteCategory(CategoryEntity category) throws IllegalArgumentException {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Category or id null");
        }
        em.remove(category);
    }

    public CategoryEntity getCategoryById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Null id");
        }
        return em.find(CategoryEntity.class, id);
    }

    public List<CategoryEntity> getAllCategories() {
        return em.createQuery("select c from Category c", CategoryEntity.class)
                .getResultList();
    }
}
