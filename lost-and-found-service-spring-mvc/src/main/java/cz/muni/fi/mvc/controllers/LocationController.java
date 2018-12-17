package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.LocationDTO;
import cz.muni.fi.api.facade.ItemFacade;
import cz.muni.fi.api.facade.LocationFacade;
import cz.muni.fi.service.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

/**
 * SpringMVC Controller for locations.
 *
 * @author Jakub Polacek
 */
@Controller
@RequestMapping("/location")
public class LocationController {

    final static Logger log = LoggerFactory.getLogger(LocationController.class);


    @Autowired
    private LocationFacade locationFacade;

    @Autowired
    private ItemFacade itemFacade;

    /**
     * Shows a list of products with the ability to add, delete or edit.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = {"", "/", "/all", "list"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("locations", locationFacade.getAllLocations());
        return "location/list";
    }

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = {"/new", "/create"}, method = RequestMethod.GET)
    public String newLocation(Model model) {
        log.debug("New location");
        model.addAttribute("location", new LocationDTO());
        return "location/create";
    }

    @RequestMapping(value = {"/new", "/create"}, method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("locationCreate") LocationDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("Creating location ", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "location/create";
        }
        locationFacade.addLocation(formBean);
        
        redirectAttributes.addFlashAttribute("alert_success", "Location was created");
        return "redirect:" + uriBuilder.path("/location/list").build().toUriString();
    }


    /**
     * Get update page for location
     *
     * @param id of the location
     */
    @RequestMapping(value = {"edit/{id}/"}, method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("Start update location id: " + id);
        LocationDTO location = locationFacade.getLocationById(id);
        if (location == null) {
            log.warn("Tried to update nonexisting location");
            return "redirect:" + uriBuilder.path("/location/list").build().toUriString();
        }
        model.addAttribute("location", location);
        model.addAttribute("description", location.getDescription());
        return "/location/edit";
    }

    /**
     * Processes location update request
     *
     * @param id    of the location
     * @param location to be updated
     */
    @RequestMapping(value = {"edit/{id}/"}, method = RequestMethod.POST)
    public String postUpdate(@PathVariable Long id,
                             RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder,
                             @ModelAttribute("location") LocationDTO location,
                             BindingResult bindingResult) {
        log.debug("Updating location id: " + id);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning",
                    "Location update failed. Incorrect values.");

            return "/location/edit";
        }
        try {
            locationFacade.updateLocation(location);
            redirectAttributes.addFlashAttribute(
                    "alert_success",
                    "Location was updated.");
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Location update failed for unknown reasons.");
        }

        return "redirect:"  + uriBuilder.path("/location/list").build().toUriString();
    }



    /**
     * Deletes location
     *
     * @param id of location
     */
    @RequestMapping(value = {"delete/{id}/"}, method = RequestMethod.GET)
    public String deleteLocation(@PathVariable Long id, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("delete location: " + id);
        try {
            LocationDTO toDelete = locationFacade.getLocationById(id);
            if (itemFacade.getAllItems().stream()
                    .noneMatch(itemDTO ->
                            toDelete.equals(itemDTO.getFoundLocation()) || toDelete.equals(itemDTO.getLostLocation()))) {
                locationFacade.deleteLocation(toDelete);
                redirectAttributes.addFlashAttribute("alert_success", "Location was deleted");
                return "redirect:" + uriBuilder.path("/location/list").build().toUriString();
            } else {
                redirectAttributes.addFlashAttribute(
                        "alert_warning",
                        "Location cannot be deleted, there are still items with this location.");
            }

        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Location failed to be deleted");
            log.error("Cant delete location: " + id, e);
        }
        return "redirect:" + uriBuilder.path("/location/list").build().toUriString();
    }
}
