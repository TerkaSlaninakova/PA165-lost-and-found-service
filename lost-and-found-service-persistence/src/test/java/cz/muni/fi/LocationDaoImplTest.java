package cz.muni.fi;

import cz.muni.fi.dao.ItemDao;
import cz.muni.fi.dao.LocationDao;
import cz.muni.fi.entity.Item;
import cz.muni.fi.entity.Location;
import cz.muni.fi.entity.Status;
import cz.muni.fi.exceptions.ItemDaoException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.NoSuchEJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import java.util.List;
import java.util.Properties;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author TerkaSlaninakova
 */
public class LocationDaoImplTest {

    private static Context context;
    private static Properties p;

    private static LocationDao locationDao;
    private static Location location, locationWithItems;
    private static ItemDao itemDao;
    private static Item notebook;

    /**
     *
     * Create database connection
     */
    @BeforeClass
    public static void suiteSetup() {
        p = new Properties();
        p.put("locationDatabase", "new://Resource?type=DataSource");
        p.put("locationDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("locationDatabase.JdbcUrl", "jdbc:hsqldb:mem:Locationdb");

        context = EJBContainer.createEJBContainer(p).getContext();
    }

    /**
     * Create mock objects for testing
     * @throws Exception when something goes horribly wrong
     */
    @Before
    public void testSetup() throws Exception {
        locationDao = (LocationDao) context.lookup("java:global/lost-and-found-service-persistence/LocationDaoImpl");

        location = new Location();
        location.setDescription("Found at a train station");

        notebook = new Item();
        notebook.setName("notebook");
        notebook.setCharacteristics("white, macbook");
        notebook.setPhoto("photo");
        notebook.setStatus(Status.IN_PROGRESS);

        locationWithItems = new Location();
        locationWithItems.setDescription("At a bus station");
    }

    @After
    public void testTeardown() {
        // make sure that locationDao is cleaned after every test (to make tests independent of one another)
        try {
            List<Location> locations = locationDao.getAllLocations();
            for (Location location : locations) {
                locationDao.deleteLocation(location);
            }

        } catch (
                NoSuchEJBException ex) {
            // needed after negative test cases, userDao contains thrown exception and needs to be re-created
        }
    }

    @Test
    public void shouldReturn0LocationsWhenEmpty() throws Exception {
        assertEquals(locationDao.getAllLocations().size(), 0);
        assertNull(locationDao.getLocationById(0L));
    }

    @Test
    public void shouldAddLocation() throws Exception {
        locationDao.addLocation(location);
        assertEquals(locationDao.getAllLocations().size(), 1);
    }

    @Test
    public void shouldAddLocationWithItems() throws Exception {
        locationWithItems.getItems().add(notebook);
        locationDao.addLocation(locationWithItems);
    }

    @Test
    public void shouldNotCreateAdditionalLocationIfTheSameOneAdded() throws Exception {
        locationDao.addLocation(location);
        locationDao.addLocation(location);
        assertEquals(locationDao.getAllLocations().size(), 1);
        locationDao.deleteLocation(location);
        assertEquals(locationDao.getAllLocations().size(), 0);
        locationDao.addLocation(new Location());
        assertEquals(locationDao.getAllLocations().size(), 1);
    }

    @Test
    public void ShouldUpdateLocation() throws Exception {
        locationDao.addLocation(location);
        String newDescription = "Found near a swimming pool";
        location.setDescription(newDescription);

        locationDao.updateLocation(location);

        Location updatedLocation = locationDao.getLocationById(location.getId());

        assertEquals(updatedLocation.getDescription(), newDescription);
        assertEquals(updatedLocation.getItems().size(), 0);
    }

    @Test
    public void ShouldUpdateLocationWhenNoChange() throws Exception {
        locationDao.addLocation(location);
        locationDao.updateLocation(location);

        Location updatedLocation = locationDao.getLocationById(location.getId());

        assertEquals(location, updatedLocation);
    }

    @Test
    public void ShouldUpdateLocationWithItems() throws Exception {
        locationDao.addLocation(locationWithItems);
        Location foundLocation = locationDao.getLocationById(locationWithItems.getId());
        assertEquals(foundLocation.getItems().size(), 0);

        locationWithItems.getItems().add(notebook);
        locationDao.updateLocation(locationWithItems);
        assertEquals(locationDao.getAllLocations().size(), 1);
        foundLocation = locationDao.getLocationById(locationWithItems.getId());
        assertEquals(foundLocation.getItems().size(), 1);
    }

    @Test
    public void ShouldDeleteLocation() throws Exception {
        locationDao.addLocation(location);
        locationDao.addLocation(locationWithItems);
        assertEquals(locationDao.getAllLocations().size(), 2);

        locationDao.deleteLocation(locationWithItems);
        assertEquals(locationDao.getAllLocations().size(), 1);

        assertNull(locationDao.getLocationById(locationWithItems.getId()));
        assertEquals(locationDao.getLocationById(location.getId()), location);
    }

    @Test
    public void shouldFailOnAddNullLocation() throws Exception {
        assertThatThrownBy(() -> locationDao.addLocation(null)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailOnAddNullUpdate() throws Exception {
        assertThatThrownBy(() -> locationDao.updateLocation(null))
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailOnAddNullDelete() throws Exception {
        assertThatThrownBy(() -> locationDao.deleteLocation(null))
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldFailOnAddNullGetById() throws Exception {
        assertThatThrownBy(() -> locationDao.getLocationById(null))
                .hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void deleteNullIdLocation() throws Exception {
        assertThatThrownBy(() -> locationDao.deleteLocation(location)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void updateNullIdLocation() throws Exception {
        assertThatThrownBy(() -> locationDao.updateLocation(location)).hasCauseInstanceOf(IllegalArgumentException.class);
    }

}