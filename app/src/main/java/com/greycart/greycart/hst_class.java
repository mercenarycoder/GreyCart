package com.greycart.greycart;

public class hst_class {
    String hsn_id,hsn_name,hsn_rate,hsn_description;

    public hst_class(String hsn_id, String hsn_name,String hsn_rate,String hsn_description) {
        this.hsn_id = hsn_id;
        this.hsn_name = hsn_name;
        this.hsn_rate=hsn_rate;
        this.hsn_description=hsn_description;
    }

    public String getHsn_rate() {
        return hsn_rate;
    }

    public String getHsn_description() {
        return hsn_description;
    }

    public String getHsn_id() {
        return hsn_id;
    }

    public String getHsn_name() {
        return hsn_name;
    }
}
