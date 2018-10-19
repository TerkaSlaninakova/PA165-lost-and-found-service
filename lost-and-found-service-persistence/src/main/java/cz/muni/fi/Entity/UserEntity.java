package cz.muni.fi.Entity;

/**
 * @author TerkaSlaninakova
 */
public class UserEntity {

    private Long id;
    private String name;
    private String email;
    private boolean isAdmin;

    /**
     *
     * @return name of the user
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @param name of the user to set
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     *
     * @return email of the user
     */
    public String getEmail(){
        return email;
    }

    /**
     *
     * @param email of the user to set
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     *
     * @return isAdmin property of user
     */
    public Boolean getIsAdmin(){
        return isAdmin;
    }

    /**
     *
     * @param isAdmin property of the user to set
     */
    public void setIsAdmin(Boolean isAdmin){
        this.isAdmin = isAdmin;
    }
}
