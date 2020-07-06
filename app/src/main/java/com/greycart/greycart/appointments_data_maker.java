package com.greycart.greycart;

public class appointments_data_maker {
    String payment_mode=null;
    String appointment_id=null;
    String s_id=null;
    String book_time=null;
    String cus_name=null;
    String cus_mobile=null;
    String book_date=null;
    String app_date=null;
    String app_date_mon=null;
    String pay_status=null;
    String order_no=null;
    String grand_total=null;
    String day=null;
    String time=null;

    public String getApp_date_mon() {
        return app_date_mon;
    }

    public appointments_data_maker(String payment_mode, String appointment_id, String s_id,
                                   String book_time, String cus_name, String cus_mobile, String book_date,
                                   String app_date, String app_date_mon, String pay_status,
                                   String order_no, String grand_total, String day, String time) {
        this.payment_mode = payment_mode;
        this.appointment_id = appointment_id;
        this.s_id = s_id;
        this.book_time = book_time;
        this.cus_name = cus_name;
        this.cus_mobile = cus_mobile;
        this.book_date = book_date;
        this.app_date = app_date;
        this.app_date_mon=app_date_mon;
        this.pay_status = pay_status;
        this.order_no = order_no;
        this.grand_total = grand_total;
        this.day = day;
        this.time = time;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public String getS_id() {
        return s_id;
    }

    public String getBook_time() {
        return book_time;
    }

    public String getCus_name() {
        return cus_name;
    }

    public String getCus_mobile() {
        return cus_mobile;
    }

    public String getBook_date() {
        return book_date;
    }

    public String getApp_date() {
        return app_date;
    }

    public String getPay_status() {
        return pay_status;
    }

    public String getOrder_no() {
        return order_no;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
