package cz.muni.fi;

import cz.muni.fi.dao.LocationDao;
import cz.muni.fi.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;


/**
 * @author TerkaSlaninakova
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class LocationDaoImplTest extends AbstractTestNGSpringContextTests {
    
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private LocationDao locationDao;

    private static Location trainStation, busStation;


    @BeforeMethod
    public void testSetup()  {
        trainStation = new Location();
        trainStation.setDescription("Found at a train station");

        busStation = new Location();
        busStation.setDescription("At a bus station");
    }

    @Test
    public void shouldReturn0LocationsWhenEmpty()  {
        assertEquals(locationDao.getAllLocations().size(), 0);
        assertNull(locationDao.getLocationById(0L));
    }

    @Test
    public void shouldAddLocation()  {
        em.persist(trainStation);
        assertEquals(locationDao.getAllLocations().size(), 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldNotCreateAdditionalLocationIfTheSameOneAdded()  {
        em.persist(trainStation);
        locationDao.addLocation(trainStation);
        assertEquals(em.createQuery("select l from Location l", Location.class)
                .getResultList().size(), 1);
        em.remove(trainStation);
        assertEquals(em.createQuery("select l from Location l", Location.class)
                .getResultList().size(), 0);
        locationDao.addLocation(new Location());
        assertEquals(locationDao.getAllLocations().size(), 1);
    }

    @Test
    public void ShouldUpdateLocation()  {
        em.persist(trainStation);
        String newDescription = "Found near a swimming pool";
        trainStation.setDescription(newDescription);

        locationDao.updateLocation(trainStation);

        Location updatedLocation = em.find(Location.class, trainStation.getId());

        assertEquals(updatedLocation.getDescription(), newDescription);
    }

    @Test
    public void ShouldUpdateLocationWhenNoChange()  {
        em.persist(trainStation);
        locationDao.updateLocation(trainStation);

        Location updatedLocation = em.find(Location.class, trainStation.getId());

        assertEquals(trainStation, updatedLocation);
    }

    @Test
    public void ShouldDeleteLocation()  {
        em.persist(trainStation);
        em.persist(busStation);
        assertEquals(em.createQuery("select l from Location l", Location.class)
                .getResultList().size(), 2);

        locationDao.deleteLocation(busStation);
        assertEquals(em.createQuery("select l from Location l", Location.class)
                .getResultList().size(), 1);

        assertNull(em.find(Location.class, busStation.getId()));
        assertEquals(em.find(Location.class, trainStation.getId()), trainStation);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldFailOnAddNullLocation()  {
         locationDao.addLocation(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldFailOnAddNullUpdate()  {
         locationDao.updateLocation(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldFailOnAddNullDelete()  {
         locationDao.deleteLocation(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldFailOnAddNullGetById()  {
         locationDao.getLocationById(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void deleteNullIdLocation()  {
         locationDao.deleteLocation(trainStation);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNullIdLocation()  {
         locationDao.updateLocation(trainStation);
    }
    

}