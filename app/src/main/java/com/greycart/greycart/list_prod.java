package com.greycart.greycart;

public class list_prod {
    String name=null;
    String id=null;
    String bar_code=null;
    String img_link=null;
    String status=null;
    String cat_name=null;
    String rate=null;
    String old_mrp=null;
    String tax=null;

    public String getOld_mrp() {
        return old_mrp;
    }

    public String getTax() {
        return tax;
    }

    public String getStaff_id() {
        return staff_id;
    }

    String staff_id;
    public list_prod(String name, String id, String bar_code, String img_link,
                     String status, String cat_name, String rate,String old_mrp,String tax,String staff_id) {
        this.name = name;
        this.id = id;
        this.bar_code = bar_code;
        this.img_link = img_link;
        this.status = status;
        this.cat_name = cat_name;
        this.rate = rate;
        this.old_mrp=old_mrp;
        this.tax=tax;
        this.staff_id=staff_id;
    }

    public void setOld_mrp(String old_mrp) {
        this.old_mrp = old_mrp;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
