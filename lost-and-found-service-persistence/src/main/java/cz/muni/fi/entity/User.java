package cz.muni.fi.entity;

//import com.sun.istack.internal.NotNull;
import org.jetbrains.annotations.NotNull;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author TerkaSlaninakova
 */
@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String name;

    // TODO: Make secure by adding hash
    @Column
    private String password;

    @Column
    private String email;

    @Column
    private boolean isAdmin;

    @OneToMany(mappedBy = "owner")
    private Set<Item> items = new HashSet<>();

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public Boolean getIsAdmin(){
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
