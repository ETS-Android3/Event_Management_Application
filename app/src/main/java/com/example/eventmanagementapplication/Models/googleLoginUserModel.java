package com.example.eventmanagementapplication.Models;

public class googleLoginUserModel {
    String customerID,fname,lname,email,picture;

    public googleLoginUserModel(String customerID, String fname, String lname, String email, String picture) {
        this.customerID = customerID;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.picture = picture;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
