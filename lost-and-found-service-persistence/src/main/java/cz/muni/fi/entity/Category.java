package cz.muni.fi.entity;

import javax.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Jakub Polacek
 */
@Entity
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

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.getId()) &&
                Objects.equals(name, category.getName()) &&
                Objects.equals(attribute, category.getAttribute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, attribute);
    }
}
