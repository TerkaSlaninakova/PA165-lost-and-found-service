package cz.muni.fi.dao;

import cz.muni.fi.entity.Item;
import cz.muni.fi.exceptions.*;

import java.util.List;

/**
 * Data Access Object for {@link Item}.
 *
 * @author Augustin Nemec
 */
public interface ItemDao {

    /**
     * Create a new item.
     *
     * @param item - itemEntity to be added
     * @throws IllegalArgumentException when itemEntity is null
     * @throws ItemDaoException when itemEntity already exists
     * */
    void addItem(Item item) throws ItemDaoException;

    /**
     * Delete an item.
     *
     * @param item - itemEntity to be deleted
     * @throws IllegalArgumentException when itemEntity is null
     * @throws ItemDaoException when itemEntity doesn't exist (nothing to delete)
     */
    void deleteItem(Item item) throws ItemDaoException;

    /**
     * Find item with given id.
     *
     * @param id - id of itemEntity to be found
     * @return - item that was found or null if no item was found
     * @throws IllegalArgumentException when id is null
     * */
    Item getItembyId(Long id) throws ItemDaoException;

    /**
     * Find all archived items.
     *
     * @return - items that were found or Collections.EMPTY_LIST if no items were found
     * */
    List<Item> getAllItems();

    /**
     * Update given item.
     *
     * @param item - itemEntity to be updated
     * @throws IllegalArgumentException when itemEntity is null
     * @throws ItemDaoException when item is not persisted yet
     * */
    void updateItem(Item item) throws ItemDaoException;

}
