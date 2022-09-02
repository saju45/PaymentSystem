package com.example.mytpaymentsystem.Admin.Model;

public class Discount {

    Double discount;
    String name,date,time;

    public Discount() {
    }

    public Discount(Double discount, String name, String date, String time) {
        this.discount = discount;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
