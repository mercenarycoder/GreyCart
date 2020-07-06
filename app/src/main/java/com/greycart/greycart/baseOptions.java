package com.greycart.greycart;

public class baseOptions {
    String description,place_id;

    public baseOptions(String description, String place_id) {
        this.description = description;
        this.place_id = place_id;

    }
    public String getDescription() {
        return description;
    }

    public String getPlace_id() {
        return place_id;
    }
}
