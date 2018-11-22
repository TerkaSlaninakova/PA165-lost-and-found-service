package cz.muni.fi.service.facade;

import cz.muni.fi.dto.LocationDTO;
import cz.muni.fi.facade.LocationFacade;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.service.BeanMappingService;
import cz.muni.fi.service.BeanMappingServiceImpl;
import cz.muni.fi.service.LocationService;
import cz.muni.fi.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Implementation of tests for LocationFacade
 *
 * @author Terezia Slaninakova (445526)
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class LocationFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationFacade locationFacade = new LocationFacadeImpl();

    @Spy
    @Autowired
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    private Location locationStation,locationClub;
    private LocationDTO locationDto;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        locationStation = new Location();
        locationStation.setId(1L);
        locationStation.setDescription("near the swimming pool");

        locationClub = new Location();
        locationClub.setId(1L);
        locationClub.setDescription("near a club");
        locationDto = beanMappingService.mapTo(locationStation, LocationDTO.class);
    }

    @Test
    public void testAddLocation() {
        locationFacade.addLocation(locationDto);
        verify(locationService).addLocation(any(Location.class));
    }

    @Test
    public void testUpdateLocation() {
        locationDto.setDescription("near the bus station");
        locationFacade.updateLocation(locationDto);
        verify(locationService).updateLocation(any(Location.class));
    }

    @Test
    public void testDeleteLocation() {
        locationFacade.deleteLocation(locationDto);
        verify(locationService).deleteLocation(any(Location.class));
    }

    @Test
    public void testGetLocationById() {
        when(locationService.getLocationById(locationStation.getId())).thenReturn(locationStation);
        LocationDTO locationDto = locationFacade.getLocationById(locationStation.getId());
        verify(locationService).getLocationById(locationStation.getId());
        assertEquals(locationStation, beanMappingService.mapTo(locationDto, Location.class));
    }

    @Test
    public void testGetAllLocations() {
        when(locationService.getAllLocations()).thenReturn(Arrays.asList(locationStation, locationClub));
        List<LocationDTO> locationDtos = locationFacade.getAllLocations();
        verify(locationService).getAllLocations();
        List<Location> locations = beanMappingService.mapTo(locationDtos, Location.class);
        assertEquals(2,locations.size());
        assertThat(locations.contains(locationStation));
        assertThat(locations.contains(locationClub));
    }
}
