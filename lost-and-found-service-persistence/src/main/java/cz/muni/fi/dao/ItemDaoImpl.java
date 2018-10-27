package cz.muni.fi.dao;

import cz.muni.fi.entity.Item;
import cz.muni.fi.exceptions.ItemDaoException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Augustin Nemec
 */
public class ItemDaoImpl implements ItemDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createItem(Item item) throws ItemDaoException {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        try {
            em.persist(item);
        } catch (EntityExistsException e) {
            throw new ItemDaoException("Item already exists");
        }

    }

    @Override
    public void deleteItem(Item item) throws ItemDaoException {
        if (item == null || item.getId() == null) {
            throw new IllegalArgumentException("Item is null");
        }
        try {
            em.remove(item);
        } catch (IllegalArgumentException e) {
            throw new ItemDaoException("Nothing to remove");
        }

    }

    @Override
    public Item findItembyId(Long id) throws ItemDaoException {
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
    public void updateItem(Item item) throws ItemDaoException {
        if (item == null || item.getId() == null) {
            throw new IllegalArgumentException("Item is null");
        }
        try {
        em.merge(item);
        } catch (IllegalArgumentException e) {
            throw new ItemDaoException("Item does not exist");
        }
    }
}
