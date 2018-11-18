package cz.muni.fi.dto;

import com.sun.istack.internal.NotNull;
import cz.muni.fi.enums.Status;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Jakub Polacek
 */
public class ItemCreateDTO {

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
    private String archive;

    @NotNull
    private Status status;


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
        return foundDate;
    }

    public void setFoundDate(LocalDate foundDate) {
        this.foundDate = foundDate;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCreateDTO that = (ItemCreateDTO) o;

        return Objects.equals(this.name, that.getName())
                && Objects.equals(this.archive, that.archive)
                && Objects.equals(this.characteristics, that.characteristics)
                && Objects.equals(this.foundDate, that.foundDate)
                && Objects.equals(this.status, that.status)
                && Objects.equals(this.type, that.type);

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, archive, characteristics, foundDate, status, type);
    }

    @Override
    public String toString() {
        return "ItemCreateDTO{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", characteristics='" + characteristics + '\'' +
                ", foundDate=" + foundDate +
                ", archive='" + archive + '\'' +
                ", status=" + status +
                '}';
    }
}
