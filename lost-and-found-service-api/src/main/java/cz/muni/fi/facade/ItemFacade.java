package cz.muni.fi.facade;

import cz.muni.fi.dto.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Facade interface for Item
 * @author Jakub Polacek
 */
public interface ItemFacade {

    /**
     * Creates item that has been lost
     *
     * @param itemCreateDTO item to create
     */
    void addItemLost(ItemCreateDTO itemCreateDTO);

    /**
     * Creates item that has been found
     *
     * @param itemCreateDTO item to create
     */
    void addItemFound(ItemCreateDTO itemCreateDTO);

    /**
     * Updates Item
     * @param itemDTO itemDTO to update
     */
    void updateItem(ItemDTO itemDTO);

    /**
     * Adds category to item
     *
     * @param itemId id of item which gets category added
     * @param categoryId category to add
     */
    void addCategoryToItem(Long itemId, Long categoryId);


    /**
     * Removes category from item
     *
     * @param itemId id of item which gets category removed
     * @param categoryId category to remove
     */
    void removeCategoryFromItem(Long itemId, Long categoryId);


    /**
     * Changes found location to location or null if locationId null
     *
     * @param itemId id of item to change location for
     * @param locationId null or location id
     */
    void changeFoundLocation(Long itemId, Long locationId);

    /**
     * Changes lost location to location or null if locationId null
     *
     * @param itemId id of item to change location for
     * @param locationId null or location id
     */
    void changeLostLocation(Long itemId, Long locationId);

    /**
     * Changes owner of an item to user or null if userId null
     *
     * @param itemId id of item
     * @param userId null or user id
     */
    void changeOwner(Long itemId, Long userId);

    /**
     * Deletes item
     *
     * @param itemDTO to delete
     */
    void deleteItem(ItemDTO itemDTO);

    /**
     * Gets all items
     *
     * @return all items
     */
    List<ItemDTO> getAllItems();

    /**
     * Returns items with given category name
     *
     * @param categoryName name of category to look up
     * @return list of items with categories with given name
     */
    List<ItemDTO> getItemsByCategory(String categoryName);

    /**
     * Returns item by id
     *
     * @param id of item
     * @return itemDTO
     */
    ItemDTO getItemById(Long id);

    /**
     * Archives an item
     *
     * @param item - itemEntity to be archived
     * @throws IllegalArgumentException when itemEntity is null
     */
    void archiveItem(ItemDTO item);

    /**
     * Resolves lost itemDTO
     *
     * @param itemDTO - itemEntity which was previously lost
     */
    void resolveLostItem(ItemDTO itemDTO, LocalDate foundDate, LocationDTO foundLocation);

    /**
     * Resolves found itemDTO
     *
     * @param itemDTO - which was previously found
     * @throws IllegalArgumentException when itemEntity is null or status is not CLAIM_RECEIVED_LOST
     */
    void resolveFoundItem(ItemDTO itemDTO, LocalDate lostDate, LocationDTO lostLocation, UserDTO owner);


    /**
     * Changes item's picture
     *
     * @param itemChangeDTO change picture of item
     */
    void changeImage(ItemChangeImageDTO itemChangeDTO);
}
