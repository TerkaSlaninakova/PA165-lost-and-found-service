package cz.muni.fi.dao;

import cz.muni.fi.entity.Location;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 *
 * @author Jakub Polacek
 */

@Repository
@Transactional
public class LocationDaoImpl implements LocationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addLocation(Location location) throws IllegalArgumentException {
        if (location == null) {
            throw new IllegalArgumentException("Location");
        }
        if (location.getId() == null) {
            em.persist(location);
        }
    }

    @Override
    public void updateLocation(Location location) throws IllegalArgumentException {
        if (location == null || location.getId() == null) {
            throw new IllegalArgumentException("Location or id null");
        }
        em.merge(location);
    }

    @Override
    public void deleteLocation(Location location) throws IllegalArgumentException {
        if (location == null || location.getId() == null) {
            throw new IllegalArgumentException("Location or id null");
        }
        em.remove(location);
    }

    @Override
    public Location getLocationById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Null id");
        }
        return em.find(Location.class, id);
    }

    @Override
    public List<Location> getAllLocations() {
        return em.createQuery("select l from Location l", Location.class)
                .getResultList();
    }
}
