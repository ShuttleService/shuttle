package com.real.apps.shuttle.controller;

import com.google.gson.Gson;
import com.real.apps.shuttle.config.MvcConfiguration;
import com.real.apps.shuttle.domain.model.Driver;
import com.real.apps.shuttle.domain.model.Trip;
import com.real.apps.shuttle.domain.model.User;
import com.real.apps.shuttle.domain.model.Vehicle;
import com.real.apps.shuttle.repository.DriverRepository;
import com.real.apps.shuttle.repository.RepositoryConfig;
import com.real.apps.shuttle.repository.VehicleRepository;
import com.real.apps.shuttle.service.ServiceConfig;
import com.real.apps.shuttle.service.TripService;
import com.real.apps.shuttle.valueObject.Currency;
import com.real.apps.shuttle.valueObject.Money;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.real.apps.shuttle.controller.UserDetailsUtils.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by zorodzayi on 14/10/05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class, ServiceConfig.class, RepositoryConfig.class})
@WebAppConfiguration
public class TripControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final String VIEW_PAGE = TripController.VIEW_NAME;
    @Autowired
    private TripController controller;
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    @Mock
    private TripService service;
    @Autowired
    private TripService tripService;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    private final int skip = 0, limit = 2;
    @Autowired
    private Filter springSecurityFilterChain;
    @Autowired
    private MongoOperations mongoTemplate;
    private Vehicle vehicle = new Vehicle();
    private Driver driver = new Driver();

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain).alwaysDo(print()).build();
    }

    @After
    public void cleanUp() {
        controller.setService(tripService);
        mongoTemplate.dropCollection("driver");
        mongoTemplate.dropCollection("vehicle");
    }

    @Test
    public void shouldRenderTripPage() throws Exception {
        mockMvc.perform(get("/" + VIEW_PAGE).with(user(world(ObjectId.get()))))
                .andExpect(view().name(VIEW_PAGE))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAPageOfAllTripsWhenAnAdminIsLoggedIn() throws Exception {
        final String source = "Test Source To List";
        Trip trip = new Trip();
        trip.setSource(source);
        List<Trip> trips = Arrays.asList(trip);
        final Page<Trip> page = new PageImpl<Trip>(trips);
        context.checking(new Expectations() {{
            oneOf(service).page(skip, limit);
            will(returnValue(page));
        }});

        controller.setService(service);

        mockMvc.perform(get("/" + VIEW_PAGE + "/" + skip + "/" + limit).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['content'][0]['source']").value(source));
    }

    @Test
    public void shouldCallPOSTOnControllerGivenABookedRangeWithISODates() throws Exception {
        String content = "{\"price\":{\"currency\":{\"currencyCode\":\"R\"},\"amount\":81},\"pricePerKm\":9,\"distance\":9,\"clientName\":" +
                "\"Zoro\",\"clientCellNumber\":845757575,\"source\":\"Home\",\"destination\":\"Work\",\"bookedRange\":{\"from\":\"2015-05-01T09:11:00.000Z\",\"to\":\"2015-05-02T09:11:00.000Z\"},\"companyId\":{\"_time\":1431357211,\"_machine" +
                "\":-56178087,\"_inc\":1189193287,\"_new\":false},\"companyName\":\"Real Shuttle\",\"driverId\":{\"_time\":1431360003," +
                "\"_machine\":-56182995,\"_inc\":-1874559372,\"_new\":false},\"driverName\":\"Violet Majoni\",\"vehicleName\":" +
                "\"Toyota Fortuner CK62NNGP\",\"vehicleId\":{\"_time\":1431357281,\"_machine\":-56178087,\"_inc\":1189193302,\"_new\":false}}";

        TripService service = Mockito.mock(TripService.class);
        controller.setService(service);

        mockMvc.perform(post(String.format("/%s", VIEW_PAGE)).
                with(user(admin(ObjectId.get()))).
                contentType(MediaType.APPLICATION_JSON).
                content(content)).
                andExpect(status().isOk());

    }

    @Test
    public void shouldSaveATripGivenDoublesValuesForDistanceAndMoney() throws Exception {
        BigDecimal distance = new BigDecimal(454.45);
        Money price = new Money(new Currency("R"), new BigDecimal(345.90));
        BigDecimal pricePerKm = new BigDecimal(12.4);
        Trip trip = new Trip();
        trip.setDistance(distance);
        trip.setPrice(price);
        trip.setPricePerKm(pricePerKm);

        String tripString = new Gson().toJson(trip);
        TripService service = Mockito.mock(TripService.class);

        controller.setService(service);
        mockMvc.perform(post(String.format("/%s", VIEW_PAGE)).
                contentType(MediaType.APPLICATION_JSON).
                with(user(admin(ObjectId.get()))).
                content(tripString)).
                andExpect(status().isOk());
    }

    @Test
    public void shouldSetTheTripClientIdAndNameToLoggedInUserCallServiceInsertAndReturnTheInsertedTripWhenWorldIsLoggedIn() throws Exception {
        String name = "Test Client Name To Insert";
        String surname = "Test Client Surname To Insert";

        ObjectId id = ObjectId.get();
        driverRepository.save(driver);
        vehicleRepository.save(vehicle);
        final Trip trip = new Trip();
        trip.setDriverId(driver.getId());
        trip.setVehicleId(vehicle.getId());
        User user = world(id);
        user.setFirstName(name);
        user.setId(id);
        user.setSurname(surname);

        String tripString = new Gson().toJson(trip);
        mockMvc.perform(post("/" + VIEW_PAGE).
                with(user(user)).
                contentType(MediaType.APPLICATION_JSON).
                content(tripString)).
                andExpect(jsonPath("$['clientId']['timestamp']").value(id.getTimestamp())).
                andExpect(jsonPath("$['clientName']").value(name + " " + surname));
        mongoTemplate.dropCollection("trip");
    }

    @Test
    public void shouldSetTheTripCompanyIdAndNameToTheLoggedInCompanyWhenACompanyUserIsLoggedIn() throws Exception {
        String companyName = "Test Company Name To Be Set On Trip";
        ObjectId id = ObjectId.get();
        User user = companyUser(id);
        user.setCompanyName(companyName);
        driverRepository.save(driver);
        vehicleRepository.save(vehicle);
        Trip trip = new Trip();
        trip.setDriverId(driver.getId());
        trip.setVehicleId(vehicle.getId());
        String jsonTrip = new Gson().toJson(trip);

        mockMvc.perform(post("/" + VIEW_PAGE).with(user(user)).contentType(MediaType.APPLICATION_JSON).content(jsonTrip)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['companyName']").value(companyName)).
                andExpect(jsonPath("$['companyId']['timestamp']").value(id.getTimestamp()));
        mongoTemplate.dropCollection("trip");
    }

    @Test
    public void shouldCallServiceInsertWhenAdminIsLoggedIn() throws Exception {
        final Trip trip = new Trip();
        String source = "Test Source To Be Inserted";
        trip.setSource(source);
        String json = new Gson().toJson(trip);

        context.checking(new Expectations() {{
            oneOf(service).insert(with(any(Trip.class)));
            will(returnValue(trip));
        }});

        controller.setService(service);

        mockMvc.perform(post("/" + VIEW_PAGE).with(user(admin(ObjectId.get()))).contentType(MediaType.APPLICATION_JSON).content(json)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['source']").value(source));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldSetTheTripClientIdAndNameToLoggedInUserCallServiceUpdateAndReturnTheInsertedTripWhenWorldIsLoggedIn() throws Exception {
        String name = "Test Client Name To Insert";
        String surname = "Test Client Surname To Insert";

        ObjectId id = ObjectId.get();

        final Trip trip = new Trip();
        User user = world(id);
        user.setFirstName(name);
        user.setId(id);
        user.setSurname(surname);

        String tripString = new Gson().toJson(trip);
        mockMvc.perform(put("/" + VIEW_PAGE).with(user(user)).contentType(MediaType.APPLICATION_JSON).content(tripString)).
                andExpect(jsonPath("$['clientId']['timestamp']").value(id.getTimestamp()))
                .andExpect(jsonPath("$['clientName']").value(name + " " + surname));
        mongoTemplate.dropCollection("trip");
    }

    @Test
    public void shouldSetTheTripCompanyIdAndNameToTheLoggedInCompanyAndCallUpdateWhenACompanyUserIsLoggedIn() throws Exception {
        String companyName = "Test Company Name To Be Set On Trip";
        ObjectId id = ObjectId.get();

        User user = companyUser(id);
        user.setCompanyName(companyName);

        Trip trip = new Trip();
        String jsonTrip = new Gson().toJson(trip);

        mockMvc.perform(put("/" + VIEW_PAGE).with(user(user)).contentType(MediaType.APPLICATION_JSON).content(jsonTrip)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['companyName']").value(companyName)).
                andExpect(jsonPath("$['companyId']['timestamp']").value(id.getTimestamp()));
        mongoTemplate.dropCollection("trip");
    }

    @Test
    public void shouldCallServiceUpdateWhenAdminIsLoggedIn() throws Exception {
        final Trip trip = new Trip();
        String source = "Test Source To Be Inserted";
        trip.setSource(source);
        String json = new Gson().toJson(trip);

        context.checking(new Expectations() {{
            oneOf(service).update(with(any(Trip.class)));
            will(returnValue(trip));
        }});

        controller.setService(service);

        mockMvc.perform(put("/" + VIEW_PAGE).with(user(admin(ObjectId.get()))).contentType(MediaType.APPLICATION_JSON).content(json)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['source']").value(source));
        context.assertIsSatisfied();
    }

    @Test
    public void shouldCallServiceDeleteAndReturnTheDeletedTripOnlyWhenAnAdminIsLoggedIn() throws Exception {
        String source = "Test Source To Delete";
        final Trip trip = new Trip();
        trip.setSource(source);
        String tripString = new Gson().toJson(trip);
        controller.setService(service);
        context.checking(new Expectations() {
            {
                oneOf(service).delete(with(any(Trip.class)));
                will(returnValue(trip));
            }
        });

        mockMvc.perform(delete("/" + VIEW_PAGE).with(user(admin(ObjectId.get()))).contentType(MediaType.APPLICATION_JSON).content(tripString)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['source']").value(source));
    }

    @Test
    public void shouldNotDeleteTripWhenNonAdminIsLogged() throws Exception {
        String source = "Test Source To Delete";
        final Trip trip = new Trip();
        trip.setSource(source);
        String tripString = new Gson().toJson(trip);
        controller.setService(service);

        context.checking(new Expectations() {
            {
                never(service).delete(with(any(Trip.class)));
                will(returnValue(trip));
            }
        });

        ObjectId id = ObjectId.get();

        mockMvc.perform(delete("/" + VIEW_PAGE).with(user(companyUser(id))).contentType(MediaType.APPLICATION_JSON).content(tripString)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.source").value(source));

        mockMvc.perform(delete("/" + VIEW_PAGE).with(user(world(id))).contentType(MediaType.APPLICATION_JSON).content(tripString)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['source']").value(source));

    }

    @Test
    public void shouldCallServiceFindOneAndReturnTheOneTrip() throws Exception {
        final ObjectId id = ObjectId.get();
        final Trip trip = new Trip();
        trip.setId(id);

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).findOne(id);
            will(returnValue(trip));
        }});

        mockMvc.perform(get("/" + VIEW_PAGE + "/one/" + id).with(user(admin(ObjectId.get())))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['id']['timestamp']").value(id.getTimestamp()));
    }

    @Test
    public void shouldReturnTripsOfTheCompanyWhenACompanyUserIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        final Trip trip = new Trip();
        trip.setCompanyId(id);
        final Page<Trip> page = new PageImpl<>(Arrays.asList(trip));

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).pageByCompanyId(id, skip, limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(companyUser(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['content'][0]['companyId']['timestamp']").value(id.getTimestamp()));

        context.assertIsSatisfied();
    }

    @Test
    public void shouldReturnTheLoggedInUserTripsWhenAWorldIsLoggedIn() throws Exception {
        final ObjectId id = ObjectId.get();
        final Trip trip = new Trip();
        trip.setClientId(id);
        final Page<Trip> page = new PageImpl<>(Arrays.asList(trip));

        controller.setService(service);

        context.checking(new Expectations() {{
            oneOf(service).pageByUserId(id, skip, limit);
            will(returnValue(page));
        }});

        mockMvc.perform(get(String.format("/%s/%d/%d", VIEW_PAGE, skip, limit)).with(user(world(id)))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$['content'][0]['clientId']['timestamp']").value(id.getTimestamp()));
        context.assertIsSatisfied();
    }

}