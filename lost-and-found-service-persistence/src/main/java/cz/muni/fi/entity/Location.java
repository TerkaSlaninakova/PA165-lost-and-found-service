package cz.muni.fi.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub Polacek
 */

@Entity(name = "Location")
public class Location extends AbstractEntity {

    @Column
    private String description;

    @OneToMany(mappedBy="location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    /**
     * Adds item to items list if item not null, otherwise throws exception
     * @param item
     */
    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        items.add(item);
    }

    /**
     * Removes item from items list, if item not null and exists in list, otherwise throws exception
     * @param item
     */
    public void removeItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }
        if (items.contains(item)) {
            items.remove(item);
        }
        else {
            throw new IllegalArgumentException("Item not in category");
        }
    }
}
