package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.LocationDTO;
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


@Controller
@RequestMapping("/location")
public class LocationController {

    final static Logger log = LoggerFactory.getLogger(LocationController.class);


    @Autowired
    private LocationFacade locationFacade;

    /**
     * Shows a list of products with the ability to add, delete or edit.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
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
        log.debug("new()");
        model.addAttribute("locationCreate", new LocationDTO());
        return "location/createEdit";
    }

    @RequestMapping(value = {"/new", "/create"}, method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("locationCreate") LocationDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(formBean={})", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "location/createEdit";
        }
        //create product
        locationFacade.addLocation(formBean);
        //report success
        redirectAttributes.addFlashAttribute("alert_success", "Location was created");
        return "redirect:" + uriBuilder.path("/location/list").build().toUriString();
    }

    /**
     * Deletes location
     *
     * @param id of location
     */
    @RequestMapping(value = {"/{id}/delete"}, method = RequestMethod.GET)
    public String deleteLocation(@PathVariable Long id, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("delete location: " + id);
        try {
            locationFacade.deleteLocation(locationFacade.getLocationById(id));
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("alert_error", "Location failed to be deleted");
            log.error("Cant delete location: " + id, e);
        }
        redirectAttributes.addFlashAttribute("alert_success", "Location was deleted");
        return "redirect:" + uriBuilder.path("/location/list").build().toUriString();
    }
}
