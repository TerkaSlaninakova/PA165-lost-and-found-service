package cz.muni.fi.service.facade;

import cz.muni.fi.dto.LocationDTO;
import cz.muni.fi.facade.LocationFacade;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.service.BeanMappingService;
import cz.muni.fi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of LocationFacade
 *
 * @author Terezia Slaninakova (445526)
 */
@Service
@Transactional
public class LocationFacadeImpl implements LocationFacade {

    @Autowired
    private LocationService locationService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void addLocation(LocationDTO locationDTO) {
        locationService.addLocation(beanMappingService.mapTo(locationDTO, Location.class));
    }

    @Override
    public void updateLocation(LocationDTO locationDTO) {
        locationService.updateLocation(beanMappingService.mapTo(locationDTO, Location.class));
    }

    @Override
    public void deleteLocation(LocationDTO locationDTO) {
        locationService.deleteLocation(beanMappingService.mapTo(locationDTO, Location.class));
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        return beanMappingService.mapTo(locationService.getLocationById(id), LocationDTO.class);
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        return beanMappingService.mapTo(locationService.getAllLocations(), LocationDTO.class);
    }
}
