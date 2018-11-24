package cz.muni.fi.service;

import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.exceptions.ServiceException;

import java.time.LocalDate;
import java.util.List;

/**
 * Service layer interface for Item
 *
 * @author Terezia Slaninakova (445526)
 */
public interface ItemService {

    /**
     * Archives an item
     *
     * @param item - itemEntity to be archived
     * @return archived item
     * @throws ServiceException when itemEntity cannot be archived or  itemEntity is null
     */
    void archiveItem(Item item) throws ServiceException;

    /**
     * Resolves lost item
     *
     * @param item - itemEntity which was previously lost
     * @return resolved item
     * @throws ServiceException when itemEntity is null or status is not CLAIM_RECEIVED_LOST
     */
    Item resolveLostItem(Item item, LocalDate foundDate, Location foundLocation) throws ServiceException;

    /**
     * Resolves found item
     *
     * @param item - itemEntity which was previously found
     * @return resolved item
     * @throws ServiceException when itemEntity is null or status is not CLAIM_RECEIVED_LOST
     */
    Item resolveFoundItem(Item item, LocalDate lostDate, Location lostLocation, User owner) throws ServiceException;

    /**
     * Gets all items with a specific status
     *
     * @param status - status which to filter based upon
     * @return archived item
     * @throws ServiceException when items cannot be get or when itemEntity is null or status is not CLAIM_RECEIVED_LOST
     */
    List<Item> getAllItemsWithStatus(Status status) throws ServiceException;

    /**
     * Create a new item.
     *
     * @param item - itemEntity to be added
     * @return added item
     * @throws ServiceException when add fails
     */
    void addItem(Item item) throws ServiceException;

    /**
     * Delete an item.
     *
     * @param item - itemEntity to be deleted
     * @return deleted item
     * @throws ServiceException when delete fails
     */
    void deleteItem(Item item) throws ServiceException;

    /**
     * Find item with given id.
     *
     * @param id - id of itemEntity to be found
     * @return - item that was found or null if no item was found
     * @throws ServiceException when get item fails
     */
    Item getItemById(Long id) throws ServiceException;

    /**
     * Get all items.
     *
     * @return - items that were found or Collections.EMPTY_LIST if no items were found
     * @throws ServiceException when all items get fails
     */
    List<Item> getAllItems() throws ServiceException;

    /**
     * Update given item.
     *
     * @param item - itemEntity to be updated
     * @return updated item
     * @throws ServiceException when update fails
     */
    void updateItem(Item item) throws ServiceException;

    /**
     * Add category to item
     *
     * @param itemId     of item to add category
     * @param categoryId of category to add
     * @throws ServiceException when category cannot be added, doesn't exis, or null arguments
     */
    void addCategoryToItem(Long itemId, Long categoryId) throws ServiceException;


    /**
     * Remove category from item
     *
     * @param itemId     of item to add category
     * @param categoryId of category to remove
     * @throws ServiceException when category cannot be removed, doesn't exist. or item does not have category or null arguments
     */
    void removeCategoryFromItem(Long itemId, Long categoryId) throws ServiceException;


    /**
     * Changes found location to location or null if locationId null
     *
     * @param itemId     of item to change location
     * @param locationId null or location id
     * @throws ServiceException when location with given id when id not null doesn't exist. or null itemId
     */
    void changeFoundLocation(Long itemId, Long locationId) throws ServiceException;

    /**
     * Changes lost location to location or null if locationId null
     *
     * @param itemId     of item to change location
     * @param locationId null or location id
     * @throws ServiceException when location with given id when id not null doesn't exist. or null itemId
     */
    void changeLostLocation(Long itemId, Long locationId) throws ServiceException;

    /**
     * Changes user to user or null if userId null
     *
     * @param itemId of item to change location
     * @param userId null or user id
     * @throws ServiceException when user with given id when id not null doesn't exist. or null itemId
     */
    void changeUser(Long itemId, Long userId) throws ServiceException;


}
