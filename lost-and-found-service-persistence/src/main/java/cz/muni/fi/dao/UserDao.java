package cz.muni.fi.dao;

import cz.muni.fi.entity.User;

import java.util.List;

/**
 * @author TerkaSlaninakova
 */
public interface UserDao {

    /**
     * @param user
     */
    void addUser(User user);

    /**
     * @param user
     */
    void updateUser(User user);

    /**
     * @param user
     */
    void deleteUser(User user);

    /**
     * @param id
     */
    User getUserById(Long id);

    List<User> getAllUsers();
}
