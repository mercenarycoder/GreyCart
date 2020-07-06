package com.greycart.greycart;

public class order_view_flager
{
    String number;
    String name_product;
    String category_name;
    String discountVal,discount;
    String base_price,price;
    String product_point;

    public order_view_flager(String number, String name_product, String category_name,
                             String discountVal, String discount, String base_price,
                             String price, String product_point) {
        this.number = number;
        this.name_product = name_product;
        this.category_name = category_name;
        this.discountVal = discountVal;
        this.discount = discount;
        this.base_price = base_price;
        this.price = price;
        this.product_point = product_point;
    }

    public String getNumber() {
        return number;
    }

    public String getName_product() {
        return name_product;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getDiscountVal() {
        return discountVal;
    }

    public String getDiscount() {
        return discount;
    }

    public String getBase_price() {
        return base_price;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_point() {
        return product_point;
    }
}
