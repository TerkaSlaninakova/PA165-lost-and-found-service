package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.CategoryCreateDTO;
import cz.muni.fi.api.dto.CategoryDTO;
import cz.muni.fi.api.dto.UserDTO;
import cz.muni.fi.api.facade.CategoryFacade;
import cz.muni.fi.api.facade.ItemFacade;
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
 * SpringMVC Controller for categories.
 *
 * @author Jakub Polacek
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    final static Logger log = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    private CategoryFacade categoryFacade;

    @Autowired
    private ItemFacade itemFacade;

    @Autowired
    private HttpSession session;
    /**
     * Shows a list of products with the ability to add, delete or edit.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = {"", "/", "/all", "list"}, method = RequestMethod.GET)
    public String list(Model model) {

        UserDTO user = (UserDTO) session.getAttribute("authenticated");
        if (user != null) {
            model.addAttribute("authenticatedUser", user.getEmail());
        }
        model.addAttribute("admin", user.getIsAdmin());
        model.addAttribute("categories", categoryFacade.getAllCategories());
        return "category/list";
    }

    /**
     * Prepares an empty form.
     *
     * @param model data to be displayed
     * @return JSP page
     */
    @RequestMapping(value = {"/new", "/create"}, method = RequestMethod.GET)
    public String newCategory(Model model) {
        log.debug("Creating category");
        model.addAttribute("categoryCreate", new CategoryCreateDTO());
        return "category/create";
    }

    @RequestMapping(value = {"/new", "/create"}, method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("categoryCreate") CategoryCreateDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("create(formBean={})", formBean);
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                log.trace("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                log.trace("FieldError: {}", fe);
            }
            return "category/create";
        }

        categoryFacade.addCategory(formBean);

        redirectAttributes.addFlashAttribute("alert_success", "Category was created");
        return "redirect:" + uriBuilder.path("/category/list").build().toUriString();
    }

    /**
     * Get update page for category
     *
     * @param id of the category
     */
    @RequestMapping(value = {"edit/{id}/"}, method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("Start update category id: " + id);
        CategoryDTO category = categoryFacade.getCategoryById(id);
        if (category == null) {
            log.warn("Tried to update nonexisting category");
            return "redirect:" + uriBuilder.path("/category/list").build().toUriString();
        }
        model.addAttribute("category", category);
        model.addAttribute("name", category.getName());
        model.addAttribute("attribute", category.getAttribute());
        return "/category/edit";
    }

    /**
     * Processes category update request
     *
     * @param id    of the category
     * @param category to be updated
     */
    @RequestMapping(value = {"edit/{id}/"}, method = RequestMethod.POST)
    public String postUpdate(@PathVariable Long id,
                             RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder,
                             @ModelAttribute("category") CategoryDTO category,
                             BindingResult bindingResult) {
        log.debug("Updating category id: " + id);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning",
                    "Category update failed. Incorrect values.");

            return "/category/edit";
        }
        try {
            categoryFacade.updateCategory(category);
            redirectAttributes.addFlashAttribute(
                    "alert_success",
                    "Category was updated.");
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Category update failed for unknown reasons.");
        }

        return "redirect:"  + uriBuilder.path("/category/list").build().toUriString();
    }


    /**
     * Deletes category
     *
     * @param id of category
     */
    @RequestMapping(value = {"delete/{id}/"}, method = RequestMethod.GET)
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("Deleting category: " + id);
        try {
            CategoryDTO toDelete = categoryFacade.getCategoryById(id);
            if (itemFacade.getItemsByCategory(toDelete.getName()).isEmpty()) {

                categoryFacade.deleteCategory(categoryFacade.getCategoryById(id));
                redirectAttributes.addFlashAttribute("alert_success", "Category was deleted");

            } else {
                log.debug("Category " + id + "stil on item, cant delete.");
                redirectAttributes.addFlashAttribute(
                        "alert_warning",
                        "Category cannot be deleted, there are still items with this category.");
            }
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("alert_danger", "Category failed to be deleted");
            log.error("Cant delete category: " + id, e);
        }

        return "redirect:" + uriBuilder.path("/category/list").build().toUriString();
    }
}
