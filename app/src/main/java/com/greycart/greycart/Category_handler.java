package com.greycart.greycart;

public class Category_handler {
    String cat_name=null;
    String id=null;
    String status=null;
    String staff_id;

    public Category_handler(String cat_name,String id,String status, String staff_id) {
        this.cat_name = cat_name;
        this.id=id;
        this.status=status;
        this.staff_id = staff_id;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }
}
