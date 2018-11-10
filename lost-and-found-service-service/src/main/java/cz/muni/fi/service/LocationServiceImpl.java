package cz.muni.fi.service;

import cz.muni.fi.dao.LocationDao;
import cz.muni.fi.entity.Location;
import cz.muni.fi.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Terezia Slaninakova (445526)
 */
//@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    private Logger logger = Logger.getLogger(LocationServiceImpl.class.getName());

    public void addLocation(Location location) throws DataAccessException {
        if (location == null) {
            throw new IllegalArgumentException("Location");
        }
        try {
            locationDao.addLocation(location);
        }
        catch (Throwable t) {
            throw new ServiceException("Couldn't create location: " + location.getDescription(), t);
        }

    }

    public void updateLocation(Location location) throws DataAccessException {
        if (location == null || location.getId() == null) {
            throw new IllegalArgumentException("Location or id null");
        }
        try {
            locationDao.updateLocation(location);
        }
        catch (Throwable t) {
            throw new ServiceException("Couldn't update location: " + location.getId(), t);
        }
    }

    public void deleteLocation(Location location) throws DataAccessException {
        if (location == null || location.getId() == null) {
            throw new IllegalArgumentException("Location or id null");
        }
        try {
            locationDao.deleteLocation(location);
        }
        catch (Throwable t) {
            throw new ServiceException("Couldn't delete location: " + location.getId(), t);
        }
    }

    public Location getLocationById(Long id) throws DataAccessException {
        if (id == null) {
            throw new IllegalArgumentException("Null id");
        }
        try {
            return locationDao.getLocationById(id);
        }
        catch (Throwable t) {
            throw new ServiceException("Couldn't get location by id: " + id, t);
        }
    }

    public List<Location> getAllLocations() {
        try {
            return locationDao.getAllLocations();
        }
        catch (Throwable t) {
            throw new ServiceException("Couldn't get all locations ", t);
        }
    }

}
