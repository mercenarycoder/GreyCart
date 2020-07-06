package com.greycart.greycart;

public class medicineBaseClass2 {
String medicine;
String id;
    public medicineBaseClass2(String id,String medicine) {
        this.medicine = medicine;
        this.id=id;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getId() {
        return id;
    }
}
