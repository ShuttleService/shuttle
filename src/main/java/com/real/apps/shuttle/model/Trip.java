package com.real.apps.shuttle.model;

import org.bson.types.ObjectId;

/**
 * Created by zorodzayi on 14/10/10.
 */
public class Trip {
    private ObjectId id;
    private String source;
    private String clientName;
    private String destination;
    private int pricePerKm;
    private int price;
    private String vehicle;
    private String driver;
    private int distance;
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

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
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

    @Override
    public String toString() {

        return String.format("{source:%s,id:%s,clientName:%s,destination:%s,pricePerKm:%d,price:%d,vehicle:%s,driver:%s,distance:%d,clientCellNumber:%s}",source, id, clientName, destination,
                pricePerKm, price, vehicle, driver,distance,clientCellNumber);
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
