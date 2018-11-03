package cz.muni.fi.entity;

//import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.NotNull;
import javax.persistence.*;
import java.util.List;

/**
 *
 * @author Jakub Polacek
 */
@Entity(name = "Category")
public class Category extends AbstractEntity {


    @NotNull
    @Column
    private String name;

    @Column
    private String attribute;

    @ManyToMany(mappedBy = "categories")
    private List<Item> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
