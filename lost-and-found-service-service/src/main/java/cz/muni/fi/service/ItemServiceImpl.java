package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.ItemDao;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Item
 * @author Terezia Slaninakova (445526)
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    public Item archiveItem(Item item) throws ServiceException{
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (item.getStatus() != Status.RESOLVED) {
            throw new IllegalArgumentException("Item must be in resolved state");
        }
        item.setArchive("{ 'Item': {" +
                    " 'name': '" + item.getName() +"'," +
                    " 'characteristics': '" + item.getCharacteristics() + "'," +
                    " 'photo': '" + item.getPhoto() + "'," +
                    " 'type': '" + item.getType() + "'," +
                    " 'foundDate': '" + item.getFoundDate().toString() + "'," +
                    " 'id': '" + item.getId() + "'," +
                    " 'categories' " + item.getCategories() + "'," +
                    " 'lostLocation': '" + item.getLostLocation() + "'," +
                    " 'foundLocation': '" + item.getFoundLocation() + "'," +
                    " 'owner': '" + item.getOwner() + "'," +
                    " 'status': '" + item.getStatus() + "'," +
                    "}}");

        try {
            itemDao.updateItem(item);
        } catch (EntityExistsException e) {
            throw new ServiceException("Could not archive Item", e);
        }
        return item;
    }

    @Override
    public Item resolveLostItem(Item item, LocalDate foundDate, Location foundLocation) throws ServiceException{
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (foundDate == null) {
            throw new IllegalArgumentException("Found date cannot be null");
        }
        if (foundLocation == null) {
            throw new IllegalArgumentException("foundLocation cannot be null");
        }
        if (item.getStatus() != Status.CLAIM_RECEIVED_LOST) {
            throw new IllegalStateException("Item must be in CLAIM_RECEIVED_LOST state");
        }
        if (foundDate.isAfter(item.getLostDate())) {
            throw new IllegalStateException("Found date must be after lostDate");
        }
        if (item.getOwner() == null) {
            throw new IllegalStateException("Owner must be set");
        }

        item.setFoundLocation(foundLocation);
        item.setFoundDate(foundDate);
        item.setStatus(Status.RESOLVED);

        try {
            itemDao.updateItem(item);
        } catch (EntityExistsException e) {
            throw new ServiceException("Could not resolve Item", e);
        }
        return item;
    }

    @Override
    public Item resolveFoundItem(Item item, LocalDate lostDate, Location lostLocation, User owner) throws ServiceException{
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (lostDate == null) {
            throw new IllegalArgumentException("Lost date cannot be null");
        }
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null");
        }
        if (lostLocation == null) {
            throw new IllegalArgumentException("lostLocation cannot be null");
        }
        if (item.getStatus() != Status.CLAIM_RECEIVED_FOUND) {
            throw new IllegalStateException("Item must be in CLAIM_RECEIVED_FOUND state");
        }
        if (lostDate.isAfter(item.getLostDate())) {
            throw new IllegalStateException("Lost date must be after foundDate");
        }
        if (item.getOwner() != null) {
            throw new IllegalStateException("Owner cannot be set");
        }

        item.setLostLocation(lostLocation);
        item.setLostDate(lostDate);
        item.setOwner(owner);
        item.setStatus(Status.RESOLVED);
        try {
            itemDao.updateItem(item);
        } catch (EntityExistsException e) {
            throw new ServiceException("Could not archive Item", e);
        }
        return item;
    }

    @Override
    public List<Item> getAllItemsWithStatus(Status status) {
        try {
            List<Item> allItems = itemDao.getAllItems();
            return allItems.stream().filter(object -> object.getStatus() == status).collect(Collectors.toList());
        } catch (EntityExistsException e) {
            throw new ServiceException("Could not get items with status: " + status, e);
        }
    }

    @Override
    public Item addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        try {
            itemDao.addItem(item);
        } catch (EntityExistsException e) {
            throw new ServiceException("Could not add Item", e);
        }
        return item;
    }

    @Override
    public Item deleteItem(Item item) {
        if (item == null || item.getId() == null) {
            throw new IllegalArgumentException("Item is null");
        }
        try {
            itemDao.deleteItem(item);
        } catch (EntityExistsException e) {
            throw new ServiceException("Could not delete Item", e);
        }
        return item;
    }

    @Override
    public Item getItembyId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Item's id is null");
        }
        try {
            return itemDao.getItembyId(id);
        } catch (EntityExistsException e) {
            throw new ServiceException("Could not delete Item", e);
        }
    }

    @Override
    public List<Item> getAllItems() {
        try {
            return itemDao.getAllItems();
        } catch (EntityExistsException e) {
            throw new ServiceException("Could not get all items", e);
        }
    }

    @Override
    public Item updateItem(Item item) {
        if (item == null || item.getId() == null) {
            throw new IllegalArgumentException("Item is null");
        }
        try {
            itemDao.updateItem(item);
        } catch (EntityExistsException e) {
            throw new ServiceException("Could not get all items", e);
        }
        return item;
    }
}
