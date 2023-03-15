package com.example.shopsneaker.model;

public class Rates {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    int id;
    int accountid;
    float rate;

    public Rates(int id, int accountid, float rate) {
        this.id = id;
        this.accountid = accountid;
        this.rate = rate;
    }
}
