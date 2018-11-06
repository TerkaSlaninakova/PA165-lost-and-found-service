package cz.muni.fi.dao;

import cz.muni.fi.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Terezia Slaninakova (445526)
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (user.getId() != null) {
            throw new IllegalArgumentException("User id is not null");
        }
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("User id is null");
        }
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (user.getId() == null) {
            throw new IllegalArgumentException("User id is null");
        }
        entityManager.remove(user);
    }

    @Override
    public User getUserById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Id is null");
        }
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getUserByName(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }
        return entityManager.createQuery("SELECT u FROM User u WHERE name=:name",
                User.class).setParameter("name", name).getResultList();
    }


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select e from User e", User.class)
                .getResultList();
    }
}
