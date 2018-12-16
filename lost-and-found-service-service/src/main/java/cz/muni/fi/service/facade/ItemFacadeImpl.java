package cz.muni.fi.service.facade;

import cz.muni.fi.api.dto.*;
import cz.muni.fi.api.enums.Status;
import cz.muni.fi.api.facade.ItemFacade;
import cz.muni.fi.api.facade.LocationFacade;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.BeanMappingService;
import cz.muni.fi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of ItemFacade
 *
 * @author Jakub Polacek
 */

@Service
@Transactional
public class ItemFacadeImpl implements ItemFacade {

    @Autowired
    private ItemService itemService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private LocationFacade locationFacade;

    @Override
    public void addItemLost(ItemCreateLostDTO itemCreateDTO) {
        ItemDTO itemDTO = beanMappingService.mapTo(itemCreateDTO, ItemDTO.class);
        itemDTO.setLostLocation(locationFacade.getLocationById(itemCreateDTO.getLostLocationId()));
        itemDTO.setStatus(Status.CLAIM_RECEIVED_LOST);
        itemDTO.setLostDate(LocalDate.now());

        addItem(itemDTO);
    }

    @Override
    public void addItemFound(ItemCreateFoundDTO itemCreateDTO) {
        ItemDTO itemDTO = beanMappingService.mapTo(itemCreateDTO, ItemDTO.class);
        itemDTO.setFoundLocation(locationFacade.getLocationById(itemCreateDTO.getFoundLocationId()));
        itemDTO.setStatus(Status.CLAIM_RECEIVED_FOUND);
        itemDTO.setFoundDate(LocalDate.now());

        addItem(itemDTO);
    }

    private void addItem(ItemDTO itemDTO) {
        itemDTO.setCategories(new ArrayList<>());
        itemService.addItem(beanMappingService.mapTo(itemDTO, Item.class));
    }

    @Override
    public void updateItem(ItemDTO itemDTO) {
        itemService.updateItem(beanMappingService.mapTo(itemDTO, Item.class));
    }

    @Override
    public void addCategoryToItem(Long itemId, Long categoryId) {
        itemService.addCategoryToItem(itemId, categoryId);
    }

    @Override
    public void removeCategoryFromItem(Long itemId, Long categoryId) {
        itemService.removeCategoryFromItem(itemId, categoryId);
    }


    @Override
    public void changeFoundLocation(Long itemId, Long locationId) {
        itemService.changeFoundLocation(itemId, locationId);
    }

    @Override
    public void changeLostLocation(Long itemId, Long locationId) {
        itemService.changeLostLocation(itemId, locationId);
    }

    @Override
    public void changeOwner(Long itemId, Long userId) {
        itemService.changeOwner(itemId, userId);
    }

    @Override
    public void deleteItem(ItemDTO itemDTO) {
        itemService.deleteItem(beanMappingService.mapTo(itemDTO, Item.class));
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return beanMappingService.mapTo(itemService.getAllItems(), ItemDTO.class);
    }

    @Override
    public List<ItemDTO> getItemsByCategory(String categoryName) {
        return getAllItems().stream().
                filter(
                        item -> item.getCategories().stream()
                                .anyMatch(categoryDTO -> categoryDTO.getName().equals(categoryName)))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDTO getItemById(Long id) {
        return beanMappingService.mapTo(itemService.getItemById(id), ItemDTO.class);
    }

    @Override
    public void archiveItem(ItemDTO itemDTO) {
        itemService.archiveItem(beanMappingService.mapTo(itemDTO, Item.class));
    }

    @Override
    public void resolveLostItem(ItemDTO itemDTO, LocalDate foundDate, LocationDTO foundLocation) {
        itemService.resolveLostItem(
                beanMappingService.mapTo(itemDTO, Item.class),
                foundDate,
                beanMappingService.mapTo(foundLocation, Location.class));
    }

    @Override
    public void resolveFoundItem(ItemDTO itemDTO, LocalDate lostDate, LocationDTO lostLocation, UserDTO owner) {
        itemService.resolveFoundItem(
                beanMappingService.mapTo(itemDTO, Item.class),
                lostDate,
                beanMappingService.mapTo(lostDate, Location.class),
                beanMappingService.mapTo(owner, User.class));
    }

    @Override
    public void changeImage(ItemChangeImageDTO imageChangeDTO) {
        ItemDTO itemDTO = getItemById(imageChangeDTO.getItemId());
        itemDTO.setImage(imageChangeDTO.getImage());
        itemDTO.setImageMimeType(imageChangeDTO.getImageMimeType());

        updateItem(itemDTO);
    }
}
