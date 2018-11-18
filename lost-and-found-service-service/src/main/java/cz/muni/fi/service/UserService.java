package cz.muni.fi.service;

import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.exceptions.ServiceException;

import java.util.List;

/**
 * Service layer interface for User
 * @author Terezia Slaninakova (445526)
 */
public interface UserService {
    /**
     * Save User to DB
     * @param user user to be saved
     * @throws ServiceException if User or User id is not null or something unexpected happens
     */
    void addUser(User user) throws ServiceException;

    /**
     * Update User
     * @param user user to be updated
     * @throws ServiceException if User or User id is null or something unexpected happens
     */
    void updateUser(User user) throws ServiceException;

    /**
     * Delete User
     * @param user user to be deleted
     * @throws ServiceException if User or User id is null or something unexpected happens
     */
    void deleteUser(User user) throws ServiceException;

    /**
     * Get user by his id
     * @param id id of user
     * @throws ServiceException if User id is null or something unexpected happens
     * @return User object
     */
    User getUserById(Long id) throws ServiceException;

    /**
     * Get user by his name
     * @param name name of user
     * @throws ServiceException if User name is null or something unexpected happens
     * @return list of User objects
     */
    List<User> getUsersByName(String name) throws ServiceException;

    /**
     * Get all existing users
     * @throws ServiceException if something unexpected happens
     * @return list of User objects
     */
    List<User> getAllUsers() throws ServiceException;
}
