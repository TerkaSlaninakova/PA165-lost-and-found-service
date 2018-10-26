package cz.muni.fi.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.awt.*;

/**
 * Entity for ItemEntity
 *
 * @author Augustin Nemec
 */

@Entity(name = "Item")
public class ItemEntity {

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

    @Column
    private String lostLocation;

    @Column
    private String foundLocation;

    @ManyToOne
    @NotNull
    private UserEntity owner;

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
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

    public String getLostLocation() {
        return lostLocation;
    }

    public void setLostLocation(String lostLocation) {
        this.lostLocation = lostLocation;
    }

    public String getFoundLocation() {
        return foundLocation;
    }

    public void setFoundLocation(String foundLocation) {
        this.foundLocation = foundLocation;
    }
}
