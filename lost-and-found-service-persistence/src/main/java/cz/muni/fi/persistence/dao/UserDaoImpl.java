package cz.muni.fi.persistence.dao;

import cz.muni.fi.persistence.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Terezia Slaninakova (445526)
 */
@Repository
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
        entityManager.remove(getUserById(user.getId()));
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

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Cannot search for null email");
        try{
            return entityManager.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email).getSingleResult();
        }catch(NoResultException noResult){
            return null;
        }
    }
}
