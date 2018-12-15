package cz.muni.fi.service.facade;

import cz.muni.fi.api.dto.UserDTO;
import cz.muni.fi.api.facade.UserFacade;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.BeanMappingService;
import cz.muni.fi.service.BeanMappingServiceImpl;
import cz.muni.fi.service.UserService;
import cz.muni.fi.service.config.ServiceConfiguration;
import cz.muni.fi.service.facade.UserFacadeImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
/**
 * Tests for UserFacade
 *
 * @author Augustin Nemec
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFacade userFacade = new UserFacadeImpl();

    @Spy
    @Autowired
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    private User user1, user2;
    private UserDTO userDTO;


    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        user1 = new User();
        user1.setName("John");
        user1.setId(10L);
        user1.setEmail("john@gmail.com");
        user1.setPassword("123");
        user1.setIsAdmin(false);

        user2 = new User();
        user2.setName("Paul");

        userDTO = beanMappingService.mapTo(user1, UserDTO.class);

    }

    @Test
    public void testAddUser() {
        userFacade.addUser(userDTO);
        verify(userService).addUser(any(User.class));
    }

    @Test
    public void testUpdateUser() {
        userFacade.updateUser(userDTO);
        verify(userService).updateUser(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        userFacade.deleteUser(userDTO);
        verify(userService).deleteUser(any(User.class));
    }

    @Test
    public void testGetUserById() {
        when(userService.getUserById(user1.getId())).thenReturn(user1);
        UserDTO result = userFacade.getUserById(user1.getId());

        verify(userService).getUserById(user1.getId());
        assertEquals(user1, beanMappingService.mapTo(result, User.class));
    }

    @Test
    public void testGetAllUsers() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));
        List<UserDTO> result = userFacade.getAllUsers();
        verify(userService).getAllUsers();

        List<User> users = beanMappingService.mapTo(result, User.class);
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
}
