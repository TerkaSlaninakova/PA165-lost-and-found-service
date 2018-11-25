package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.LocationDao;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.service.config.ServiceConfiguration;
import cz.muni.fi.service.exceptions.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Tests for LocationService
 * @author Augustin Nemec
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class LocationServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private LocationDao locationDao;

    @Autowired
    @InjectMocks
    private LocationService locationService = new LocationServiceImpl();


    private Location bratislava;
    private Location brno;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void createLocations() {

        bratislava = new Location();
        bratislava.setDescription("In Slovakia");
        bratislava.setId(1L);

        brno = new Location();
        brno.setDescription("In Czech Republic");
        brno.setId(2L);

    }

    @Test
    public void testAddLocation() {

        doAnswer(invocationOnMock -> {
            Location location = (Location) invocationOnMock.getArguments()[0];
            location.setId(1L);
            return null;
        }).when(locationDao).addLocation(bratislava);

        locationService.addLocation(bratislava);

        assertNotNull(bratislava.getId());
        verify(locationDao).addLocation(bratislava);

    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddNullLocation() {
        doThrow(new IllegalArgumentException()).when(locationDao).addLocation(null);
        locationService.addLocation(null);
    }

    @Test
    public void testUpdateLocation() {
        locationService.updateLocation(bratislava);
        verify(locationDao).updateLocation(bratislava);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateNullLocation() {
        doThrow(new IllegalArgumentException()).when(locationDao).updateLocation(null);
        locationService.updateLocation(null);
    }

    @Test
    public void testGetLocationById() {

        when(locationDao.getLocationById(1L)).thenReturn(brno);

        Location l = locationService.getLocationById(1L);

        assertEquals(brno, l);
        verify(locationDao).getLocationById(1L);
    }

    @Test
    public void testGetAllLocations() {
        when(locationDao.getAllLocations()).thenReturn(Arrays.asList(bratislava, brno));
        List<Location> result = locationService.getAllLocations();

        assertEquals(2, result.size());
        assertTrue(result.contains(brno));
        assertTrue(result.contains(bratislava));
    }



}
