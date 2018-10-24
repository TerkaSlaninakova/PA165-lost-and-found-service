package cz.muni.fi.entity;

import com.sun.istack.internal.NotNull;
import javax.persistence.*;

/**
 *
 * @author Jakub Polacek
 */
@Entity(name = "Category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String name;

    @Column
    private String attribute;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
