package com.example.mytpaymentsystem.personal.Model;

public class CashInModel {

    String number;
    double amount;


    public CashInModel() {
    }

    public CashInModel(String number, double amount) {
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
