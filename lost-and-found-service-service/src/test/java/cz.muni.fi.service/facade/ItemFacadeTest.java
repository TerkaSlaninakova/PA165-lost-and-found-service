package cz.muni.fi.service.facade;

import cz.muni.fi.dto.*;
import cz.muni.fi.facade.ItemFacade;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.persistence.enums.Status;
import cz.muni.fi.service.BeanMappingService;
import cz.muni.fi.service.BeanMappingServiceImpl;
import cz.muni.fi.service.ItemService;
import cz.muni.fi.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for itemFacade
 * @author Jakub Polacek
 */

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ItemFacadeTest extends AbstractTestNGSpringContextTests {


    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemFacade itemFacade = new ItemFacadeImpl();

    @Spy
    @Autowired
    private BeanMappingService beanMappingService = new BeanMappingServiceImpl();

    private User testUser;
    private UserDTO testUserDTO;

    private Location testLocation;
    private LocationDTO testLocationDTO;
    
    private Item phone, computer;
    private ItemDTO phoneDTO;
    
    private ItemCreateDTO createPencilDTO;
    private ItemChangeImageDTO changeComputerImageDTO;
    
    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        phone = new Item();
        phone.setId(1L);
        phone.setName("phone");
        phone.setType("phone type");
        phone.setCharacteristics("can make calls");
        phone.setStatus(Status.CLAIM_RECEIVED_LOST);

        phoneDTO = beanMappingService.mapTo(phone, ItemDTO.class);

        computer = new Item();
        computer.setId(2L);
        computer.setName("computer");
        computer.setType("computer type");
        computer.setCharacteristics("can compute");
        computer.setStatus(Status.CLAIM_RECEIVED_FOUND);

        createPencilDTO = new ItemCreateDTO();
        createPencilDTO.setName("pencil");
        createPencilDTO.setType("writing instrument");
        createPencilDTO.setCharacteristics("very smooth");

        changeComputerImageDTO = new ItemChangeImageDTO();
        changeComputerImageDTO.setItemId(2L);
        changeComputerImageDTO.setImage(new byte[5]);
        changeComputerImageDTO.setImageMimeType(".pdf");

        testLocation = new Location();
        testLocation.setDescription("In Slovakia");
        testLocation.setId(1L);

        testLocationDTO = beanMappingService.mapTo(testLocation, LocationDTO.class);

        testUser = new User();
        testUser.setName("John");
        testUser.setId(10L);
        testUser.setEmail("john@gmail.com");
        testUser.setPassword("123");
        testUser.setIsAdmin(false);

        testUserDTO = beanMappingService.mapTo(testUser, UserDTO.class);

    }

    @Test
    public void testAddItem() {
        itemFacade.addItemFound(createPencilDTO);
        verify(itemService).addItem(any(Item.class));


        itemFacade.addItemLost(createPencilDTO);
        verify(itemService, VerificationModeFactory.atLeastOnce()).addItem(any(Item.class));
    }


    @Test
    public void testUpdateItem() {
        itemFacade.updateItem(phoneDTO);
        verify(itemService, VerificationModeFactory.atLeastOnce()).updateItem(any(Item.class));
    }

    @Test
    public void testDeleteItem() {
        itemFacade.deleteItem(phoneDTO);
        verify(itemService).deleteItem(any(Item.class));
    }

    @Test
    public void testGetItemById() {
        when(itemService.getItemById(1L)).thenReturn(phone);
        ItemDTO result = itemFacade.getItemById(1L);

        verify(itemService).getItemById(1L);
        assertEquals(phone, beanMappingService.mapTo(result, Item.class));
    }

    @Test
    public void testGetAllItems() {
        when(itemService.getAllItems()).thenReturn(Arrays.asList(phone, computer));
        List<ItemDTO> result = itemFacade.getAllItems();
        verify(itemService).getAllItems();

        List<Item> items = beanMappingService.mapTo(result, Item.class);
        assertEquals(2, items.size());
        assertTrue(items.contains(phone));
        assertTrue(items.contains(computer));
    }

    @Test
    public void testAddCategoryToItem() {
        itemFacade.addCategoryToItem(1L, 1L);
        verify(itemService).addCategoryToItem(1L, 1L);
    }
    @Test
    public void testRemoveCategoryToItem() {
        itemFacade.removeCategoryFromItem(1L, 1L);
        verify(itemService).removeCategoryFromItem(1L, 1L);
    }
    @Test
    public void testChangeOwner() {
        itemFacade.changeOwner(1L, 1L);
        verify(itemService).changeOwner(1L, 1L);
    }
    @Test
    public void testChangeLostLocation() {
        itemFacade.changeLostLocation(1L, 1L);
        verify(itemService).changeLostLocation(1L, 1L);
    }

    @Test
    public void testChangeFoundLocation() {
        itemFacade.changeFoundLocation(1L, 1L);
        verify(itemService).changeFoundLocation(1L, 1L);
    }
    @Test
    public void testResolveFoundItem() {
        itemFacade.resolveLostItem(phoneDTO, LocalDate.of(2018,1,1), testLocationDTO);
        verify(itemService).resolveLostItem(any(Item.class), any(LocalDate.class), any(Location.class));

    }
    @Test
    public void testResolveLostItem() {
        itemFacade.resolveFoundItem(phoneDTO, LocalDate.of(2018,1,1), testLocationDTO, testUserDTO);
        verify(itemService).resolveFoundItem(any(Item.class), any(LocalDate.class), any(Location.class), any(User.class));
    }
    @Test
    public void testArchiveItem() {
        itemFacade.archiveItem(phoneDTO);
        verify(itemService).archiveItem(any(Item.class));
    }
    @Test
    public void testChangeImageOfItem() {
        when(itemService.getItemById(2L)).thenReturn(computer);
        itemFacade.changeImage(changeComputerImageDTO);
        verify(itemService, VerificationModeFactory.atLeastOnce()).updateItem(any(Item.class));
    }

}
