package com.real.apps.shuttle.domain.model;

import com.real.apps.shuttle.valueObject.Money;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zorodzayi on 14/10/10.
 */
@Document
public class Trip extends Proprietary {
    private String source;
    @Indexed
    private String clientName;
    @Indexed
    private String destination;
    @Indexed
    private ObjectId clientId;
    private int pricePerKm;
    private Money price;
    private String vehicleName;
    private String driverName;
    private ObjectId vehicleId;
    private ObjectId driverId;
    private int distance;
    @Indexed
    private String clientCellNumber;
    private BookedRange bookedRange;

    /*{"price":{"currency":{}},"clientName":"Sofi","clientCellNumber":988484848,"source":"Land","destination":"Earth",
    "bookedRange":{"from":"2015-05-01T12:22:00.000Z","to":"2015-05-16T09:11:00.000Z"},
    "companyId":{"_time":1431357211,"_machine":-56178087,"_inc":1189193287,"_new":false},"companyName":"Real Shuttle",
    "driverId":{"_time":1431360003,"_machine":-56182995,"_inc":-1874559372,"_new":false},"driverName":"Violet Majoni",
    "vehicleName":"Toyota Fortuner CK62NNGP","vehicleId":{"_time":1431357281,"_machine":-56178087,"_inc":1189193302,"_new":false}}*/
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(int pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getClientCellNumber() {
        return clientCellNumber;
    }

    public void setClientCellNumber(String clientCellNumber) {
        this.clientCellNumber = clientCellNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public ObjectId getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(ObjectId vehicleId) {
        this.vehicleId = vehicleId;
    }

    public ObjectId getDriverId() {
        return driverId;
    }

    public void setDriverId(ObjectId driverId) {
        this.driverId = driverId;
    }

    public ObjectId getClientId() {
        return clientId;
    }

    public void setClientId(ObjectId clientId) {
        this.clientId = clientId;
    }

    public BookedRange getBookedRange() {
        return bookedRange;
    }

    public void setBookedRange(BookedRange bookedRange) {
        this.bookedRange = bookedRange;
    }

    @Override
    public String toString() {
        return String.format("{source:%s,id:%s,clientName:%s,destination:%s,pricePerKm:%d,price:%s,vehicleName:%s,vehicleId:%s,driverName:%s," +
                        "driverId:%s,distance:%d,clientCellNumber:%s,clientId:%s,bookedRange:%s}", source, id, clientName, destination, pricePerKm,
                price, vehicleName, vehicleId, driverName, driverId, distance, clientCellNumber, clientId, bookedRange);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (id == null) {
            return false;
        }
        if (!(object instanceof Trip)) {
            return false;
        }
        Trip trip = (Trip) object;
        return id.equals(trip.getId());
    }
}
