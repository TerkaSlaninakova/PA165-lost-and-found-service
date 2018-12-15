package cz.muni.fi.persistence.dao;

import cz.muni.fi.persistence.PersistenceApplicationContext;
import cz.muni.fi.persistence.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


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
    private static Location trainStation, busStation;



    @BeforeMethod
    public void setup() {

        trainStation = new Location();
        trainStation.setDescription("Found at a train station");

        busStation = new Location();
        busStation.setDescription("Found at a bus station");
    }


    @Test
    public void testGetAllLocationsWhenEmpty() {
        assertEquals(locationDao.getAllLocations().size(), 0);
    }

    @Test
    public void testAddLocation() {
        locationDao.addLocation(trainStation);
        assertEquals(trainStation, entityManager.find(Location.class, trainStation.getId()));
    }


    @Test
    public void testUpdateLocation() {
        entityManager.persist(trainStation);
        Location modified = trainStation;
        String newDescription = "Found near a swimming pool";
        modified.setDescription(newDescription);
        locationDao.updateLocation(trainStation);
        assertEquals(trainStation, entityManager.find(Location.class, trainStation.getId()));
        assertEquals(trainStation.getDescription(),"Found near a swimming pool");
    }



    @Test
    public void testDeleteLocation() {
        entityManager.persist(trainStation);
        locationDao.deleteLocation(trainStation);
        assertNull(entityManager.find(Location.class, trainStation.getId()));
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
        entityManager.persist(trainStation);
        trainStation.setId(null);
        locationDao.deleteLocation(trainStation);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullIdLocation() {
        entityManager.persist(trainStation);
        trainStation.setId(null);
        locationDao.updateLocation(trainStation);
    }


    @Test
    public void testGetAllCategories() {
        entityManager.persist(trainStation);
        entityManager.persist(busStation);

        List<Location> result = locationDao.getAllLocations();
        assertEquals(2, result.size());
        assertTrue(result.contains(trainStation));
        assertTrue(result.contains(busStation));

    }

    @Test
    public void testGetCategoryById() {
        entityManager.persist(trainStation);
        AssertJUnit.assertEquals(trainStation, locationDao.getLocationById(trainStation.getId()));
    }
}