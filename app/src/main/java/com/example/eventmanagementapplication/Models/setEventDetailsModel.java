package com.example.eventmanagementapplication.Models;

public class setEventDetailsModel {
    String eventDetailsID,addDecorationID,CustomerID,vendorEmail,meetingTime,meetingDate,providePrice,meetingDescription,isOrderConfirm,currentDate,currentTime;

    public setEventDetailsModel(String eventDetailsID, String addDecorationID,String CustomerID, String vendorEmail, String meetingTime, String meetingDate, String providePrice, String meetingDescription,String isOrderConfirm,String currentDate,String currentTime) {
        this.eventDetailsID = eventDetailsID;
        this.addDecorationID = addDecorationID;
        this.CustomerID = CustomerID;
        this.vendorEmail = vendorEmail;
        this.meetingTime = meetingTime;
        this.meetingDate = meetingDate;
        this.providePrice = providePrice;
        this.meetingDescription = meetingDescription;
        this.isOrderConfirm = isOrderConfirm;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
    }

    public String getEventDetailsID() {
        return eventDetailsID;
    }

    public void setEventDetailsID(String eventDetailsID) {
        this.eventDetailsID = eventDetailsID;
    }

    public String getAddDecorationID() {
        return addDecorationID;
    }

    public void setAddDecorationID(String addDecorationID) {
        this.addDecorationID = addDecorationID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getProvidePrice() {
        return providePrice;
    }

    public void setProvidePrice(String providePrice) {
        this.providePrice = providePrice;
    }

    public String getMeetingDescription() {
        return meetingDescription;
    }

    public void setMeetingDescription(String meetingDescription) {
        this.meetingDescription = meetingDescription;
    }

    public String getIsOrderConfirm() {
        return isOrderConfirm;
    }

    public void setIsOrderConfirm(String isOrderConfirm) {
        this.isOrderConfirm = isOrderConfirm;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
