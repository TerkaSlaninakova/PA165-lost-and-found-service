package cz.muni.fi.service;

import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.service.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Service layer interface for Location
 * @author Terezia Slaninakova (445526)
 */
public interface LocationService {

    /**
     * Save Location to DB
     * @param location object to be saved
     * @throws DataAccessException if Location is null
     */
    void addLocation(Location location) throws ServiceException;

    /**
     * Update Location in DB
     * @param location object to update
     * @throws DataAccessException if Location or Location.id is null
     */
    void updateLocation(Location location) throws ServiceException;

    /**
     * Delete Location from db
     * @param location object to delete
     * @throws DataAccessException if Location or Location.id is null
     */
    void deleteLocation(Location location) throws ServiceException;

    /**
     * Get Location by given id
     * @param id id of Location
     * @return Location if found by id otherwise null
     * @throws DataAccessException if id is null
     */
    Location getLocationById(Long id) throws ServiceException;

    /**
     * Get a list of all categories in DB
     * @return lists of all categories in DB
     */
    List<Location> getAllLocations() throws ServiceException;
}
