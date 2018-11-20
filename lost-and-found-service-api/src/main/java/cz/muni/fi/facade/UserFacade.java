package cz.muni.fi.facade;

import cz.muni.fi.dto.UserDTO;

import java.util.List;

/**
 * Interface for UserFacade
 *
 * @author Augustin Nemec
 */

public interface UserFacade {

    /**
     * Save User to DB
     * @param userDTO UserDTO
     */
    void addUser(UserDTO userDTO);

    /**
     * Update User
     * @param userDTO UserChangeDTO
     */
    void updateUser(UserDTO userDTO);

    /**
     * Delete User
     * @param userDTO to be deleted
     */
    void deleteUser(UserDTO userDTO);

    /**
     * Get user by his id
     * @param id id of user
     * @return User or null
     */
    UserDTO getUserById(Long id);


    /**
     * Get all existing users
     * @return list of all users
     */
    List<UserDTO> getAllUsers();
}
