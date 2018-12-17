package cz.muni.fi.api.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
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
    @PastOrPresent(message="Date cannot be in the tfuture")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foundDate;

    @NotNull
    private Long foundLocationId;

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

    public Long getFoundLocationId() {
        return this.foundLocationId;
    }

    public void setFoundLocationId(Long foundLocation) {
        this.foundLocationId = foundLocation;
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
        return "ItemCreateFoundDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", characteristics='" + characteristics + '\'' +
                ", foundDate=" + foundDate +
                ", foundLocationId=" + foundLocationId +
                '}';
    }
}
