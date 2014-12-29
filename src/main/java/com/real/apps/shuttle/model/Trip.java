package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by zorodzayi on 14/10/10.
 */
@Document
public class Trip extends CompanyModel {
    private ObjectId id;
    private String source;
    @Indexed
    private String clientName;
    @Indexed
    private String destination;
    @Indexed
    private ObjectId clientId;
    private int pricePerKm;
    private int price;
    private String vehicleName;
    private String driverName;
    private ObjectId vehicleId;
    private ObjectId driverId;
    private int distance;
    @Indexed
    private String clientCellNumber;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    @Override
    public String toString() {

        return String.format("{source:%s,id:%s,clientName:%s,destination:%s,pricePerKm:%d,price:%d,vehicleName:%s,vehicleId:%s,driverName:%s,driverId:%s,distance:%d," +
                        "clientCellNumber:%s,clientId:%s}", source, id, clientName, destination,pricePerKm, price, vehicleName,vehicleId,driverName,driverId,distance,clientCellNumber,clientId);
    }
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(id == null){
            return false;
        }
        if(!( object instanceof Trip)){
            return false;
        }
        Trip trip = (Trip)object;
        return id.equals(trip.getId());
    }
}
