package cz.muni.fi;

import cz.muni.fi.dao.UserDao;
import cz.muni.fi.entity.Item;
import cz.muni.fi.entity.Status;
import cz.muni.fi.entity.User;
import cz.muni.fi.exceptions.ItemDaoException;
import org.junit.*;
import static org.assertj.core.api.Assertions.*;

import javax.ejb.EJBException;
import javax.ejb.NoSuchEJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import java.util.List;
import java.util.Properties;

import static junit.framework.TestCase.*;

/**
 * Unit tests for UserDao
 * @author TerkaSlaninakova
 */
public class UserDaoImplTest
{
    private static Context context;
    private static Properties p;

    private static UserDao userDao;
    private static User user, adminUser;

    private static Item notebook;

    @BeforeClass
    public static void suiteSetup()
    {
        p = new Properties();
        p.put("userDatabase", "new://Resource?type=DataSource");
        p.put("userDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("userDatabase.JdbcUrl", "jdbc:hsqldb:mem:userdb");

        context = EJBContainer.createEJBContainer(p).getContext();
    }

    @Before
    public void testSetup() throws Exception {
        userDao = (UserDao) context.lookup("java:global/lost-and-found-service-persistence/UserDaoImpl");

        notebook = new Item();
        notebook.setName("notebook");
        notebook.setCharacteristics("white, macbook");
        notebook.setPhoto("photo");
        notebook.setStatus(Status.IN_PROGRESS);

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

    @After
    public void testTeardown()
    {
        // make sure that userDao is cleaned after every test (to make tests independent of one another)
        try {
            List<User> users = userDao.getAllUsers();
            for (User user: users) {
                userDao.deleteUser(user);
            }
        }
        catch (NoSuchEJBException ex){
            // needed after negative test cases, userDao contains thrown exception and needs to be re-created
        }
    }

    @Test
    public void shouldReturn0UsersWhenEmpty() throws Exception {
        assertEquals(userDao.getAllUsers().size(), 0);
        assertNull(userDao.getUserById(new Long(0)));
    }

    @Test
    public void shouldAddUser() throws Exception {
        userDao.addUser(user);
        assertEquals(userDao.getAllUsers().size(), 1);
    }

    @Test
    public void shouldAddUserWithItems() throws Exception {
        user.getItems().add(notebook);
        userDao.addUser(user);
    }

    @Test
    public void shouldNotCreateAdditionalUserIfTheSameOneAdded() throws Exception {
        userDao.addUser(user);
        userDao.addUser(user);
        assertEquals(userDao.getAllUsers().size(), 1);
        userDao.deleteUser(user);
        userDao.addUser(new User());
        assertEquals(userDao.getAllUsers().size(), 1);
    }

    @Test
    public void ShouldUpdateUser() throws Exception {
        userDao.addUser(user);
        String newEmail = "newuser@gmail.com";
        String newName = "new UserName UserSurname";
        String newPassword = "newultraSecretPassword";
        Boolean newAdminState = true;

        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setIsAdmin(newAdminState);

        userDao.updateUser(user);

        User updatedUser = userDao.getUserById(user.getId());

        assertEquals(updatedUser.getEmail(), newEmail);
        assertEquals(updatedUser.getName(), newName);
        assertEquals(updatedUser.getPassword(), newPassword);
        assertEquals(updatedUser.getIsAdmin(), newAdminState);
    }

    @Test
    public void ShouldUpdateUserWithItems() throws Exception {
        userDao.addUser(user);
        String newEmail = "newuser@gmail.com";
        String newName = "new UserName UserSurname";
        String newPassword = "newultraSecretPassword";
        Boolean newAdminState = true;

        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);
        user.setIsAdmin(newAdminState);
        user.getItems().add(notebook);
        userDao.updateUser(user);

        User updatedUser = userDao.getUserById(user.getId());

        assertEquals(updatedUser.getEmail(), newEmail);
        assertEquals(updatedUser.getName(), newName);
        assertEquals(updatedUser.getPassword(), newPassword);
        assertEquals(updatedUser.getIsAdmin(), newAdminState);
        assertEquals(updatedUser.getItems().size(), 1);
    }

    @Test
    public void ShouldUpdateUserWhenNoChange() throws Exception {
        userDao.addUser(user);
        userDao.updateUser(user);

        User updatedUser = userDao.getUserById(user.getId());

        assertEquals(user, updatedUser);
    }

    @Test
    public void ShouldDeleteUser() throws Exception {
        userDao.addUser(user);
        userDao.addUser(adminUser);
        assertEquals(userDao.getAllUsers().size(), 2);
        userDao.deleteUser(user);
        assertEquals(userDao.getAllUsers().size(), 1);

        assertNull(userDao.getUserById(user.getId()));
        assertEquals(userDao.getUserById(adminUser.getId()), adminUser);
    }

    @Test
    public void shouldFailOnAddNullUser() throws Exception {
        // Have to catch EJBException to get to the real one
        try {
            userDao.addUser(null);
        }
        catch(EJBException ex){
            Exception causedByException = ex.getCausedByException();
            assertTrue(causedByException instanceof  IllegalArgumentException);
            assertEquals(causedByException.getMessage(), "Null user object provided");
        }
    }

    @Test
    public void shouldFailOnAddNullUpdate() throws Exception {
        assertThatThrownBy(() -> userDao.updateUser(null))
                .hasCauseInstanceOf(IllegalArgumentException.class)
                .hasStackTraceContaining("Null user object or user id provided");
    }

    @Test
    public void shouldFailOnAddNullDelete() throws Exception {
        assertThatThrownBy(() -> userDao.deleteUser(null))
                .hasCauseInstanceOf(IllegalArgumentException.class)
                .hasStackTraceContaining("Null user object or user id provided");
    }

    @Test
    public void shouldFailOnAddNullGetById() throws Exception {
        assertThatThrownBy(() -> userDao.getUserById(null))
                .hasCauseInstanceOf(IllegalArgumentException.class)
                .hasStackTraceContaining("Null id provided");
    }
}
