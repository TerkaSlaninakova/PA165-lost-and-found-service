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
}
