package cz.muni.fi.service;

import cz.muni.fi.dao.ItemDao;
import cz.muni.fi.entity.Item;
import cz.muni.fi.service.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.List;

/**
 * Service layer for Item
 * @author Terezia Slaninakova (445526)
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    public Item archiveItem(Item item, LocalDate foundDate) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (foundDate == null) {
            throw new IllegalArgumentException("Found date cannot be null");
        }
        if (foundDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Found date must be set into the past");
        }
        item.setFoundDate(foundDate);

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
