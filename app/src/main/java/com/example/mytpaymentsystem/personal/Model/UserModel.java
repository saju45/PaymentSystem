package com.example.mytpaymentsystem.personal.Model;

public class UserModel {

    String name;
    String phone;
    String type;
    String uid;
    Double balance;

    public UserModel(String name, String phone, String type, String uid, Double balance) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.uid = uid;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
