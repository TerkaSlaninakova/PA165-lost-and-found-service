package cz.muni.fi.dto;

import java.util.Objects;

/**
 *
 * @author Jakub Polacek
 */
public class LocationDTO {

    private Long id;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        LocationDTO location = (LocationDTO) o;
        return Objects.equals(id, location.getId()) &&
                Objects.equals(description, location.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
