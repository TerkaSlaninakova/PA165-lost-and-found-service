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
     * @param item - item to be added
     * @throws IllegalArgumentException when item is null or item already exists
     * */
    void addItem(Item item);

    /**
     * Delete an item.
     *
     * @param item - item to be deleted
     * @throws IllegalArgumentException when item is null or item doesn't exist (nothing to delete)
     */
    void deleteItem(Item item);

    /**
     * Find item with given id.
     *
     * @param id - id of item to be found
     * @return - item that was found or null if no item was found
     * @throws IllegalArgumentException when id is null
     * */
    Item getItembyId(Long id);

    /**
     * Find all archived items.
     *
     * @return - items that were found or Collections.EMPTY_LIST if no items were found
     * */
    List<Item> getAllItems();

    /**
     * Update given item.
     *
     * @param item - item to be updated
     * @throws IllegalArgumentException when item is null or  item is not persisted yet
     * */
    void updateItem(Item item);

}
