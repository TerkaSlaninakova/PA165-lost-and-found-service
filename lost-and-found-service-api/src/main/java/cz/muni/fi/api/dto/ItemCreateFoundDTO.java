package cz.muni.fi.api.dto;

import cz.muni.fi.api.enums.Status;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Data Transfer Object for Item creation
 * @author Jakub Polacek
 */
public class ItemCreateFoundDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String type;

    @NotNull
    @Size(max = 150)
    private String characteristics;

    @NotNull
    private LocalDate foundDate;

    @NotNull
    private LocationDTO foundLocation;

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

    public LocalDate getFoundDate() {
        return this.foundDate;
    }

    public void setFoundDate(LocalDate foundDate) {
        this.foundDate = foundDate;
    }

    public LocationDTO getFoundLocation() {
        return this.foundLocation;
    }

    public void setFoundLocation(LocationDTO foundLocation) {
        this.foundLocation = foundLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCreateFoundDTO that = (ItemCreateFoundDTO) o;

        return Objects.equals(this.name, that.getName())
                && Objects.equals(this.characteristics, that.characteristics)
                && Objects.equals(this.type, that.type);

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, characteristics, type);
    }

    @Override
    public String toString() {
        return "ItemCreateDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", characteristics='" + characteristics + '\'' +
                '}';
    }
}
