package cz.muni.fi.service;


import cz.muni.fi.persistence.dao.UserDao;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.config.ServiceConfiguration;
import cz.muni.fi.service.exceptions.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests for LocationService
 * @author Jakub Polacek
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService = new UserServiceImpl();

    private User admin, lostItemUser, foundItemUser;

    @BeforeClass
    public void beforeClass() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void beforeTest() {

        admin = new User();
        admin.setIsAdmin(true);
        admin.setName("admin");
        admin.setEmail("admin@lostandfound.com");
        admin.setPassword("thouShallNotPass");
        
        lostItemUser = new User();
        lostItemUser.setId(2L);
        lostItemUser.setIsAdmin(false);
        lostItemUser.setName("Karel");
        lostItemUser.setEmail("karel96@gmail.com");
        lostItemUser.setPassword("karlikxoxo");

        foundItemUser = new User();
        foundItemUser.setId(3L);
        foundItemUser.setIsAdmin(false);
        foundItemUser.setName("Andreas");
        foundItemUser.setEmail("andreTheGiant@gmail.com");
        foundItemUser.setPassword("YouAreABigGuy_ForYou");
    }

    @Test
    public void testAddUser() {

        doAnswer(invocationOnMock -> {
            User user = (User) invocationOnMock.getArguments()[0];
            user.setId(1L);
            return null;
        }).when(userDao).addUser(admin);

        userService.addUser(admin);
        assertThat(admin.getId() == 1L);
        verify(userDao).addUser(admin);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddNullUser() {
        doThrow(new IllegalArgumentException()).when(userDao).addUser(null);
        userService.addUser(null);
    }

    @Test
    public void testUpdateUser() {
        doNothing().when(userDao).updateUser(admin);
        userService.updateUser(admin);
        verify(userDao).updateUser(admin);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateNullUser() {
        doThrow(new IllegalArgumentException()).when(userDao).updateUser(null);
        userService.updateUser(null);
    }

    @Test
    public void testGetAllUsers()  {
        when(userDao.getAllUsers()).thenReturn(Arrays.asList(admin, lostItemUser, foundItemUser));
        assertThat(userService.getAllUsers()).containsExactlyInAnyOrder(admin, lostItemUser, foundItemUser);
        verify(userDao).getAllUsers();
    }

    @Test
    public void testGetUserById()  {
        when(userDao.getUserById(2L)).thenReturn(lostItemUser);
        assertThat(userService.getUserById(lostItemUser.getId())).isEqualToComparingFieldByField(lostItemUser);
        verify(userDao).getUserById(lostItemUser.getId());
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testGetUserByNullId() {
        doThrow(new IllegalArgumentException()).when(userDao).getUserById(null);
        userService.getUserById(null);
    }

    @Test
    public void testDeleteUser() {
        when(userDao.getUserById(2L)).thenReturn(lostItemUser);
        doNothing().when(userDao).deleteUser(lostItemUser);
        userService.deleteUser(lostItemUser);
        verify(userDao).deleteUser(lostItemUser);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteUserByNull() {
        doThrow(new IllegalArgumentException()).when(userDao).deleteUser(null);
        userService.deleteUser(null);
    }


    @Test
    public void testGetUsersByName() {
        when(userDao.getAllUsers()).thenReturn(Arrays.asList(admin, lostItemUser, foundItemUser));
        assertThat(userService.getUsersByName("Andreas")).containsExactly(foundItemUser);
    }


    @Test
    public void testGetUsersByNonexistingName() {
        when(userDao.getAllUsers()).thenReturn(Arrays.asList(admin, lostItemUser, foundItemUser));
        assertThat(userService.getUsersByName("NotInDb")).isEmpty();
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testGetUsersByNullName() {
        when(userDao.getAllUsers()).thenReturn(Arrays.asList(admin, lostItemUser, foundItemUser));
        userService.getUsersByName(null);
    }

}
