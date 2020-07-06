package com.greycart.greycart;

public class Offer_item_base {
    String offer_id,offer_code,offer_name,offer_date,offer_mon,offer_uses,offer_status;

    public Offer_item_base(String offer_id, String offer_code,
                           String offer_name, String offer_date,String offer_mon,
                           String offer_uses, String offer_status) {
        this.offer_id = offer_id;
        this.offer_code = offer_code;
        this.offer_name = offer_name;
        this.offer_date = offer_date;
        this.offer_mon  = offer_mon;
        this.offer_uses = offer_uses;
        this.offer_status = offer_status;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public String getOffer_code() {
        return offer_code;
    }

    public String getOffer_name() {
        return offer_name;
    }

    public String getOffer_date() {
        return offer_date;
    }

    public String getOffer_uses() {
        return offer_uses;
    }

    public String getOffer_status() {
        return offer_status;
    }
}
