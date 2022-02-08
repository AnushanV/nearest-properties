package com.anushan.nearestproperties;

import com.opencsv.bean.CsvBindByName;

//This class represents houses found in csv file
public class House {

    @CsvBindByName(column = "ID")
    private long id;
    @CsvBindByName(column = "unit_address_as_provided")
    private String unitAddress;
    @CsvBindByName(column = "ADDRESS")
    private String address;
    @CsvBindByName(column = "state")
    private String state;
    @CsvBindByName(column = "city")
    private String city;
    @CsvBindByName(column = "zip_code")
    private int zipCode;
    @CsvBindByName(column = "rent")
    private int rent;

    public long getId() {
        return id;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public String getAddress() {
        return address;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public int getRent() {
        return rent;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", unitAddress='" + unitAddress + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipCode=" + zipCode +
                ", rent=" + rent +
                '}';
    }
}
