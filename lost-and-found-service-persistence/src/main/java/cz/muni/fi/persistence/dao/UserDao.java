package cz.muni.fi.persistence.dao;

import cz.muni.fi.persistence.entity.User;

import java.util.List;

/**
 * @author Terezia Slaninakova (445526)
 */
public interface UserDao {

    /**
     * Save User to DB
     * @param user user to be saved
     * @throws IllegalArgumentException if User or User id is not null
     */
    void addUser(User user) throws IllegalArgumentException;

    /**
     * Update User
     * @param user user to be updated
     * @throws IllegalArgumentException if User or User id is null
     */
    void updateUser(User user) throws IllegalArgumentException;

    /**
     * Delete User
     * @param user user to be deleted
     * @throws IllegalArgumentException if User or User id is null
     */
    void deleteUser(User user) throws IllegalArgumentException;

    /**
     * Get user by his id
     * @param id id of user
     * @throws IllegalArgumentException if User id is null
     * @return User object
     */
    User getUserById(Long id) throws IllegalArgumentException;

    /**
     * Get user by his name
     * @param name name of user
     * @throws IllegalArgumentException if User name is null
     * @return list of User objects
     */
    List<User> getUserByName(String name) throws IllegalArgumentException;

    /**
     * Get all existing users
     * @return list of User objects
     */
    List<User> getAllUsers();

    User findByEmail(String email);
}
