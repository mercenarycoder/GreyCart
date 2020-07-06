package com.greycart.greycart;

public class Notification_baseclass
{
    String message,date,time,id;

    public Notification_baseclass(String message, String date, String time, String id) {
        this.message = message;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }
}
