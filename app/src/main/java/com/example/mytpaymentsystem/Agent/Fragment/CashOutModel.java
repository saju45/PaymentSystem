package com.example.mytpaymentsystem.Agent.Fragment;

public class CashOutModel {

    String number;
    double amount;

    public CashOutModel() {
    }


    public CashOutModel(String number, double amount) {
        this.number = number;
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
