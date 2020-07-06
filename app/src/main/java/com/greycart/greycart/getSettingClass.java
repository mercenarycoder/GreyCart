package com.greycart.greycart;

public class getSettingClass
{
    String cmp_id;
    String cmp_name;
    String city_name;
    String address;
    String tandc;
    String contact_no;
    String logo_url;
    String bussniess_status;
    String bussniess_category;
    String service_at;

    public getSettingClass(String cmp_id, String cmp_name, String city_name, String address, String tandc,
                           String contact_no,
                           String logo_url, String bussniess_status,
                           String bussniess_category, String service_at) {
        this.cmp_id = cmp_id;
        this.cmp_name = cmp_name;
        this.city_name = city_name;
        this.address = address;
        this.tandc = tandc;
        this.contact_no = contact_no;
        this.logo_url = logo_url;
        this.bussniess_status = bussniess_status;
        this.bussniess_category = bussniess_category;
        this.service_at = service_at;
    }

    public String getCmp_id() {
        return cmp_id;
    }

    public void setCmp_id(String cmp_id) {
        this.cmp_id = cmp_id;
    }

    public String getCmp_name() {
        return cmp_name;
    }

    public void setCmp_name(String cmp_name) {
        this.cmp_name = cmp_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTandc() {
        return tandc;
    }

    public void setTandc(String tandc) {
        this.tandc = tandc;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getBussniess_status() {
        return bussniess_status;
    }

    public void setBussniess_status(String bussniess_status) {
        this.bussniess_status = bussniess_status;
    }

    public String getBussniess_category() {
        return bussniess_category;
    }

    public void setBussniess_category(String bussniess_category) {
        this.bussniess_category = bussniess_category;
    }

    public String getService_at() {
        return service_at;
    }

    public void setService_at(String service_at) {
        this.service_at = service_at;
    }
}
