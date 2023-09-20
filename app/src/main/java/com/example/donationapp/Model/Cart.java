package com.example.donationapp.Model;

public class Cart {
    private String pid,pname,quantity;

    public Cart(String pid, String pname, String quantity) {
        this.pid = pid;
        this.pname = pname;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

