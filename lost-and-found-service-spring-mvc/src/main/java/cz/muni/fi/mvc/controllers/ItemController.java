package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.*;
import cz.muni.fi.api.enums.Status;
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
import java.util.List;


@Controller
@RequestMapping("/item")
public class ItemController {

    final static Logger log = LoggerFactory.getLogger(ItemController.class);


    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private LocationFacade locationFacade;

    /**
     * Shows a list of products with the ability to add, delete or edit.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = {"", "/", "/all", "list"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("items", itemFacade.getAllItems());
        model.addAttribute("statuses", Status.values());
        return "item/list";
    }

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = {"/new-lost", "/create-lost"}, method = RequestMethod.GET)
    public String newItemLost(Model model) {
        log.debug("Creating item");
        model.addAttribute("itemCreateLost", new ItemCreateLostDTO());
        model.addAttribute("locations", locationFacade.getAllLocations());
        return "item/create-lost";
    }

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = {"/new-found", "/create-found"}, method = RequestMethod.GET)
    public String newItemFound(Model model) {
        log.debug("Creating item");
        model.addAttribute("itemCreateFound", new ItemCreateFoundDTO());
        model.addAttribute("locations", locationFacade.getAllLocations());
        return "item/create-found";
    }

    /**
     * Creates a new item
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = {"/new-found", "/create-found"}, method = RequestMethod.POST)
    public String createFound(@Valid @ModelAttribute("itemCreateFound") ItemCreateFoundDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("Create(formBean={}) ", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "item/create-found";
        }
        itemFacade.addItemFound(formBean);

        redirectAttributes.addFlashAttribute("alert_success", "Item was created");
        return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
    }

    /**
     * Creates a new item
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = {"/new-lost", "/create-lost"}, method = RequestMethod.POST)
    public String createLost(@Valid @ModelAttribute("itemCreateLost") ItemCreateLostDTO formBean, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("Create(formBean={}) ", formBean);
        //in case of validation error forward back to the the form
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "item/create-lost";
        }
        itemFacade.addItemLost(formBean);

        redirectAttributes.addFlashAttribute("alert_success", "Item was created");
        return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
    }

    /**
     * Get update page for item
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/{id}/update", "/{id}/edit", "/{id}/change"}, method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("Start update item id: " + id);
        ItemDTO item = itemFacade.getItemById(id);
        if (item == null) {
            log.warn("Tried to update nonexisting item");
            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }
        model.addAttribute("item", item);
        model.addAttribute("name", item.getName());
        return "/item/edit";
    }

    /**
     * Processes item update request
     *
     * @param id    of the item
     * @param item to be updated
     */
    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.POST)
    public String postUpdate(@PathVariable Long id,
                             RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder,
                             @ModelAttribute("item") ItemDTO item,
                             BindingResult bindingResult) {
        log.debug("Updating item id: " + id);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning",
                    "Item update failed. Incorrect values.");

            return "/item/edit";
        }
        try {
            itemFacade.updateItem(item);
            redirectAttributes.addFlashAttribute(
                    "alert_success",
                    "Item was updated.");
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Item update failed for unknown reasons.");
        }

        return "redirect:"  + uriBuilder.path("/item/list").build().toUriString();
    }

    /**
     * Deletes item
     *
     * @param id of item
     */
    @RequestMapping(value = {"/{id}/delete"}, method = RequestMethod.GET)
    public String deleteItem(@PathVariable Long id, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("Deleting item: " + id);
        try {
                itemFacade.deleteItem(itemFacade.getItemById(id));
                redirectAttributes.addFlashAttribute("alert_success", "Item was deleted");
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Item failed to be deleted");
            log.error("Cant delete item: " + id, e);
        }

        return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
    }

}
