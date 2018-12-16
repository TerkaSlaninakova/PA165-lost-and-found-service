package cz.muni.fi.samples;

import cz.muni.fi.persistence.entity.Category;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.CategoryService;
import cz.muni.fi.service.ItemService;
import cz.muni.fi.service.LocationService;
import cz.muni.fi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade{
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private LocationService locationService;

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
        john.setEmail("johndoe@gmail.com");
        john.setPassword("123");
        john.setIsAdmin(false);
        userService.addUser(john);

        User admin = new User();
        admin.setName("Seth Adams");
        admin.setEmail("sethAdmin@gmail.com");
        admin.setPassword("admin");
        admin.setIsAdmin(true);
        userService.addUser(admin);
    }

    private void loadCategories() {
        Category electronics = new Category();
        electronics.setAttribute("silver");
        electronics.setAttribute("not water resistant");
        electronics.setName("electronics");
        categoryService.addCategory(electronics);

        Category clothes = new Category();
        clothes.setAttribute("cotton");
        clothes.setName("clothes");
        categoryService.addCategory(clothes);
    }

    private void loadLocations() {
        Location locationStation = new Location();
        locationStation.setDescription("At the reception of the swimming pool at Sportovní 486/4");
        locationService.addLocation(locationStation);

        Location locationClub = new Location();
        locationClub.setDescription("On the floor of a club at Dominikanska 5");
        locationService.addLocation(locationClub);

        Location locationTrainStation = new Location();
        locationTrainStation.setDescription("On the platform of the main train station");
        locationService.addLocation(locationTrainStation);

    }

    private void loadItems() {
        List<Location> locations = locationService.getAllLocations();
        List<Category> categories = categoryService.getAllCategories();
        Category electronics = categories.get(0);
        Category clothes = categories.get(1);
        Item phone = new Item();
        phone.setName("IPhone 7");
        phone.setType("phone");
        phone.setCategories(Arrays.asList(electronics));
        phone.setCharacteristics("128GB, 138 g, ED-backlit IPS LCD, 16M colors");
        phone.setLostLocation(locations.get(0));
        phone.setLostDate(LocalDate.now().minusMonths(1));
        phone.setStatus(Status.CLAIM_RECEIVED_LOST);
        phone.setOwner(userService.getAllUsers().get(0));
        itemService.addItem(phone);

        Item computer = new Item();
        computer.setName("Dell XPS 13");
        computer.setType("computer");
        computer.setCategories(Arrays.asList(electronics));
        computer.setCharacteristics("1.8GHz Intel Core i7-8550U,16GB DDR3");
        computer.setStatus(Status.CLAIM_RECEIVED_FOUND);
        computer.setFoundLocation(locations.get(1));
        computer.setFoundDate(LocalDate.now().minusDays(5));
        itemService.addItem(computer);

        Item sweater = new Item();
        sweater.setName("Woolen christmas sweater");
        sweater.setType("sweater");
        sweater.setCategories(Arrays.asList(clothes));
        sweater.setCharacteristics("Warm red sweater with christmas motive");
        sweater.setStatus(Status.RESOLVED);
        sweater.setLostLocation(locations.get(0));
        sweater.setLostDate(LocalDate.now().minusMonths(1));
        sweater.setFoundLocation(locations.get(1));
        sweater.setFoundDate(LocalDate.now());
        sweater.setOwner(userService.getAllUsers().get(0));
        itemService.addItem(sweater);
    }

}
