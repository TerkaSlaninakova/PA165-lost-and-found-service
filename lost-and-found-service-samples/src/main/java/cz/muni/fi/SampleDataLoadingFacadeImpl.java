package cz.muni.fi;

import cz.muni.fi.persistence.entity.*;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade{
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private UserService userService;
    private CategoryService categoryService;
    private ItemService itemService;
    private LocationService locationService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void loadData() throws IOException {
        loadUsers();
        log.debug("Loaded users");
        loadCategories();
        log.debug("Loaded categories");
        loadLocations();
        log.debug("Loaded locations");
        loadItems();
        log.debug("Loaded items");
    }

    private void loadUsers() {
        User john = new User();
        john.setName("John Doe");
        john.setId(1L);
        john.setEmail("johndoe@gmail.com");
        john.setPassword(encoder.encode("123"));
        john.setIsAdmin(false);
        userService.addUser(john);

        User admin = new User();
        admin.setName("Seth Adams");
        admin.setId(2L);
        admin.setEmail("sethAdmin@gmail.com");
        admin.setPassword(encoder.encode("admin"));
        admin.setIsAdmin(true);
        userService.addUser(admin);
    }

    private void loadCategories() {
        Category electronics = new Category();
        electronics.setId(1L);
        electronics.setAttribute("silver");
        electronics.setAttribute("not water resistant");
        electronics.setName("electronics");
        categoryService.addCategory(electronics);

        Category clothes = new Category();
        clothes.setId(2L);
        clothes.setAttribute("cotton");
        clothes.setName("clothes");
        categoryService.addCategory(clothes);
    }

    private void loadLocations() {
        Location locationStation = new Location();
        locationStation.setId(1L);
        locationStation.setDescription("At the reception of the swimming pool at Sportovní 486/4");
        locationService.addLocation(locationStation);

        Location locationClub = new Location();
        locationClub.setId(1L);
        locationClub.setDescription("On the floor of a club at Dominikanska 5");
        locationService.addLocation(locationClub);

        Location locationTrainStation = new Location();
        locationTrainStation.setId(1L);
        locationTrainStation.setDescription("On the platform of the main train station");
        locationService.addLocation(locationTrainStation);

    }

    private void loadItems() {
        List<Location> locations = locationService.getAllLocations();
        Category electronics = categoryService.getCategoryById(1L);
        Category clothes = categoryService.getCategoryById(2L);
        Item phone = new Item();
        phone.setId(1L);
        phone.setName("IPhone 7");
        phone.setType("phone");
        phone.setCategories(Arrays.asList(electronics));
        phone.setCharacteristics("128GB, 138 g, ED-backlit IPS LCD, 16M colors");
        phone.setLostLocation(locations.get(0));
        phone.setLostDate(LocalDate.now().minusMonths(1));
        phone.setStatus(Status.CLAIM_RECEIVED_LOST);
        phone.setOwner(userService.getUserById(1L));
        itemService.addItem(phone);

        Item computer = new Item();
        computer.setId(2L);
        computer.setName("Dell XPS 13");
        computer.setType("computer");
        computer.setCategories(Arrays.asList(electronics));
        computer.setCharacteristics("1.8GHz Intel Core i7-8550U,16GB DDR3");
        computer.setStatus(Status.CLAIM_RECEIVED_FOUND);
        computer.setFoundLocation(locations.get(1));
        computer.setFoundDate(LocalDate.now().minusDays(5));
        itemService.addItem(computer);

        Item sweater = new Item();
        sweater.setId(3L);
        sweater.setName("Woolen christmas sweater");
        sweater.setType("sweater");
        sweater.setCategories(Arrays.asList(clothes));
        sweater.setCharacteristics("Warm red sweater with christmas motive");
        sweater.setStatus(Status.RESOLVED);
        sweater.setLostLocation(locations.get(0));
        sweater.setLostDate(LocalDate.now().minusMonths(1));
        sweater.setFoundLocation(locations.get(1));
        sweater.setFoundDate(LocalDate.now());
        sweater.setOwner(userService.getUserById(1L));
        itemService.addItem(sweater);
    }

}
