package cz.muni.fi.dao;

import cz.muni.fi.entity.UserEntity;

import java.util.List;

/**
 * @author TerkaSlaninakova
 */
public interface UserDao {

    /**
     * @param user
     */
    void addUser(UserEntity user);

    /**
     * @param user
     */
    void updateUser(UserEntity user);

    /**
     * @param user
     */
    void deleteUser(UserEntity user);

    /**
     * @param id
     */
    UserEntity getUserById(Long id);

    List<UserEntity> getAllUsers();
}
