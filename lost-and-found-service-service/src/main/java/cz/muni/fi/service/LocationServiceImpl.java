package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.LocationDao;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.service.exceptions.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Service layer implementation for Location
 *
 * @author Augustin Nemec
 */

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;


    @Override
    public void addLocation(Location location) throws DataAccessException {
        try {
            locationDao.addLocation(location);
        } catch (Exception e) {
            throw new ServiceException("Could not add location!", e);
        }
    }

    @Override
    public void updateLocation(Location location) throws DataAccessException {
        try {
            locationDao.updateLocation(location);
        } catch (Exception e) {
            throw new ServiceException("Could not update location!", e);
        }
    }

    @Override
    public void deleteLocation(Location location) throws DataAccessException {
        try {
            locationDao.deleteLocation(location);
        } catch (Exception e) {
            throw new ServiceException("Could not delete location!", e);
        }
    }

    @Override
    public Location getLocationById(Long id) throws DataAccessException {
        try {
            return locationDao.getLocationById(id);
        } catch (Exception e) {
            throw new ServiceException("Could not get location by id!", e);
        }
    }

    @Override
    public List<Location> getAllLocations() {
        try {
            return locationDao.getAllLocations();
        } catch (Exception e) {
            throw new ServiceException("Could not add location!", e);
        }
    }
}
