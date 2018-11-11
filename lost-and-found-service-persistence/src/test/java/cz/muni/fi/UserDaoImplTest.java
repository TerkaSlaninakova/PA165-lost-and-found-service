package cz.muni.fi;

import cz.muni.fi.dao.UserDao;
import cz.muni.fi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

/**
 * Unit tests for userDao
 *
 * @author Terezia Slaninakova (445526)
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoImplTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserDao userDao;

    private User user, adminUser;

    @BeforeMethod
    public void setup() {
        user = new User();
        user.setName("UserName UserSurname");
        user.setEmail("user@gmail.com");
        user.setPassword("ultraSecretPassword");
        user.setIsAdmin(false);

        adminUser = new User();
        adminUser.setName("AdminUserName AdminUserSurname");
        adminUser.setEmail("adminUser@gmail.com");
        adminUser.setPassword("ultraSecretPasswordButThisTimeForAdmin");
        adminUser.setIsAdmin(true);
    }

    @Test
    public void testAddUser() {
        userDao.addUser(user);
        User foundUser = entityManager.find(User.class, user.getId());
        assertNotNull(foundUser);
        //assertEquals(user, foundUser); - To uncomment when equals and hashcode are implemented
    }

    @Test
    public void testGetUserByName() {
        entityManager.persist(user);
        List<User> foundUsers = userDao.getUserByName(user.getName());
        assertEquals(1, foundUsers.size());
        //assertEquals(foundUsers.get(0), user); - To uncomment when equals and hashcode are implemented
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddNullUser() {
        userDao.addUser(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddUserWithId() {
        entityManager.persist(user);
        userDao.addUser(user);
    }

    @Test(expectedExceptions = {ConstraintViolationException.class})
    public void testAddUserIncomplete() {
        User tmpUser = new User();
        userDao.addUser(tmpUser);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetNullUser() {
        User tmpUser = new User();
        userDao.getUserById(tmpUser.getId());
    }

    @Test
    public void testGetAllUsers() {
        entityManager.persist(user);
        entityManager.persist(adminUser);
        List<User> users = userDao.getAllUsers();
        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.contains(user));
        assertTrue(users.contains(adminUser));
    }

    @Test
    public void testUpdateUser() {
        entityManager.persist(user);
        User foundUser = entityManager.find(User.class, user.getId());
        assertNotNull(foundUser);
        String newEmail = "newuser@gmail.com";
        String newName = "new UserName UserSurname";
        String newPassword = "newultraSecretPassword";
        Boolean newAdminState = true;

        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setIsAdmin(newAdminState);

        userDao.updateUser(user);

        User updatedUser = entityManager.find(User.class, user.getId());

        assertEquals(updatedUser.getEmail(), newEmail);
        assertEquals(updatedUser.getName(), newName);
        assertEquals(updatedUser.getPassword(), newPassword);
        assertEquals(updatedUser.getIsAdmin(), newAdminState);
    }

    @Test
    public void testDeleteUser() {
        entityManager.persist(user);
        List<User> users = entityManager.createQuery("select e from User e", User.class)
                .getResultList();
        assertNotNull(users);
        assertEquals(1, users.size());
        userDao.deleteUser(user);
        users = entityManager.createQuery("select e from User e", User.class)
                .getResultList();

        assertNotNull(users);
        assertEquals(0, users.size());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNullId() {
        userDao.updateUser(user);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNullId() {
        userDao.deleteUser(user);
    }
}
