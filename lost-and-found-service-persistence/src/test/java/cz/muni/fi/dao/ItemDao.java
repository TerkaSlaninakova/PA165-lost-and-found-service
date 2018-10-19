package cz.muni.fi.dao;

import cz.muni.fi.entity.Item;
import cz.muni.fi.entity.Status;
import cz.muni.fi.exceptions.*;
import sun.awt.image.ImageFormatException;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

// Neviem ci budeme pouzivat SQL databazu, kedtak nahradime SQLException niecim inym

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
     * @return - archived Item
     * @throws IllegalArgumentException when item is null
     * @throws ItemDaoException when item already exists
     * @throws SQLException when accessing DB failed
     * */
    Item createItem(Item item) throws ItemDaoException, SQLException;

    /**
     * Delete an item.
     *
     * @param item - item to be deleted
     * @throws IllegalArgumentException when item is null
     * @throws ItemDaoException when item doesn't exist (nothing to delete)
     * @throws SQLException when accessing DB failed
     */
    void deleteItem(Item item) throws ItemDaoException, SQLException;

    /**
     * Find item with given id.
     *
     * @param id - id of item to be found
     * @return - item that was found or null if no item was found
     * @throws IllegalArgumentException when id is null
     * @throws SQLException when accessing DB failed
     * */
    Item findItembyId(Long id) throws ItemDaoException;

    /**
     * Find all archived items.
     *
     * @return - items that were found or Collections.EMPTY_LIST if no items were found
     * @throws SQLException when accessing DB failed
     * */
    List<Item> getAllItems() throws SQLException;

    /**
     * Upload photo for given item.
     *
     * @param item - item whose photo will be updated
     * @param photo - new photo of item
     * @throws IllegalArgumentException when item or photo is null
     * @throws ImageFormatException when photo is not correct format or size
     * @throws SQLException when accessing DB failed
     * */
    void uploadPhoto(Item item, Image photo) throws ImageFormatException, SQLException;

    /**
     * Update status for given item.
     *
     * @param item - item whose status will be updated
     * @param status - new status of item
     * @throws IllegalArgumentException when item or status is null
     * @throws SQLException when accessing DB failed
     * */
    void updateStatus(Item item, Status status) throws SQLException;

    /**
     * Update found location for given item.
     *
     * @param item - item whose status will be updated
     * @param foundLocation - new found location of item
     * @throws IllegalArgumentException when item or foundLocation is null
     * @throws SQLException when accessing DB failed
     * */
    void setFoundLocation(Item item, String foundLocation) throws SQLException;

    /**
     * Update lost location for given item.
     *
     * @param item - item whose status will be updated
     * @param lostLocation - new lost location of item
     * @throws IllegalArgumentException when item or lostLocation is null
     * @throws SQLException when accessing DB failed
     * */
    void setLostLocation(Item item, String lostLocation) throws SQLException;

}
