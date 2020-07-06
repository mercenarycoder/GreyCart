package com.greycart.greycart;
//this adapter is common for every recyclerview of this project soo just keep this thing in mind

public class dashboard_Adapter {
    String clientName;
    String time;
    String date_num;
    String date_mon;
    public dashboard_Adapter(String clientName,String time,String date_num,String date_mon)
    {
        this.clientName=clientName;
        this.time=time;
        this.date_num=date_num;
        this.date_mon=date_mon;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate_num() {
        return date_num;
    }

    public void setDate_num(String date_num) {
        this.date_num = date_num;
    }

    public String getDate_mon() {
        return date_mon;
    }

    public void setDate_mon(String date_mon) {
        this.date_mon = date_mon;
    }
}
