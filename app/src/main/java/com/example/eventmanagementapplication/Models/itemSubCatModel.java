package com.example.eventmanagementapplication.Models;

public class itemSubCatModel {
    private String available_service_name,decora_image,money_start,money_end;

    public itemSubCatModel(String decora_image, String available_service_name, String money_start, String money_end) {
        this.decora_image = decora_image;
        this.available_service_name = available_service_name;
        this.money_start = money_start;
        this.money_end = money_end;
    }

    public String getDecora_image() {
        return decora_image;
    }

    public void setDecora_image(String decora_image) {
        this.decora_image = decora_image;
    }

    public String getMoney_start() {
        return money_start;
    }

    public void setMoney_start(String money_start) {
        this.money_start = money_start;
    }

    public String getMoney_end() {
        return money_end;
    }

    public void setMoney_end(String money_end) {
        this.money_end = money_end;
    }

    public String getAvailable_service_name() {
        return available_service_name;
    }

    public void setAvailable_service_name(String available_service_name) {
        this.available_service_name = available_service_name;
    }


}
