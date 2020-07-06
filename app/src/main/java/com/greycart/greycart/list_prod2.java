package com.greycart.greycart;

public class list_prod2 {
    String name=null,name2;
    String id=null,id2;
    String bar_code=null,bar_code2;
    String img_link=null,img_link2;
    String status=null,status2;
    String cat_name=null,cat_name2;
    String rate=null,rate2;
    String old_mrp=null,old_mrp2;
    String tax=null,tax2;

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
    public list_prod2(String name, String id, String bar_code, String img_link,
                     String status,  String rate,String old_mrp,String tax,String name2, String id2,
                      String bar_code2, String img_link2,
                      String status2, String rate2,String old_mrp2,String tax2) {
        this.name = name;
        this.id = id;
        this.bar_code = bar_code;
        this.img_link = img_link;
        this.status = status;
      //  this.cat_name = cat_name;
        this.rate = rate;
        this.old_mrp=old_mrp;
        this.tax=tax;

        this.name2=name2;
        this.id2=id2;
        this.bar_code2=bar_code2;
        this.img_link2=img_link2;
        this.status2=status2;
    //    this.cat_name2=cat_name2;
        this.rate2=rate2;
        this.old_mrp2=old_mrp2;
        this.tax2=tax2;
    }

    public String getName2() {
        return name2;
    }

    public String getId2() {
        return id2;
    }

    public String getBar_code2() {
        return bar_code2;
    }

    public String getImg_link2() {
        return img_link2;
    }

    public String getStatus2() {
        return status2;
    }

    public String getRate2() {
        return rate2;
    }

    public String getOld_mrp2() {
        return old_mrp2;
    }

    public String getTax2() {
        return tax2;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
