package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.ItemCreateFoundDTO;
import cz.muni.fi.api.dto.ItemCreateLostDTO;
import cz.muni.fi.api.dto.ItemDTO;
import cz.muni.fi.api.dto.ItemResolveDTO;
import cz.muni.fi.api.enums.Status;
import cz.muni.fi.api.facade.CategoryFacade;
import cz.muni.fi.api.facade.ItemFacade;
import cz.muni.fi.api.facade.LocationFacade;
import cz.muni.fi.api.facade.UserFacade;
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
 * SpringMVC Controller for operations on items.
 *
 * @author Terézia Slanináková, Jakub Polacek
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    final static Logger log = LoggerFactory.getLogger(ItemController.class);


    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private LocationFacade locationFacade;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private CategoryFacade categoryFacade;

    /**
     * Shows a list of items
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = {"", "/", "/all", "list"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("items", itemFacade.getAllItems());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("categories", categoryFacade.getAllCategories());

        return "item/list";
    }

    /**
     * Registers a new lost item
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
     * Registers a new found item
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
     * Registers a new found item
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = {"/new-found", "/create-found"}, method = RequestMethod.POST)
    public String createFound(
            @Valid @ModelAttribute("itemCreateFound") ItemCreateFoundDTO formBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {

        log.debug("Create(formBean={}) ", formBean);
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
     * Registers a new lost item
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = {"/new-lost", "/create-lost"}, method = RequestMethod.POST)
    public String createLost(
            @Valid @ModelAttribute("itemCreateLost") ItemCreateLostDTO formBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            UriComponentsBuilder uriBuilder) {

        log.debug("Create(formBean={}) ", formBean);
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
     * Update item
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/edit/{id}/"}, method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model, UriComponentsBuilder uriBuilder) {
        log.debug("Start update item id: " + id);
        ItemDTO item = itemFacade.getItemById(id);
        if (item == null) {
            log.warn("Tried to update non-existing item");
            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }
        model.addAttribute("item", item);
        model.addAttribute("name", item.getName());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("archived", item.getArchive() != null);
        model.addAttribute("categories", categoryFacade.getAllCategories());
        return "/item/edit";
    }

    /**
     * Resolve item
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/resolve/{id}/"}, method = RequestMethod.GET)
    public String resolve(@PathVariable Long id, Model model, UriComponentsBuilder uriBuilder) {
        log.debug("Start update item id: " + id);
        ItemResolveDTO item = new ItemResolveDTO();
        item.setId(id);
        item.setStatus(itemFacade.getItemById(id).getStatus());
        if (item == null) {
            log.warn("Tried to resolve non-existing item");
            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }
        model.addAttribute("item", item);
        model.addAttribute("users", userFacade.getAllUsers());
        model.addAttribute("locations", locationFacade.getAllLocations());
        return "item/resolve";
    }

    /**
     * Resolve item (Post request)
     *
     * @param id    of the item
     * @param itemResolveDto to be resolved
     */
    @RequestMapping(value = {"/resolve/{id}/"}, method = RequestMethod.POST)
    public String postResolve(@PathVariable Long id,
                             RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder,
                             @ModelAttribute("item") ItemResolveDTO itemResolveDto,
                             BindingResult bindingResult) {
        log.debug("Resolving item: " + itemResolveDto.toString());
        ItemDTO item = itemFacade.getItemById(id);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning",
                    "Item update failed. Incorrect values.");

            return "/item/list";
        }
        try {
            if(item.getStatus() == Status.CLAIM_RECEIVED_LOST){
                itemFacade.resolveLostItem(item, itemResolveDto.getDate(), locationFacade.getLocationById(itemResolveDto.getLocationId()));
            }
            if(item.getStatus() == Status.CLAIM_RECEIVED_FOUND){
                itemFacade.resolveFoundItem(item, itemResolveDto.getDate(), locationFacade.getLocationById(itemResolveDto.getLocationId()), userFacade.getUserById(itemResolveDto.getOwnerId()));
            }
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
     * Processes item update request
     *
     * @param id    of the item
     * @param item to be updated
     */
    @RequestMapping(value = {"/edit/{id}/"}, method = RequestMethod.POST)
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
     * Archive by category
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/edit/{id}/archive"}, method = RequestMethod.GET)
    public String archive(@PathVariable Long id,
                          Model model,
                          RedirectAttributes redirectAttributes,
                          UriComponentsBuilder uriBuilder) {
        log.debug("Archiving item id: " + id);
        ItemDTO item = itemFacade.getItemById(id);
        if (item == null || item.getArchive() != null) {
            log.warn("Tried to archive non-existing or already archived item.");
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Item failed to be archived. It probably doesn't exist or is already archived.");
            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }
        try {
            itemFacade.archiveItem(item);
            redirectAttributes.addFlashAttribute("alert_success", "Item was archived");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning", "Item failed to be archived. Reason: " + e.getMessage());
        }
        model.addAttribute("item", item);
        model.addAttribute("name", item.getName());
        return "redirect:"  + uriBuilder.path("/item/edit/" + item.getId() ).build().toUriString();
    }

    /**
     * Add item to category
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/edit/{id}/category/set/{categoryId}"}, method = RequestMethod.GET)
    public String addToCategory(@PathVariable Long id, @PathVariable Long categoryId,
                          RedirectAttributes redirectAttributes,
                          UriComponentsBuilder uriBuilder) {
        log.debug("Adding category to item id: " + id);
        try {
            itemFacade.addCategoryToItem(id, categoryId);
            redirectAttributes.addFlashAttribute("alert_success", "Item was associated with category");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning", "Item failed to be associated with category. Reason: " + e.getMessage());
        }
        return "redirect:"  + uriBuilder.path("/item/edit/" + id).build().toUriString();
    }

    /**
     * Remove item from category
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/edit/{id}/category/remove/{categoryId}"}, method = RequestMethod.GET)
    public String removeFromCategory(@PathVariable Long id, @PathVariable Long categoryId,
                                RedirectAttributes redirectAttributes,
                                UriComponentsBuilder uriBuilder) {
        log.debug("Removed category from item id: " + id);
        try {
            itemFacade.removeCategoryFromItem(id, categoryId);
            redirectAttributes.addFlashAttribute("alert_success", "Item was disassociated with category");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning", "Item failed to be removed from category. Reason: " + e.getMessage());
        }
        return "redirect:"  + uriBuilder.path("/item/edit/" + id).build().toUriString();
    }

    /**
     * Get item archive text
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/edit/{id}/archive-text"}, method = RequestMethod.GET)
    public String getArchive(@PathVariable Long id,
                          Model model,
                          RedirectAttributes redirectAttributes,
                          UriComponentsBuilder uriBuilder) {
        log.debug("Archiving item id: " + id);
        ItemDTO item = itemFacade.getItemById(id);
        if (item == null || item.getArchive() == null) {
            log.warn("Tried to get archive of non archived text");
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Failed to get item archive.");
            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }

        model.addAttribute("archive", item.getArchive());
        model.addAttribute("name", item.getName());
        return "/item/archive-text";
    }

    /**
     * Delete item
     *
     * @param id of item
     */
    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
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
