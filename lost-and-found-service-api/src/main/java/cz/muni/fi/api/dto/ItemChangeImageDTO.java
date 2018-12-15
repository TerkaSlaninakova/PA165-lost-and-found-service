package cz.muni.fi.api.dto;

import java.util.Arrays;
import java.util.Objects;

/**
 * Data Transfer Object for Image change in Item
 * @author Jakub Polacek
 */
public class ItemChangeImageDTO {
    private Long itemId;
    private byte[] image;
    private String imageMimeType;


    public String getImageMimeType() {
        return imageMimeType;
    }

    public void setImageMimeType(String imageMimeType) {
        this.imageMimeType = imageMimeType;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemChangeImageDTO that = (ItemChangeImageDTO) o;

        return Objects.equals(this.itemId, that.itemId)
                && Arrays.equals(this.image, that.image)
                && Objects.equals(this.imageMimeType, that.imageMimeType);

    }

    @Override
    public int hashCode() {
        int result = Objects.hash(itemId, imageMimeType);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString() {
        return "ItemChangeImageDTO{" +
                "itemId=" + itemId +
                ", image=" + Arrays.toString(image) +
                ", imageMimeType='" + imageMimeType + '\'' +
                '}';
    }
}
