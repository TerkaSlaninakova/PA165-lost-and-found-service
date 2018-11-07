package cz.muni.fi;

import cz.muni.fi.dao.ItemDao;
import cz.muni.fi.dao.LocationDao;
import cz.muni.fi.entity.Item;
import cz.muni.fi.entity.Location;
import cz.muni.fi.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertEquals;


/**
 * @author TerkaSlaninakova
 */

@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class LocationDaoImplTest extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LocationDao locationDao;
    private static Location location;



    @BeforeMethod
    public void setup() {

        location = new Location();
        location.setDescription("Found at a train station");
    }


    @Test
    public void testGetAllLocationsWhenEmpty() {
        assertEquals(locationDao.getAllLocations().size(), 0);
    }

    @Test
    public void testAddLocation() {
        locationDao.addLocation(location);
        assertEquals(location, entityManager.find(Location.class, location.getId()));
    }


    @Test
    public void testUpdateLocation() {
        entityManager.persist(location);
        Location modified = location;
        String newDescription = "Found near a swimming pool";
        modified.setDescription(newDescription);

        locationDao.updateLocation(location);

        assertEquals(location, entityManager.find(Location.class, location.getId()));
    }



    @Test
    public void testDeleteLocation() {
        entityManager.persist(location);
        locationDao.deleteLocation(location);
        assertNull(entityManager.find(Location.class, location.getId()));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullLocation() {
        locationDao.addLocation(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNullLocation() {
        locationDao.updateLocation(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNullLocation() {
        locationDao.deleteLocation(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetNullLocationById() {
        locationDao.getLocationById(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNullIdLocation() {
        entityManager.persist(location);
        location.setId(null);
        locationDao.deleteLocation(location);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullIdLocation() {
        entityManager.persist(location);
        location.setId(null);
        locationDao.updateLocation(location);
    }


}