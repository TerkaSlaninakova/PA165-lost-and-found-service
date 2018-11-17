package cz.muni.fi.persistence.dao;

import cz.muni.fi.persistence.entity.Location;

import java.util.List;

/**
 *
 * @author Jakub Polacek
 */
public interface LocationDao {

    /**
     * Save Location to DB
     * @param location object to be saved
     * @throws IllegalArgumentException if Location is null or already persisted
     */
    public void addLocation(Location location) throws IllegalArgumentException;

    /**
     * Update Location in DB
     * @param location object to update
     * @throws IllegalArgumentException if Location or Location.id is null
     */
    public void updateLocation(Location location) throws IllegalArgumentException;

    /**
     * Delete Location from db
     * @param location object to delete
     * @throws IllegalArgumentException if Location or Location.id is null
     */
    public void deleteLocation(Location location) throws IllegalArgumentException;

    /**
     * Get Location by given id
     * @param id id of Location
     * @return Location if found by id otherwise null
     * @throws IllegalArgumentException if id is null
     */
    public Location getLocationById(Long id) throws IllegalArgumentException;

    /**
     * Get a list of all categories in DB
     * @return lits of all categories in DB
     */

    public List<Location> getAllLocations();

}
