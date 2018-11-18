package cz.muni.fi.persistence.dao;

import cz.muni.fi.persistence.entity.Item;

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
     * @throws IllegalArgumentException when itemEntity already exists
     * */
    Item addItem(Item item) throws IllegalArgumentException;

    /**
     * Delete an item.
     *
     * @param item - itemEntity to be deleted
     * @throws IllegalArgumentException when itemEntity is null
     * @throws IllegalArgumentException when itemEntity doesn't exist (nothing to delete)
     */
    Item deleteItem(Item item) throws IllegalArgumentException;

    /**
     * Find item with given id.
     *
     * @param id - id of itemEntity to be found
     * @return - item that was found or null if no item was found
     * @throws IllegalArgumentException when id is null
     * */
    Item getItembyId(Long id) throws IllegalArgumentException;

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
     * @throws IllegalArgumentException when item is not persisted yet
     * */
    Item updateItem(Item item) throws IllegalArgumentException;

}
