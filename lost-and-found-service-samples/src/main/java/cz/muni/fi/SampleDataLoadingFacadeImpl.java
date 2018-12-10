package cz.muni.fi;

import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade{
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public void loadData() throws IOException {
        loadUsers();
    }

    private void loadUsers() {
        User user = new User();
        userService.addUser(user);
    }

}
