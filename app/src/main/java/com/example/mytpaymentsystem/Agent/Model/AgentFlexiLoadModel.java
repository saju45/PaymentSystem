package com.example.mytpaymentsystem.Agent.Model;

public class AgentFlexiLoadModel {

    String date,time,number;
    double amount;

    public AgentFlexiLoadModel() {
    }


    public AgentFlexiLoadModel(String date, String time, String number, double amount) {
        this.date = date;
        this.time = time;
        this.number = number;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

