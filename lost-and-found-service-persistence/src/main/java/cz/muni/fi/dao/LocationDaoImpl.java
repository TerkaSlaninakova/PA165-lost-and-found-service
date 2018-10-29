package cz.muni.fi.dao;

import cz.muni.fi.entity.Location;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 *
 * @author Jakub Polacek
 */
@Stateful
public class LocationDaoImpl implements LocationDao {

    @PersistenceContext(unitName = "location-unit", type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public void addLocation(Location location) throws IllegalArgumentException {
        if (location == null) {
            throw new IllegalArgumentException("Location");
        }
        if (location.getId() == null) {
            em.persist(location);
        }
    }

    public void updateLocation(Location location) throws IllegalArgumentException {
        if (location == null || location.getId() == null) {
            throw new IllegalArgumentException("Location or id null");
        }
        em.merge(location);
    }

    public void deleteLocation(Location location) throws IllegalArgumentException {
        if (location == null || location.getId() == null) {
            throw new IllegalArgumentException("Location or id null");
        }
        em.remove(location);
    }

    public Location getLocationById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Null id");
        }
        return em.find(Location.class, id);
    }

    public List<Location> getAllLocations() {
        return em.createQuery("select l from Location l", Location.class)
                .getResultList();
    }
}
