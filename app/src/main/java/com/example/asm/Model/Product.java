package com.example.asm.Model;

public class Product {
    public int pro_id;
    public String product_id;
    public String product_name;
    public String product_price;
    public String product_image;
    public String product_describer;

    public Product(String product_name, String product_price, String product_image, String product_describer) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
        this.product_describer = product_describer;
    }
    public Product(String product_id, String product_name, String product_price, String product_image, String product_describer) {
        this.product_id=product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
        this.product_describer = product_describer;
    }
    public Product(int pro_id, String product_name, String product_price, String product_image, String product_describer) {
        this.pro_id=pro_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
        this.product_describer = product_describer;
    }

    public Product(){
    }

}
