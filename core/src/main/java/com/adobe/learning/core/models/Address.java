package com.adobe.learning.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Address {

    private int id_address;
    private String CEP;
    private String street;
    private int number;
    private String city;
    private String state;
    private String district;

    public Address(){

    }

    public Address(String CEP, String street, int number, String city, String state, String district) {
        this.CEP = CEP;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.district = district;
    }

    public Address(int id_address, String CEP, String street, int number, String city, String state, String district) {
        this.id_address = id_address;
        this.CEP = CEP;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.district = district;
    }

    public int getId_address() {
        return id_address;
    }

    public void setId_address(int id_address) {
        this.id_address = id_address;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return "ADDRESS \n" + "City: " + this.getCity() + "\n" + "State: " + this.getState()
                + "\n-----------------\n";
    }
}
