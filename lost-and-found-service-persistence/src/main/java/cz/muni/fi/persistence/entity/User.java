package cz.muni.fi.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Terezia Slaninakova (445526)
 */
@Entity
@Table(name = "Users") // have to rename the table because 'User' is a reserved keyword in derby
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(length = 50, nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean isAdmin;

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isAdmin == user.getIsAdmin() &&
                Objects.equals(id, user.getId()) &&
                Objects.equals(name, user.getName()) &&
                Objects.equals(password, user.getPassword()) &&
                Objects.equals(email, user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, isAdmin);
    }

}
