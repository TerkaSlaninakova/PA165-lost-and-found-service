package cz.muni.fi.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author Jakub Polacek
 */

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
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
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.getId()) &&
                Objects.equals(description, location.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
