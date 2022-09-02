package com.example.mytpaymentsystem.Agent.Model;

import android.widget.ImageView;

public class PayBillModel {

    int imageView;
    String text1,text2;

    public PayBillModel(int imageView, String text1, String text2) {
        this.imageView = imageView;
        this.text1 = text1;
        this.text2 = text2;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
