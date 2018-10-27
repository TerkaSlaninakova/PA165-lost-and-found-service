package cz.muni.fi.dao;

import cz.muni.fi.entity.User;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * @author TerkaSlaninakova
 */
@Stateful
public class UserDaoImpl implements UserDao {

    @PersistenceContext(unitName = "user-unit", type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public void addUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Null user object provided");
        }
        em.persist(user);
    }

    public void updateUser(User user) throws IllegalArgumentException {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("Null user object or user id provided");
        }
        em.merge(user);
    }

    public void deleteUser(User user) throws IllegalArgumentException {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("Null user object or user id provided");
        }
        em.remove(user);
    }

    public User getUserById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Null id provided");
        }
        return em.find(User.class, id);
    }

    public List<User> getAllUsers() {
        return em.createQuery("select e from User e", User.class)
                .getResultList();
    }
}
