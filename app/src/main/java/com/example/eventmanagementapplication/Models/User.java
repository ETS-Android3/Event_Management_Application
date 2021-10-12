package com.example.eventmanagementapplication.Models;

public class User {
    private String CustomerID,Fname,Lname,Email,Address,Mobile,Password,gender,dob,picture;

    public User() {
    }

    public User(String CustomerID,String fname, String lname, String email, String address, String mobile, String password, String gender, String dob, String picture) {
        this.CustomerID = CustomerID;
        Fname = fname;
        Lname = lname;
        Email = email;
        Address = address;
        Mobile = mobile;
        Password = password;
        this.gender = gender;
        this.dob = dob;
        this.picture = picture;
    }

    public User(String CustomerID, String fname, String lname, String email, String address, String mobile, String gender, String dob, String picture) {
        this.CustomerID = CustomerID;
        Fname = fname;
        Lname = lname;
        Email = email;
        Address = address;
        Mobile = mobile;
        this.gender = gender;
        this.dob = dob;
        this.picture = picture;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
