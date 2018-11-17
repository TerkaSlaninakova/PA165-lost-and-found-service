package cz.muni.fi.persistence.entity;

import cz.muni.fi.persistence.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

    @Column
    private LocalDate foundDate;

    @Column
    private String archive;

    @NotNull
    @Column
    private Status status;

    @ManyToOne
    private Location foundLocation;

    @ManyToOne
    private Location lostLocation;

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

    public void setId(Long id) {
        this.id = id;
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

    public Location getLostLocation() {
        return this.lostLocation;
    }

    public void setLostLocation(Location lostLocation) {
        this.lostLocation = lostLocation;
    }

    public Location getFoundLocation() {
        return this.foundLocation;
    }

    public void setFoundLocation(Location foundLocation) {
        this.foundLocation = foundLocation;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public LocalDate getFoundDate() {
        return this.foundDate;
    }

    public void setFoundDate(LocalDate foundDate) {
        this.foundDate = foundDate;
    }

    public String getArchive() {
        return this.archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
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
                Objects.equals(lostLocation, item.getLostLocation()) &&
                Objects.equals(foundLocation, item.getFoundLocation()) &&
                Objects.equals(archive, item.getArchive()) &&
                Objects.equals(owner, item.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, characteristics, photo, status, foundLocation, lostLocation, archive, owner);
    }
}
