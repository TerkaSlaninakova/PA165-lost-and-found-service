package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.CategoryCreateDTO;
import cz.muni.fi.api.facade.CategoryFacade;
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
 * SpringMVC Controller for administering categories.
 *
 * @author Jakub Polacek
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    final static Logger log = LoggerFactory.getLogger(CategoryController.class);


    @Autowired
    private CategoryFacade categoryFacade;

    /**
     * Shows a list of products with the ability to add, delete or edit.
     *
     * @param model data to display
     * @return JSP page name
     */
    @RequestMapping(value = {"", "/", "/all", "list"}, method = RequestMethod.GET)
    public String list(Model model) {
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
        log.debug("new()");
        model.addAttribute("categoryCreate", new CategoryCreateDTO());
        return "category/createEdit";
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
            return "category/createEdit";
        }

        categoryFacade.addCategory(formBean);

        redirectAttributes.addFlashAttribute("alert_success", "Category was created");
        return "redirect:" + uriBuilder.path("/category/list").build().toUriString();
    }


    /**
     * Deletes category
     *
     * @param id of category
     */
    @RequestMapping(value = {"/{id}/delete"}, method = RequestMethod.GET)
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        log.debug("delete category: " + id);
        try {
            categoryFacade.deleteCategory(categoryFacade.getCategoryById(id));
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("alert_error", "Category failed to be deleted");
            log.error("Cant delete category: " + id, e);
        }
        redirectAttributes.addFlashAttribute("alert_success", "Category was deleted");
        return "redirect:" + uriBuilder.path("/category/list").build().toUriString();
    }
}
