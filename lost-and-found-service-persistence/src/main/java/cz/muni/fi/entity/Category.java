package cz.muni.fi.entity;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.List;

/**
 *
 * @author Jakub Polacek
 */
@Entity(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String name;

    @Column
    private String attribute;

    @ManyToMany(mappedBy = "categories")
    private List<Item> items;

    public Long getId() {
        return id;
    }

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
}
