package cz.muni.fi.entity;

import java.awt.*;

/**
 * Entity for Item
 *
 * @author Augustin Nemec
 */
public class Item {

    private Long id;
    private String name;
    private String type;
    private String characteristics;
    private Image photo;
    private Status status;
    private String lostLocation;
    private String foundLocation;
    //private List<Category> categories - neviem ci moze mat item viac kategorii ale asi ano ked je vztah M...N


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

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
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
