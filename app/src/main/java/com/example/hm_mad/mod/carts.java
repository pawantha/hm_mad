package com.example.hm_mad.mod;

public class carts {
    private String pid,name,Quantiyt,price,date,time;
    public carts(){

    }

    public carts(String pid, String name, String quantiyt, String price, String date, String time) {
        this.pid = pid;
        this.name = name;
        Quantiyt = quantiyt;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantiyt() {
        return Quantiyt;
    }

    public void setQuantiyt(String quantiyt) {
        Quantiyt = quantiyt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
