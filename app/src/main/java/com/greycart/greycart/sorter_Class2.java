package com.greycart.greycart;

public class sorter_Class2
{
    String  name,id,bar_code,img_link,status,rate,old_mrp, tax;

    public sorter_Class2(String name, String id, String bar_code, String img_link,
                         String status, String rate, String old_mrp, String tax) {
        this.name = name;
        this.id = id;
        this.bar_code = bar_code;
        this.img_link = img_link;
        this.status = status;
        this.rate = rate;
        this.old_mrp = old_mrp;
        this.tax = tax;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getBar_code() {
        return bar_code;
    }

    public String getImg_link() {
        return img_link;
    }

    public String getStatus() {
        return status;
    }

    public String getRate() {
        return rate;
    }

    public String getOld_mrp() {
        return old_mrp;
    }

    public String getTax() {
        return tax;
    }
}
