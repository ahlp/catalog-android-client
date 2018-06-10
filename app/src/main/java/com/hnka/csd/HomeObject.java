package com.hnka.csd;

public class HomeObject {
    private String tName;
    private double amount;

    public HomeObject(String tName, double amount) {
        this.tName = tName;
        this.amount = amount;
    }

    public String gettName() {
        return tName;
    }

    public double getAmount() {
        return amount;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
