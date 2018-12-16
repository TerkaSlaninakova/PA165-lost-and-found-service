package cz.muni.fi.rest.controllers;

import cz.muni.fi.api.dto.*;
import cz.muni.fi.api.facade.ItemFacade;

import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.rest.Exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.rest.Exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import static cz.muni.fi.rest.ApiUris.ROOT_URI_ITEMS;

/**
 * REST Controller for Items
 *
 * @author Augustin Nemec
 */

@RestController
@RequestMapping(ROOT_URI_ITEMS)
public class ItemsController {

    @Inject
    private ItemFacade itemFacade;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ItemDTO> getItems() {
        return itemFacade.getAllItems();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final ItemDTO getById(@PathVariable("id") Long id) {
        try {
            return itemFacade.getItemById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/category={categoryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ItemDTO> getByCategory(@PathVariable("categoryName") String name) {
        try {
            return itemFacade.getItemsByCategory(name);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void delete(@PathVariable("id") Long id) {
        try {
            ItemDTO item = itemFacade.getItemById(id);
            itemFacade.deleteItem(item);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @RequestMapping(value = "/createLost", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void createLostItem(@RequestBody ItemCreateLostDTO item) {

        try {
            itemFacade.addItemLost(item);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/createFound", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void createFoundItem(@RequestBody ItemCreateFoundDTO item) {

        try {
            itemFacade.addItemFound(item);
        } catch (Exception ex) {
            throw new ResourceAlreadyExistingException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}/category", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ItemDTO addCategory(@PathVariable("id") long id, @RequestBody CategoryDTO category) {

        try {
            itemFacade.addCategoryToItem(id, category.getId());
            return itemFacade.getItemById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}/category", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ItemDTO removeCategory(@PathVariable("id") long id, @RequestBody CategoryDTO category) {

        try {
            itemFacade.removeCategoryFromItem(id, category.getId());
            return itemFacade.getItemById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{id}/owner", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ItemDTO changeOwner(@PathVariable("id") long id, @RequestBody UserDTO owner) {

        try {
            itemFacade.changeOwner(id, owner.getId());
            return itemFacade.getItemById(id);
        } catch (Exception ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        }
    }
}
