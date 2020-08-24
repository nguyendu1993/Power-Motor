package com.example.asm.Model;

public class Bill {
    public int bill_id;
    public String listproduct;
    public String createdate;
    public String nameuser;
    public String totalprice;


    public Bill(String listproduct, String createdate, String nameuser) {
        this.listproduct = listproduct;
        this.createdate = createdate;
        this.nameuser = nameuser;
    }

    public Bill(int bill_id, String createdate, String nameuser) {
        this.bill_id = bill_id;
        this.createdate = createdate;
        this.nameuser = nameuser;
    }

    public Bill(int bill_id, String listproduct, String createdate, String nameuser, String totalprice) {
        this.bill_id = bill_id;
        this.listproduct = listproduct;
        this.createdate = createdate;
        this.nameuser = nameuser;
        this.totalprice = totalprice;
    }

    public Bill(){

    }
}
