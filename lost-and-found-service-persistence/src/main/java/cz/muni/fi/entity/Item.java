package cz.muni.fi.entity;

import cz.muni.fi.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Entity for Item
 *
 * @author Augustin Nemec
 */

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String characteristics;

    @Column
    private String photo;

    @NotNull
    @Column
    private Status status;

    @ManyToOne
    private Location location;

    @ManyToOne
    private User owner;

    @ManyToMany
    private List<Category> categories;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location lostLocation) {
        this.location = lostLocation;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.getId()) &&
                Objects.equals(name, item.getName()) &&
                Objects.equals(type, item.getType()) &&
                Objects.equals(characteristics, item.getCharacteristics()) &&
                Objects.equals(photo, item.getPhoto()) &&
                status == item.getStatus() &&
                Objects.equals(location, item.getLocation()) &&
                Objects.equals(owner, item.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, characteristics, photo, status, location, owner);
    }
}
