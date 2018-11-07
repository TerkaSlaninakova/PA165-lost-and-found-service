package cz.muni.fi.dao;

import cz.muni.fi.entity.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 *
 * @author Jakub Polacek
 */
@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addCategory(Category category) throws IllegalArgumentException {
        if (category == null) {
            throw new IllegalArgumentException("Category");
        }
        if (category.getId() == null) {
            em.persist(category);
        }
    }

    @Override
    public void updateCategory(Category category) throws IllegalArgumentException {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Category or id null");
        }
        em.merge(category);
    }

    @Override
    public void deleteCategory(Category category) throws IllegalArgumentException {
        if (category == null || category.getId() == null) {
            throw new IllegalArgumentException("Category or id null");
        }
        em.remove(category);
    }

    @Override
    public Category getCategoryById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Null id");
        }
        return em.find(Category.class, id);
    }

    @Override
    public List<Category> getAllCategories() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }
}
