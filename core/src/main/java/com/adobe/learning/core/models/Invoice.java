package com.adobe.learning.core.models;

import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private int number;
    private double value;
    private int idUser;

    public Invoice(){

    }

    public Invoice(int number) {
        this.number = number;
    }

    public Invoice(int number, int idUser, double value) {
        this.number = number;
        this.value = value;
        this.idUser = idUser;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "INVOICE \n" + "Number: " + this.getNumber() + "\n" + "Value: " + this.getValue()
                + "\n-----------------\n";
    }
}
