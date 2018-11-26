package cz.muni.fi.facade;

import cz.muni.fi.dto.LocationDTO;


import java.util.List;

/**
 * Interface for LocationFacade
 *
 * @author Terezia Slaninakova (445526)
 */

public interface LocationFacade {

    /**
     * Create Location to
     * @param locationDTO locationDTO
     */
    void addLocation(LocationDTO locationDTO);

    /**
     * Update Location
     * @param locationDTO locationDTO to update
     */
    void updateLocation(LocationDTO locationDTO);

    /**
     * Delete Location
     * @param locationDTO to be deleted
     */
    void deleteLocation(LocationDTO locationDTO);

    /**
     * Get location by id
     * @param id id of location
     * @return Location or null
     */
    LocationDTO getLocationById(Long id);

    /**
     * Get a list of all locations in DB
     * @return list of all locations in DB
     */
    List<LocationDTO> getAllLocations();
}
