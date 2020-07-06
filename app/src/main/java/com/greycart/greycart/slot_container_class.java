package com.greycart.greycart;

public class slot_container_class {
    String day;
    String start_time;
    String end_time;
    String max_appointment;
    String status_get;
    String slot_id;
    String day_num;

    public slot_container_class(String day, String start_time,
                                String end_time, String max_appointment,
                                String status_get, String slot_id, String day_num) {
        this.day = day;
        this.start_time = start_time;
        this.end_time = end_time;
        this.max_appointment = max_appointment;
        this.status_get = status_get;
        this.slot_id = slot_id;
        this.day_num = day_num;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getMax_appointment() {
        return max_appointment;
    }

    public void setMax_appointment(String max_appointment) {
        this.max_appointment = max_appointment;
    }

    public String getStatus_get() {
        return status_get;
    }

    public void setStatus_get(String status_get) {
        this.status_get = status_get;
    }

    public String getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(String slot_id) {
        this.slot_id = slot_id;
    }

    public String getDay_num() {
        return day_num;
    }

    public void setDay_num(String day_num) {
        this.day_num = day_num;
    }
}
