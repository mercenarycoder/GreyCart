package com.greycart.greycart;

public class sorter_Class {
    String name,id,img,status,count;

    public sorter_Class(String name, String id, String img, String status) {
        this.name = name;
        this.id = id;
        this.img = img;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getStatus() {
        return status;
    }

    public String getCount() {
        return count;
    }
}
