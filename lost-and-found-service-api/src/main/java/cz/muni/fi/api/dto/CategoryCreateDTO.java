package cz.muni.fi.api.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Data Transfer Object for Category creation
 * @author Jakub Polacek
 */
public class CategoryCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String attribute;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryCreateDTO that = (CategoryCreateDTO) o;

        return Objects.equals(this.name, that.getName()) && Objects.equals(this.attribute, that.getAttribute());

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attribute);
    }

    @Override
    public String toString() {
        return "CategoryCreateDTO{" +
                "name='" + name + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }
}
