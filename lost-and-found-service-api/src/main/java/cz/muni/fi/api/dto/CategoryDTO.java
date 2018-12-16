package cz.muni.fi.api.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object for Category
 * @author Jakub Polacek
 */

public class CategoryDTO {

    private Long id;

    @NotNull
    private String name;

    private String attribute;

    private List<ItemDTO> items;

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

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO)) return false;
        CategoryDTO category = (CategoryDTO) o;
        return Objects.equals(id, category.getId()) &&
                Objects.equals(name, category.getName()) &&
                Objects.equals(attribute, category.getAttribute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, attribute);
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attribute='" + attribute + '\'' +
                ", items=" + items +
                '}';
    }
}
