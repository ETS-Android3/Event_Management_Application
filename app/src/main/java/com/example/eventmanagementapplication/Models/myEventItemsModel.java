package com.example.eventmanagementapplication.Models;

public class myEventItemsModel {
    private String my_event_date;
    private String my_event_time,my_event_decoration_name,userID,isOrderConfirm,eventDetailsID,decoration_pic_image;

    public myEventItemsModel(String my_event_date, String my_event_time,String my_event_decoration_name,String userID,String isOrderConfirm,String eventDetailsID,String decoration_pic_image) {
        this.my_event_date = my_event_date;
        this.my_event_time = my_event_time;
        this.my_event_decoration_name = my_event_decoration_name;
        this.userID = userID;
        this.isOrderConfirm = isOrderConfirm;
        this.eventDetailsID = eventDetailsID;
        this.decoration_pic_image = decoration_pic_image;
    }

    public String getMy_event_date() {
        return my_event_date;
    }

    public void setMy_event_date(String my_event_date) {
        this.my_event_date = my_event_date;
    }

    public String getMy_event_time() {
        return my_event_time;
    }

    public void setMy_event_time(String my_event_time) {
        this.my_event_time = my_event_time;
    }

    public String getMy_event_decoration_name() {
        return my_event_decoration_name;
    }

    public void setMy_event_decoration_name(String my_event_decoration_name) {
        this.my_event_decoration_name = my_event_decoration_name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getIsOrderConfirm() {
        return isOrderConfirm;
    }

    public void setIsOrderConfirm(String isOrderConfirm) {
        this.isOrderConfirm = isOrderConfirm;
    }

    public String getEventDetailsID() {
        return eventDetailsID;
    }

    public void setEventDetailsID(String eventDetailsID) {
        this.eventDetailsID = eventDetailsID;
    }

    public String getDecoration_pic_image() {
        return decoration_pic_image;
    }

    public void setDecoration_pic_image(String decoration_pic_image) {
        this.decoration_pic_image = decoration_pic_image;
    }
}
