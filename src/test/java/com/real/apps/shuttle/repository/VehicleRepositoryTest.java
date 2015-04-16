package com.real.apps.shuttle.repository;

import com.real.apps.shuttle.domain.model.Vehicle;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zorodzayi on 14/11/01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfig.class})
public class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository repository;
    @Autowired
    private MongoOperations operations;

    @After
    public void cleanUp(){
        operations.dropCollection("vehicle");
    }
    @Test
    public void shouldSaveVehicleIntoMongo(){
        String make = "Test Vehicle Make To Be Inserted";
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(make);
        Vehicle saved = repository.save(vehicle);
        assertThat(saved.getId(),notNullValue());
        Query query = new Query(Criteria.where("make").is(make));
        Vehicle found = operations.findOne(query,Vehicle.class);
        assertThat(found.getMake(),notNullValue());
        operations.remove(query,Vehicle.class);
    }
    @Test
    public void shouldFindNNumberOfVehicle(){
        int skip = 0;
        int limit = 2;
        Vehicle vehicle = new Vehicle();
        operations.save(vehicle);
        operations.save(vehicle);
        Page<Vehicle> page = repository.findAll(new PageRequest(skip,limit));
        assertThat(page.getSize(),is(limit));
        operations.dropCollection("vehicle");
    }

    @Test
    public void shouldOnlyFindTheVehiclesWithTheGivenCompanyId(){
        ObjectId id = ObjectId.get();
        ObjectId control = ObjectId.get();

        Vehicle vehicleToBeFound = new Vehicle();
        vehicleToBeFound.setCompanyId(id);
        vehicleToBeFound.setLicenseNumber("Test License Number To Be Found");

        Vehicle vehicleToBeFound2 = new Vehicle();
        vehicleToBeFound2.setLicenseNumber("Test License Number To Be Found 2");
        vehicleToBeFound2.setCompanyId(id);

        Vehicle vehicleNotToBeFound = new Vehicle();
        vehicleNotToBeFound.setLicenseNumber("Test License Number Not To Be Found");
        vehicleNotToBeFound.setCompanyId(control);

        Vehicle controlVehicle = new Vehicle();


        assertNotNull(repository.save(vehicleNotToBeFound).getId());
        assertNotNull(repository.save(vehicleToBeFound).getId());
        assertNotNull(repository.save(vehicleToBeFound2).getId());
        assertNotNull(repository.save(controlVehicle).getId());

        Page<Vehicle> actual = repository.findByCompanyId(id,new PageRequest(0,10));
        assertThat(actual.getTotalElements(),is(2l));
        assertThat(actual.getContent().get(0).getCompanyId(),is(id));
        assertThat(actual.getContent().get(1).getCompanyId(),is(id));
    }
}
