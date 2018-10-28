package cz.muni.fi.entity;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

/**
 * Entity for Item
 *
 * @author Augustin Nemec
 */

@Entity(name = "Item")
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

    @Column
    @NotNull
    private Status status;

    @ManyToOne(fetch=FetchType.LAZY)
    private Location location;

    @ManyToOne(fetch=FetchType.LAZY)
    @NotNull
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
}
