package cz.muni.fi.dto;

import cz.muni.fi.enums.Status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemDTO {

    private Long id;

    private String name;

    private String type;

    private String characteristics;

    private String imageMimeType;

    private byte[] image;

    private LocalDate foundDate, lostDate;

    private String archive;

    private Status status;

    private LocationDTO foundLocation;

    private LocationDTO lostLocation;

    private UserDTO owner;


    private List<CategoryDTO> categories;

    public UserDTO getOwner() {
        return this.owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
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
        return this.characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getImageMimeType() {
        return imageMimeType;
    }

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocationDTO getLostLocation() {
        return this.lostLocation;
    }

    public void setLostLocation(LocationDTO lostLocation) {
        this.lostLocation = lostLocation;
    }

    public LocationDTO getFoundLocation() {
        return this.foundLocation;
    }

    public void setFoundLocation(LocationDTO foundLocation) {
        this.foundLocation = foundLocation;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public LocalDate getFoundDate() {
        return this.foundDate;
    }

    public void setFoundDate(LocalDate foundDate) {
        this.foundDate = foundDate;
    }

    public LocalDate getLostDate() {
        return this.lostDate;
    }

    public void setLostDate(LocalDate lostDate) {
        this.lostDate = lostDate;
    }

    public String getArchive() {
        return this.archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemDTO)) return false;
        ItemDTO item = (ItemDTO) o;
        return Objects.equals(id, item.getId()) &&
                Objects.equals(name, item.getName()) &&
                Objects.equals(type, item.getType()) &&
                Objects.equals(characteristics, item.getCharacteristics()) &&
                Objects.equals(imageMimeType, item.getImageMimeType()) &&
                status == item.getStatus() &&
                Objects.equals(lostLocation, item.getLostLocation()) &&
                Objects.equals(foundLocation, item.getFoundLocation()) &&
                Objects.equals(lostDate, item.getLostDate()) &&
                Objects.equals(foundDate, item.getFoundDate()) &&
                Objects.equals(archive, item.getArchive()) &&
                Objects.equals(owner, item.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, name, type, characteristics,
                imageMimeType, status,foundLocation,
                lostLocation, foundDate, lostDate,
                archive, owner);
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", characteristics='" + characteristics + '\'' +
                ", imageMimeType='" + imageMimeType + '\'' +
                ", image=" + Arrays.toString(image) +
                ", foundDate=" + foundDate +
                ", lostDate=" + lostDate +
                ", archive='" + archive + '\'' +
                ", status=" + status +
                ", foundLocation=" + foundLocation +
                ", lostLocation=" + lostLocation +
                ", owner=" + owner +
                ", categories=" + categories +
                '}';
    }
}
