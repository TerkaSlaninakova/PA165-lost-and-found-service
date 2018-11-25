package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.ItemDao;
import cz.muni.fi.persistence.entity.Category;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Item
 *
 * @author Terezia Slaninakova (445526)
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    LocationService locationService;

    @Override
    public void archiveItem(Item item) throws ServiceException {
        if (item == null) {
            throw new ServiceException("Item cannot be null");
        }
        if (item.getStatus() != Status.RESOLVED) {
            throw new ServiceException("Item must be in resolved state");
        }
        item.setArchive("{ 'Item': {" +
                " 'name': '" + item.getName() + "'," +
                " 'characteristics': '" + item.getCharacteristics() + "'," +
                " 'photo': '" + item.getImageMimeType() + "'," +
                " 'type': '" + item.getType() + "'," +
                " 'foundDate': '" + item.getFoundDate() + "'," +
                " 'id': '" + item.getId() + "'," +
                " 'categories' " + item.getCategories() + "'," +
                " 'lostLocation': '" + item.getLostLocation() + "'," +
                " 'foundLocation': '" + item.getFoundLocation() + "'," +
                " 'owner': '" + item.getOwner().getName() + "'," +
                " 'status': '" + item.getStatus() + "'," +
                "}}");

        try {
            itemDao.updateItem(item);
        } catch (Exception e) {
            throw new ServiceException("Could not archive Item", e);
        }
    }

    @Override
    public Item resolveLostItem(Item item, LocalDate foundDate, Location foundLocation) throws ServiceException {
        if (item == null) {
            throw new ServiceException("Item cannot be null");
        }
        if (foundDate == null) {
            throw new ServiceException("Found date cannot be null");
        }
        if (foundLocation == null) {
            throw new ServiceException("foundLocation cannot be null");
        }
        if (item.getStatus() != Status.CLAIM_RECEIVED_LOST) {
            throw new ServiceException("Item must be in CLAIM_RECEIVED_LOST state");
        }
        if (foundDate.isBefore(item.getLostDate())) {
            throw new ServiceException("Found date must be after lostDate");
        }
        if (item.getOwner() == null) {
            throw new ServiceException("Owner must be set");
        }

        item.setFoundLocation(foundLocation);
        item.setFoundDate(foundDate);
        item.setStatus(Status.RESOLVED);

        try {
            itemDao.updateItem(item);
        } catch (Exception e) {
            throw new ServiceException("Could not resolve Item", e);
        }
        return item;
    }

    @Override
    public Item resolveFoundItem(Item item, LocalDate lostDate, Location lostLocation, User owner) throws ServiceException {
        if (item == null) {
            throw new ServiceException("Item cannot be null");
        }
        if (lostDate == null) {
            throw new ServiceException("Lost date cannot be null");
        }
        if (owner == null) {
            throw new ServiceException("Owner to be set cannot be null");
        }
        if (lostLocation == null) {
            throw new ServiceException("Lost location cannot be null");
        }
        if (item.getStatus() != Status.CLAIM_RECEIVED_FOUND) {
            throw new ServiceException("Item must be in CLAIM_RECEIVED_FOUND state");
        }
        if (lostDate.isBefore(item.getFoundDate())) {
            throw new ServiceException("Lost date must be after foundDate");
        }
        if (item.getOwner() != null) {
            throw new ServiceException("Owner cannot be set");
        }

        item.setLostLocation(lostLocation);
        item.setLostDate(lostDate);
        item.setOwner(owner);
        item.setStatus(Status.RESOLVED);
        try {
            itemDao.updateItem(item);
        } catch (Exception e) {
            throw new ServiceException("Could not archive Item", e);
        }
        return item;
    }

    @Override
    public List<Item> getAllItemsWithStatus(Status status) {
        try {
            List<Item> allItems = itemDao.getAllItems();
            return allItems.stream().filter(object -> object.getStatus() == status).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Could not get items with status: " + status, e);
        }
    }

    @Override
    public void addItem(Item item) {
        try {
            itemDao.addItem(item);
        } catch (Exception e) {
            throw new ServiceException("Could not add Item", e);
        }
    }

    @Override
    public void deleteItem(Item item) {
        try {
            itemDao.deleteItem(item);
        } catch (Exception e) {
            throw new ServiceException("Could not delete Item", e);
        }
    }

    @Override
    public Item getItemById(Long id) {
        try {
            return itemDao.getItemById(id);
        } catch (Exception e) {
            throw new ServiceException("Could not delete Item", e);
        }
    }

    @Override
    public List<Item> getAllItems() {
        try {
            return itemDao.getAllItems();
        } catch (Exception e) {
            throw new ServiceException("Could not get all items", e);
        }
    }

    @Override
    public void updateItem(Item item) {
        try {
            itemDao.updateItem(item);
        } catch (Exception e) {
            throw new ServiceException("Could not get all items", e);
        }
    }

    @Override
    public void addCategoryToItem(Long itemId, Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        Item item = getItemById(itemId);

        item.addCategory(category);
        category.addItem(item);

        updateItem(item);
        categoryService.updateCategory(category);
    }

    @Override
    public void removeCategoryFromItem(Long itemId, Long categoryId) {
        if (itemId == null || categoryId == null) {
            throw new ServiceException("Arguments can't be null");
        }

        Category category = categoryService.getCategoryById(categoryId);
        Item item = getItemById(itemId);

        if (!item.getCategories().contains(category)) {
            throw new ServiceException("Item is not of given category, cannot remove");
        }

        item.removeCategory(category);
        category.removeItem(item);

        updateItem(item);
        categoryService.updateCategory(category);
    }

    @Override
    public void changeFoundLocation(Long itemId, Long locationId) {
        if (itemId == null) {
            throw new ServiceException("itemId can't be null");
        }

        Item item = getItemById(itemId);
        if (locationId != null) {
            Location location = locationService.getLocationById(locationId);

            if (location == null) {
                throw new ServiceException("Location with given id doesn't exist");
            }

            item.setFoundLocation(location);
        } else {
            item.setFoundLocation(null);
        }
        updateItem(item);
    }

    @Override
    public void changeLostLocation(Long itemId, Long locationId) {
        if (itemId == null) {
            throw new ServiceException("itemId can't be null");
        }

        Item item = getItemById(itemId);
        if (locationId != null) {
            Location location = locationService.getLocationById(locationId);

            if (location == null) {
                throw new ServiceException("Location with given id doesn't exist");
            }

            item.setLostLocation(location);
        } else {
            item.setLostLocation(null);
        }
        updateItem(item);
    }

    @Override
    public void changeUser(Long itemId, Long userId) {
        if (itemId == null) {
            throw new ServiceException("itemId can't be null");
        }

        Item item = getItemById(itemId);
        if (userId != null) {
            User user = userService.getUserById(userId);

            if (user == null) {
                throw new ServiceException("User with given id doesn't exist");
            }

            item.setOwner(user);
        } else {
            item.setOwner(null);
        }
        updateItem(item);
    }
}
