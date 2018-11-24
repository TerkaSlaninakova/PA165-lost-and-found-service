package cz.muni.fi.service;

import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.service.exceptions.ServiceException;

import java.util.List;

/**
 * Service layer interface for Location
 * @author Terezia Slaninakova (445526)
 */
public interface LocationService {

    /**
     * Save Location to DB
     * @param location object to be saved
     * @throws ServiceException if adding fails
     */
    void addLocation(Location location) throws ServiceException;

    /**
     * Update Location in DB
     * @param location object to update
     * @throws ServiceException if update fails
     */
    void updateLocation(Location location) throws ServiceException;

    /**
     * Delete Location from db
     * @param location object to delete
     * @throws ServiceException if deletion fails
     */
    void deleteLocation(Location location) throws ServiceException;

    /**
     * Get Location by given id
     * @param id id of Location
     * @throws ServiceException if get fails
     * @return Location if found by id otherwise null
     */
    Location getLocationById(Long id) throws ServiceException;

    /**
     * Get a list of all categories in DB
     * @throws ServiceException if get fails
     * @return lists of all categories in DB
     */
    List<Location> getAllLocations() throws ServiceException;
}
