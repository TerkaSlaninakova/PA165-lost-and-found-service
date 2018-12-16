package cz.muni.fi.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Data Transfer Object for User
 * @author Augustin Nemec
 */
public class UserDTO {

    private Long id;
    private String name;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private boolean isAdmin;

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return isAdmin == userDTO.getIsAdmin() &&
                Objects.equals(id, userDTO.getId()) &&
                Objects.equals(name, userDTO.getName()) &&
                Objects.equals(password, userDTO.getPassword()) &&
                Objects.equals(email, userDTO.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, isAdmin);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
