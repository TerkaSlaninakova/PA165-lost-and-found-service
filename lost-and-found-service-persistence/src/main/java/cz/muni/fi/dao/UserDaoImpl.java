package cz.muni.fi.dao;

import cz.muni.fi.entity.UserEntity;

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

    public void addUser(UserEntity user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("Null user object provided");
        }
        em.persist(user);
    }

    public void updateUser(UserEntity user) throws IllegalArgumentException {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("Null user object or user id provided");
        }
        em.merge(user);
    }

    public void deleteUser(UserEntity user) throws IllegalArgumentException {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("Null user object or user id provided");
        }
        em.remove(user);
    }

    public UserEntity getUserById(Long id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("Null id provided");
        }
        return em.find(UserEntity.class, id);
    }

    public List<UserEntity> getAllUsers() {
        return em.createQuery("select e from User e", UserEntity.class)
                .getResultList();
    }
}
