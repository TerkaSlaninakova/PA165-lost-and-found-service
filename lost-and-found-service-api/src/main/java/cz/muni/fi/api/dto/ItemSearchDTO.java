package cz.muni.fi.api.dto;

import cz.muni.fi.api.enums.Status;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Data Transfer Object for Item search
 * @author Terézia Slanináková
 */
public class ItemSearchDTO {

    private String name;
    private String categoryName;


    private Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemSearchDTO that = (ItemSearchDTO) o;

        return Objects.equals(this.name, that.getName());

    }
    // TODO: Finish hash and toString properly
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ItemCreateDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
