package com.example.asm.Model;

public class Card {
    public String card_id;
    public String product_id;
    public String product_name;
    public String product_price;
    public String product_image	;
    public String product_describer;

    public Card(){

    }

    public Card(String product_id, String product_name, String product_price, String product_image, String product_describer) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
        this.product_describer = product_describer;
    }
}
