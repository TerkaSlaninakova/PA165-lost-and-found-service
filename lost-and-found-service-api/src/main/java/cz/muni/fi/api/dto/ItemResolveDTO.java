package cz.muni.fi.api.dto;

import cz.muni.fi.api.enums.Status;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Data Transfer Object for Item resolve
 * @author Terézia Slanináková
 */
public class ItemResolveDTO {
    private Long id;

    @Size(min = 3, max = 50)
    private String name;

    @Size(max = 50)
    private String type;

    @Size(max = 150)
    private String characteristics;

    @PastOrPresent(message="Date cannot be set in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Long locationId;

    private Long ownerId;

    private Status status;

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

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }


    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemResolveDTO that = (ItemResolveDTO) o;

        return Objects.equals(this.name, that.getName())
                && Objects.equals(this.characteristics, that.characteristics)
                && Objects.equals(this.type, that.type);

    }
    // TODO: Finish hash and toString properly
    @Override
    public int hashCode() {
        return Objects.hash(name, characteristics, type);
    }

    @Override
    public String toString() {
        return "ItemCreateFoundDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", characteristics='" + characteristics + '\'' +
                '}';
    }
}
