package cz.muni.fi.service;

import cz.muni.fi.entity.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Service layer interface for User
 * @author Terezia Slaninakova (445526)
 */
public interface UserService {
    /**
     * Save User to DB
     * @param user user to be saved
     * @throws DataAccessException if User or User id is not null
     */
    void addUser(User user) throws DataAccessException;

    /**
     * Update User
     * @param user user to be updated
     * @throws DataAccessException if User or User id is null
     */
    void updateUser(User user) throws DataAccessException;

    /**
     * Delete User
     * @param user user to be deleted
     * @throws DataAccessException if User or User id is null
     */
    void deleteUser(User user) throws DataAccessException;;

    /**
     * Get user by his id
     * @param id id of user
     * @throws DataAccessException if User id is null
     * @return User object
     */
    User getUserById(Long id) throws DataAccessException;

    /**
     * Get user by his name
     * @param name name of user
     * @throws DataAccessException if User name is null
     * @return list of User objects
     */
    List<User> getUserByName(String name) throws DataAccessException;

    /**
     * Get all existing users
     * @return list of User objects
     */
    List<User> getAllUsers();
}
