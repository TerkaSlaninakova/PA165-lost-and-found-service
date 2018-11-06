package cz.muni.fi.dao;

import cz.muni.fi.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 *
 * @author Jakub Polacek
 */
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext(unitName = "category-unit", type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public void addCategory(Category category) throws IllegalArgumentException {
        if (category == null) {
            throw new IllegalArgumentException("Category");
        }
        if (category.getId() == null) {
            em.persist(category);
        }
    }

    public void updateCategory(Category category) throws IllegalArgumentException {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Category or id null");
        }
        em.merge(category);
    }

    public void deleteCategory(Category category) throws IllegalArgumentException {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Category or id null");
        }
        em.remove(category);
    }

    public Category getCategoryById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Null id");
        }
        return em.find(Category.class, id);
    }

    public List<Category> getAllCategories() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }
}
