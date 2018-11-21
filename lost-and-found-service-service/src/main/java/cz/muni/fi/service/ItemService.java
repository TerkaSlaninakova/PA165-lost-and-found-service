package cz.muni.fi.service;

import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.exceptions.ServiceException;
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
    Item archiveItem(Item item) throws ServiceException;

    /**
     * Resolves lost item
     *
     * @param item - itemEntity which was previously lost
     * @throws IllegalArgumentException when itemEntity is null or status is not CLAIM_RECEIVED_LOST
     * @throws DataAccessException when itemEntity cannot be archived
     *
     * @return archived item
     * */
    Item resolveLostItem(Item item, LocalDate foundDate, Location foundLocation) throws ServiceException;

    /**
     * Resolves found item
     *
     * @param item - itemEntity which was previously found
     * @throws IllegalArgumentException when itemEntity is null or status is not CLAIM_RECEIVED_LOST
     * @throws DataAccessException when itemEntity cannot be archived
     *
     * @return archived item
     * */
    Item resolveFoundItem(Item item, LocalDate lostDate, Location lostLocation, User owner) throws ServiceException;

    /**
     * Gets all items with a specific status
     *
     * @param status - status which to filter based upon
     * @throws IllegalArgumentException when itemEntity is null or status is not CLAIM_RECEIVED_LOST
     * @throws DataAccessException when itemEntity cannot be archived
     *
     * @return archived item
     * */
    List<Item> getAllItemsWithStatus(Status status) throws ServiceException;
    /**
     * Create a new item.
     *
     * @param item - itemEntity to be added
     * @throws IllegalArgumentException when itemEntity is null
     * @throws DataAccessException when itemEntity already exists
     * @return added item
     * */
    Item addItem(Item item) throws ServiceException;

    /**
     * Delete an item.
     *
     * @param item - itemEntity to be deleted
     * @throws IllegalArgumentException when itemEntity is null
     * @throws DataAccessException when itemEntity doesn't exist (nothing to delete)
     * @return deleted item
     */
    Item deleteItem(Item item) throws ServiceException;

    /**
     * Find item with given id.
     *
     * @param id - id of itemEntity to be found
     * @return - item that was found or null if no item was found
     * @throws IllegalArgumentException when id is null
     * */
    Item getItembyId(Long id) throws ServiceException;

    /**
     * Find all archived items.
     *
     * @return - items that were found or Collections.EMPTY_LIST if no items were found
     * */
    List<Item> getAllItems() throws ServiceException;

    /**
     * Update given item.
     *
     * @param item - itemEntity to be updated
     * @throws IllegalArgumentException when itemEntity is null
     * @throws DataAccessException when item is not persisted yet
     *
     * @return updated item
     * */
    Item updateItem(Item item) throws ServiceException;
}
