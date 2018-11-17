package cz.muni.fi.facade;

import cz.muni.fi.dto.ItemChangeImageDTO;
import cz.muni.fi.dto.ItemCreateDTO;
import cz.muni.fi.dto.ItemDTO;

import java.util.List;

 interface ItemFacade {

    /**
     * creates item
     * @param i item to create
     * @return id of created Item
     */
     Long createItem(ItemCreateDTO i);

    /**
     * Adds category to item
     * @param itemId of item which gets category added
     * @param categoryId category to add
     */
     void addCategory(Long itemId, Long categoryId);


    /**
     * Removes category from item
     * @param itemId of item which gets category removed
     * @param categoryId category to remove
     */
     void removeCategory(Long itemId, Long categoryId);


    /**
     * Changes found location to location or null if locationId null
     * @param itemId of item to change location
     * @param locationId null or location id
     */
     void changeFoundLocation(Long itemId, Long locationId);

     /**
      * Changes lost location to location or null if locationId null
      * @param itemId of item to change location
      * @param locationId null or location id
      */
     void changeLostLocation(Long itemId, Long locationId);

     /**
      * Changes user to user or null if userId null
      * @param itemId of item to change location
      * @param userId null or user id
      */
     void changeUser(Long itemId, Long userId);

    /**
     * Deletes item
     * @param itemId of item to delete
     */
     void deleteItem(Long itemId);

    /**
     * @return all items
     */
     List<ItemDTO> getAllItems();

    /**
     * Returns items with given category name
     * @param categoryName name of category to look up
     * @return list of items with categories with given name
     */
     List<ItemDTO> getItemsByCategory(String categoryName);

    /**
     * return item by id
     * @param id of item
     * @return itemDTO
     */
     ItemDTO getItemWithId(Long id);

    /**
     * Changes item's picture
     * @param itemChange change picture of item
     */
     void changeImage(ItemChangeImageDTO itemChange);
}
