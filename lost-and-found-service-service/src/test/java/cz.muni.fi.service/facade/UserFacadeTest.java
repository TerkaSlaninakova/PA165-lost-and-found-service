package cz.muni.fi.service.facade;

import cz.muni.fi.dto.UserDTO;
import cz.muni.fi.facade.UserFacade;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.BeanMappingService;
import cz.muni.fi.service.BeanMappingServiceImpl;
import cz.muni.fi.service.UserService;
import cz.muni.fi.service.config.ServiceConfiguration;
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
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 *
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

    private User user1;


    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        user1 = new User();
        user1.setName("John");

    }

    @Test
    public void testAddUser() {
//
//        doAnswer(invocationOnMock -> {
//            User user = (User) invocationOnMock.getArguments()[0];
//            user.setId(64L);
//            return null;
//        }).when(userService).addUser(user1);

        UserDTO userDTO = beanMappingService.mapTo(user1, UserDTO.class);

        userFacade.createUser(userDTO);

        verify(userService).addUser(any(User.class));
    }

    //need to be finished
}
