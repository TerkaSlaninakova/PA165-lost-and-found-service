package cz.muni.fi.service.facade;

import cz.muni.fi.api.dto.UserDTO;
import cz.muni.fi.api.facade.UserFacade;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.BeanMappingService;
import cz.muni.fi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Implementation of UserFacade
 *
 * @author Augustin Nemec
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void addUser(UserDTO userDTO) {
        userService.addUser(beanMappingService.mapTo(userDTO, User.class));
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        userService.updateUser(beanMappingService.mapTo(userDTO, User.class));
    }

    @Override
    public void deleteUser(UserDTO userDTO) {
        userService.deleteUser(beanMappingService.mapTo(userDTO, User.class));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return beanMappingService.mapTo(userService.getUserById(id), UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return beanMappingService.mapTo(userService.getUsersByEmail(email), UserDTO.class);
    }

    @Override
    public boolean authenticate(UserDTO u, String password) {
        User user = beanMappingService.mapTo(u, User.class);
        return userService.authenticate(user, password);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.getAllUsers(), UserDTO.class);
    }
}
