package cz.muni.fi.service;

import cz.muni.fi.persistence.dao.UserDao;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service layer for User
 *
 * @author Jakub Polacek
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) throws ServiceException {
        try {
            userDao.addUser(user);
        } catch (Exception e) {
            throw new ServiceException("Could not add user.", e);
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        try {
            userDao.updateUser(user);
        } catch (Exception e) {
            throw new ServiceException("Could not update user.", e);
        }
    }

    @Override
    public void deleteUser(User user) throws ServiceException {
        try {
            userDao.deleteUser(user);
        } catch (Exception e) {
            throw new ServiceException("Could not delete user.", e);
        }
    }

    @Override
    public List<User> getUsersByName(String name) throws ServiceException {
        try {
            if (name == null) {
                throw new IllegalArgumentException("Null name argument.");
            }
            return userDao.getAllUsers().stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Could not get users by name.", e);
        }
    }

    @Override
    public User getUserById(Long id) throws ServiceException {
        try {
            return userDao.getUserById(id);
        } catch (Exception e) {
            throw new ServiceException("Could not get user by id.", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userDao.getAllUsers();
        } catch (Exception e) {
            throw new ServiceException("Could not get all categories.", e);
        }
    }


}