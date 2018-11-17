package cz.muni.fi.service;

import cz.muni.fi.entity.Item;
import org.springframework.dao.DataAccessException;
import java.time.LocalDate;

import java.util.List;

/**
 * Service layer interface for Item
 * @author Terezia Slaninakova (445526)
 */
public interface ItemService {

    /**
     * Archives an item
     *
     * @param item - itemEntity to be archived
     * @throws IllegalArgumentException when itemEntity is null
     * @throws DataAccessException when itemEntity cannot be archived
     *
     * @return archived item
     * */
    Item archiveItem(Item item, LocalDate foundDate) throws DataAccessException;

    /**
     * Create a new item.
     *
     * @param item - itemEntity to be added
     * @throws IllegalArgumentException when itemEntity is null
     * @throws DataAccessException when itemEntity already exists
     * @return added item
     * */
    Item addItem(Item item) throws DataAccessException;

    /**
     * Delete an item.
     *
     * @param item - itemEntity to be deleted
     * @throws IllegalArgumentException when itemEntity is null
     * @throws DataAccessException when itemEntity doesn't exist (nothing to delete)
     * @return deleted item
     */
    Item deleteItem(Item item) throws DataAccessException;

    /**
     * Find item with given id.
     *
     * @param id - id of itemEntity to be found
     * @return - item that was found or null if no item was found
     * @throws IllegalArgumentException when id is null
     * */
    Item getItembyId(Long id) throws DataAccessException;

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
     * @throws DataAccessException when item is not persisted yet
     *
     * @return updated item
     * */
    Item updateItem(Item item) throws DataAccessException;
}
