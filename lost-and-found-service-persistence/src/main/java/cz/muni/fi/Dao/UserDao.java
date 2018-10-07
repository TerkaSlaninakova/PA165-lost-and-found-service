package cz.muni.fi.Dao;

import cz.muni.fi.Entity.UserEntity;

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
    void findUser(Long id);

    void getAllUsers();
}
