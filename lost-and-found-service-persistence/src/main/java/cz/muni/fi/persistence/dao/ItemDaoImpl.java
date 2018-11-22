package cz.muni.fi.persistence.dao;

import cz.muni.fi.persistence.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Augustin Nemec
 */
@Repository
public class ItemDaoImpl implements ItemDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        try {
            em.persist(item);
        } catch (EntityExistsException e) {
            throw new IllegalArgumentException("Item already exists");
        }
    }

    @Override
    public void deleteItem(Item item) {
        if (item == null || item.getId() == null) {
            throw new IllegalArgumentException("Item is null");
        }
        try {
            em.remove(item);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Nothing to remove");
        }
    }

    @Override
    public Item getItembyId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Item's id is null");
        }
        return em.find(Item.class, id);
    }

    @Override
    public List<Item> getAllItems() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    @Override
    public void updateItem(Item item) {
        if (item == null || item.getId() == null) {
            throw new IllegalArgumentException("Item is null");
        }
        try {
            em.merge(item);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Item does not exist");
        }
    }
}
