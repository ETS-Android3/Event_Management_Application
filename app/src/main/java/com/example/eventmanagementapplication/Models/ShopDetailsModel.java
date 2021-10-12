package com.example.eventmanagementapplication.Models;

public class ShopDetailsModel {
    String signup_shopname,signup_shopaddress,signup_shopphoneNo,signup_opentime,signup_closetime;
    String email;

    public ShopDetailsModel(String email) {
        this.email = email;
    }

    public ShopDetailsModel(String signup_shopname, String signup_shopaddress, String signup_shopphoneNo, String signup_opentime, String signup_closetime) {
        this.signup_shopname = signup_shopname;
        this.signup_shopaddress = signup_shopaddress;
        this.signup_shopphoneNo = signup_shopphoneNo;
        this.signup_opentime = signup_opentime;
        this.signup_closetime = signup_closetime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignup_shopname() {
        return signup_shopname;
    }

    public void setSignup_shopname(String signup_shopname) {
        this.signup_shopname = signup_shopname;
    }

    public String getSignup_shopaddress() {
        return signup_shopaddress;
    }

    public void setSignup_shopaddress(String signup_shopaddress) {
        this.signup_shopaddress = signup_shopaddress;
    }

    public String getSignup_shopphoneNo() {
        return signup_shopphoneNo;
    }

    public void setSignup_shopphoneNo(String signup_shopphoneNo) {
        this.signup_shopphoneNo = signup_shopphoneNo;
    }

    public String getSignup_opentime() {
        return signup_opentime;
    }

    public void setSignup_opentime(String signup_opentime) {
        this.signup_opentime = signup_opentime;
    }

    public String getSignup_closetime() {
        return signup_closetime;
    }

    public void setSignup_closetime(String signup_closetime) {
        this.signup_closetime = signup_closetime;
    }
}
