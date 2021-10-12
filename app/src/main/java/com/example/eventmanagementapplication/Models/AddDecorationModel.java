package com.example.eventmanagementapplication.Models;

public class AddDecorationModel {
    String addDecorationID,vendorEmail;


    public AddDecorationModel() {
    }


    public AddDecorationModel(String addDecorationID, String vendorEmail) {
        this.addDecorationID = addDecorationID;
        this.vendorEmail = vendorEmail;
    }

    public String getAddDecorationID() {
        return addDecorationID;
    }

    public void setAddDecorationID(String addDecorationID) {
        this.addDecorationID = addDecorationID;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }
}
