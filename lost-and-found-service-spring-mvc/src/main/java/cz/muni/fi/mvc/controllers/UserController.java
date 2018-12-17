package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.UserDTO;
import cz.muni.fi.api.facade.ItemFacade;
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

/**
 *
 * @author Augustin Nemec
 */

@Controller
@RequestMapping("/user")
public class UserController {

    final static Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = {"", "/", "/all", "list"}, method = RequestMethod.GET)
    public String list(Model model) {

        UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

        model.addAttribute("users", userFacade.getAllUsers());
        return "user/list";
    }

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = {"/new", "/create"}, method = RequestMethod.GET)
    public String newUser(Model model) {
        log.debug("New user");

        UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

        model.addAttribute("user", new UserDTO());
        return "user/create";
    }

    /**
     * Processes user create request
     *
     * @param formBean UserDTO to add
     */
    @RequestMapping(value = {"/new", "/create"}, method = RequestMethod.POST)
    public String createUser(
            @Valid @ModelAttribute("userCreate") UserDTO formBean,
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
            return "user/create";
        }
        try{
            userFacade.addUser(formBean);
        }
        catch (ServiceException e) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Adding user failed for unknown reasons.");
        }

        redirectAttributes.addFlashAttribute("alert_success", "User was created");
        return "redirect:" + uriBuilder.path("/user/list").build().toUriString();
    }

    /**
     * Get update page for user
     *
     * @param id of the user
     */
    @RequestMapping(value = {"edit/{id}/"}, method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("Start update user id: " + id);

        UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

        UserDTO user = userFacade.getUserById(id);
        user.setPassword("");
        if (user == null) {
            log.warn("Tried to update nonexisting user");
            return "redirect:" + uriBuilder.path("/user/list").build().toUriString();
        }
        model.addAttribute("user", user);
        return "/user/edit";
    }

    /**
     * Processes user update request
     *
     * @param id    of the user
     * @param user to be updated
     */
    @RequestMapping(value = {"edit/{id}/"}, method = RequestMethod.POST)
    public String postUpdate(@PathVariable Long id,
                             RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder,
                             @ModelAttribute("user") UserDTO user,
                             BindingResult bindingResult) {
        log.debug("Updating user id: " + id);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning",
                    "User update failed. Incorrect values.");

            return "/user/edit";
        }
        try {
            userFacade.updateUser(user);
            redirectAttributes.addFlashAttribute(
                    "alert_success",
                    "User was updated.");
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "User update failed for unknown reasons.");
        }

        return "redirect:"  + uriBuilder.path("/user/list").build().toUriString();
    }


    /**
     * Deletes user
     *
     * @param id of user
     */
    @RequestMapping(value = {"delete/{id}/"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("delete user: " + id);
        try {
            UserDTO toDelete = userFacade.getUserById(id);
            if (itemFacade.getAllItems().stream()
                    .noneMatch(itemDTO ->
                            toDelete.equals(itemDTO.getOwner()))) {

                UserDTO loggedUser = UserDTO.class.cast(session.getAttribute("authenticated"));
                if (loggedUser.equals(toDelete)) {
                    redirectAttributes.addFlashAttribute(
                            "alert_warning",
                            "User cannot delete himself");
                } else {
                    userFacade.deleteUser(toDelete);
                    redirectAttributes.addFlashAttribute("alert_success", "User was deleted");
                    return "redirect:" + uriBuilder.path("/user/list").build().toUriString();
                }
            } else {
                redirectAttributes.addFlashAttribute(
                        "alert_warning",
                        "User cannot be deleted, he is still owner of some item");
            }

        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "User failed to be deleted");
            log.error("Cant delete user: " + id, e);
        }
        return "redirect:" + uriBuilder.path("/user/list").build().toUriString();
    }

}
