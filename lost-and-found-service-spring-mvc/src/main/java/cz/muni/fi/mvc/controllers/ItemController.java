package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.*;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Autowired
    private HttpSession session;

    /**
     * Check if user is admin or owner of given item and add it as attribute to model
     */
    private void ownerOrAdmin(ItemDTO item, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("authenticated");

        model.addAttribute(
                String.valueOf(item.getId()),
                String.valueOf(user.getIsAdmin() || Objects.equals(itemFacade.getOwnerId(item.getId()), user.getId())));
    }

    private boolean isOwnerOrAdminByItemId(Long itemId) {
        UserDTO user = (UserDTO) session.getAttribute("authenticated");
        return user.getIsAdmin() || Objects.equals(itemFacade.getOwnerId(itemId), user.getId());
    }


    /**
     * Shows a list of items
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = {"", "/", "/all", "/list"}, method = RequestMethod.GET)
    public String list(Model model) {

        UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

        model.addAttribute("items", itemFacade.getAllItems());
        model.addAttribute("search", new ItemSearchDTO());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("categories", categoryFacade.getAllCategories());

        for (ItemDTO item : itemFacade.getAllItems()) {
            ownerOrAdmin(item, model);
        }

        return "item/list";
    }

    @RequestMapping(value = {"/all"}, method = RequestMethod.POST)
    public String search(Model model, @Valid @ModelAttribute("search") ItemSearchDTO search) {
        log.debug("search: " + search.toString());

        UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

        List<ItemDTO> items = itemFacade.getAllItems();
        if (search.getStatus() != null && !search.getStatus().toString().equals("")) {
            items = itemFacade.getAllItems().stream().filter(item -> item.getStatus() == search.getStatus()).collect(Collectors.toList());
        }
        final List<ItemDTO> finalItems = items;
        if (!search.getCategoryName().equals("")) {
            items = itemFacade.getItemsByCategory(search.getCategoryName()).stream().filter(item -> finalItems.contains(item)).collect(Collectors.toList());
        }
        model.addAttribute("items", items);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("categories", categoryFacade.getAllCategories());

        for (ItemDTO item : itemFacade.getAllItems()) {
            ownerOrAdmin(item, model);
        }

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

        UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

        model.addAttribute("itemCreateLost", new ItemCreateLostDTO());
        model.addAttribute("locations", locationFacade.getAllLocations());
        model.addAttribute("users", userFacade.getAllUsers());
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

        UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

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
                log.debug("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.debug("FieldError: {}", fe);
                redirectAttributes.addFlashAttribute("alert_danger", fe.getDefaultMessage());
            }
            return "redirect:" + uriBuilder.path("/item/create-found").build().toUriString();
        }
        try {
            itemFacade.addItemFound(formBean);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Adding item failed for unknown reasons.");
        }

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

        log.debug("Create(formBean={}) ", formBean.toString());
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.debug("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.debug("FieldError: {}", fe);
                redirectAttributes.addFlashAttribute("alert_danger", fe.getDefaultMessage());
            }
            return "redirect:" + uriBuilder.path("/item/create-lost").build().toUriString();
        }
        UserDTO user = userFacade.getUserById(formBean.getOwnerId());
        log.debug("user={}) ", user.toString());
        try {
            itemFacade.addItemLost(formBean, user);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Adding item failed for unknown reasons.");
        }

        redirectAttributes.addFlashAttribute("alert_success", "Item was created");
        return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
    }


    /**
     * Update item
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/detail/{id}/", "/detail/{id}"}, method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, UriComponentsBuilder uriBuilder) {
        log.debug("Start update item id: " + id);

        UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

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

        return "/item/detail";
    }

    /**
     * Update item
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/edit/{id}/", "/edit/{id}"}, method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model, UriComponentsBuilder uriBuilder) {
        log.debug("Start update item id: " + id);

        UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

        if (!isOwnerOrAdminByItemId(id)) {
            log.debug("Droided.");
            return "redirect:" + uriBuilder.path("/adminOnly").build().toUriString();
        }

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
     * Processes item update request
     *
     * @param id   of the item
     * @param item to be updated
     */
    @RequestMapping(value = {"/edit/{id}/", "/edit/{id}"}, method = RequestMethod.POST)
    public String postUpdate(@PathVariable Long id,
                             RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder,
                             @ModelAttribute("item") ItemDTO item,
                             BindingResult bindingResult) {
        log.debug("Updating item id: " + id);

        ItemDTO oldItem = itemFacade.getItemById(id);

        if (!isOwnerOrAdminByItemId(id)) {
            log.debug("Droided.");
            return "redirect:" + uriBuilder.path("/adminOnly").build().toUriString();
        }


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning",
                    "Item update failed. Incorrect values.");

            return "/item/edit";
        }


        try {

            item.setOwner(oldItem.getOwner());
            item.setCategories(oldItem.getCategories());
            itemFacade.updateItem(item);
            redirectAttributes.addFlashAttribute(
                    "alert_success",
                    "Item was updated.");
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Item update failed for unknown reasons.");
        }

        return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
    }


    /**
     * Resolve item
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/resolve/{id}/", "/resolve/{id}"}, method = RequestMethod.GET)
    public String resolve(@PathVariable Long id, Model model, UriComponentsBuilder uriBuilder) {
        log.debug("Start resolving item id: " + id);

        UserDTO loggedUser = (UserDTO) session.getAttribute("authenticated");
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

        ItemResolveDTO itemToResolve = new ItemResolveDTO();
        ItemDTO item = itemFacade.getItemById(id);
        if (item == null) {
            log.warn("Tried to resolve non-existing item");
            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }

        itemToResolve.setId(id);
        itemToResolve.setStatus(item.getStatus());


        if (!isOwnerOrAdminByItemId(id)) {
            return "redirect:" + uriBuilder.path("/adminOnly").build().toUriString();
        }

        model.addAttribute("item", itemToResolve);
        model.addAttribute("users", userFacade.getAllUsers());
        model.addAttribute("locations", locationFacade.getAllLocations());
        return "item/resolve";
    }


    /**
     * Resolve item (Post request)
     *
     * @param id             of the item
     * @param itemResolveDto to be resolved
     */
    @RequestMapping(value = {"/resolve/{id}/", "/resolve/{id}"}, method = RequestMethod.POST)
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
                    "Item resolving failed. Incorrect values.");

            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }

        if (!isOwnerOrAdminByItemId(id)) {
            return "redirect:" + uriBuilder.path("/adminOnly").build().toUriString();
        }

        try {

            if (item.getStatus() == Status.CLAIM_RECEIVED_LOST) {

                if (itemResolveDto.getDate().isBefore(item.getLostDate())) {
                    redirectAttributes.addFlashAttribute(
                            "alert_warn",
                            "Item can't be resolved before it's lost date " + item.getLostDate() + ".");

                } else {
                    itemFacade.resolveLostItem(
                            item,
                            itemResolveDto.getDate(),
                            locationFacade.getLocationById(itemResolveDto.getLocationId()));

                    redirectAttributes.addFlashAttribute(
                            "alert_success",
                            "Item was resolved.");
                }
            }
            if (item.getStatus() == Status.CLAIM_RECEIVED_FOUND) {

                if (itemResolveDto.getDate().isBefore(item.getFoundDate())) {
                    redirectAttributes.addFlashAttribute(
                            "alert_warn",
                            "Item can't be resolved before it's found date " + item.getFoundDate().toString() + ".");

                } else {
                    itemFacade.resolveFoundItem(
                            item,
                            itemResolveDto.getDate(),
                            locationFacade.getLocationById(itemResolveDto.getLocationId()),
                            userFacade.getUserById(itemResolveDto.getOwnerId()));

                    redirectAttributes.addFlashAttribute(
                            "alert_success",
                            "Item was resolved.");
                }
            }

        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Item resolving failed for unknown reasons.");
        }

        return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
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

        ItemDTO item = itemFacade.getItemById(id);
        if (item == null) {
            log.warn("Tried to change category on non-existing item.");
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Tried to change category on non-existing item.");
            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }

        if (!isOwnerOrAdminByItemId(id)) {
            return "redirect:" + uriBuilder.path("/adminOnly").build().toUriString();
        }


        try {
            itemFacade.addCategoryToItem(id, categoryId);
            redirectAttributes.addFlashAttribute("alert_success", "Item was associated with category");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning", "Item failed to be associated with category. Reason: " + e.getMessage());
        }
        return "redirect:" + uriBuilder.path("/item/edit/" + id).build().toUriString();
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

        ItemDTO item = itemFacade.getItemById(id);
        if (item == null) {
            log.warn("Tried to change category on non-existing item.");
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Tried to change category on non-existing item.");
            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }

        if (!isOwnerOrAdminByItemId(id)) {
            return "redirect:" + uriBuilder.path("/adminOnly").build().toUriString();
        }

        try {
            itemFacade.removeCategoryFromItem(id, categoryId);
            redirectAttributes.addFlashAttribute("alert_success", "Item was disassociated with category");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning", "Item failed to be removed from category. Reason: " + e.getMessage());
        }
        return "redirect:" + uriBuilder.path("/item/edit/" + id).build().toUriString();
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

        if (!isOwnerOrAdminByItemId(id)) {
            return "redirect:" + uriBuilder.path("/adminOnly").build().toUriString();
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
        return "redirect:" + uriBuilder.path("/item/edit/" + item.getId()).build().toUriString();
    }

    /**
     * Get item archive text
     *
     * @param id of the item
     */
    @RequestMapping(value = {"/edit/{id}/archive-text", "/detail/{id}/archive-text"}, method = RequestMethod.GET)
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

        // moze accesnut aj normalny user a pozriet si ho

        if (!isOwnerOrAdminByItemId(item.getId())) {
            return "redirect:" + uriBuilder.path("/adminOnly").build().toUriString();
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

        ItemDTO item = itemFacade.getItemById(id);

        if (item == null) {
            log.warn("Tried to delete nonexistent item.");
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Failed to get item archive.");
            return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
        }


        if (!isOwnerOrAdminByItemId(item.getId())) {
            return "redirect:" + uriBuilder.path("/adminOnly").build().toUriString();
        }

        try {
            itemFacade.deleteItem(item);
            redirectAttributes.addFlashAttribute("alert_success", "Item was deleted");
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Item failed to be deleted");
            log.error("Cant delete item: " + id, e);
        }

        return "redirect:" + uriBuilder.path("/item/list").build().toUriString();
    }

}