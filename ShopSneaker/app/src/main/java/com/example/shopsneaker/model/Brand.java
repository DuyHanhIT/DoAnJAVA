package com.example.shopsneaker.model;

public class Brand implements java.io.Serializable {

    int brandid;
    String brandname;
    String information;
    String logo;
    int count;
    int countspdaban;
    int turnover;

    public Brand() {
    }

    public Brand(int brandid, String brandname) {
        this.brandid = brandid;
        this.brandname = brandname;
    }

    public Brand(int brandid, String brandname, String information, String logo) {
        this.brandid = brandid;
        this.brandname = brandname;
        this.information = information;
        this.logo = logo;
    }

    public Brand(int brandid, String brandname, String information, String logo, int count) {
        this.brandid = brandid;
        this.brandname = brandname;
        this.information = information;
        this.logo = logo;
        this.count = count;
    }

    public Brand(int brandid, String brandname, String information, String logo, int count, int countspdaban, int turnover) {
        this.brandid = brandid;
        this.brandname = brandname;
        this.information = information;
        this.logo = logo;
        this.count = count;
        this.countspdaban = countspdaban;
        this.turnover = turnover;
    }

    public int getCountspdaban() {
        return countspdaban;
    }

    public void setCountspdaban(int countspdaban) {
        this.countspdaban = countspdaban;
    }

    public int getTurnover() {
        return turnover;
    }

    public void setTurnover(int turnover) {
        this.turnover = turnover;
    }

    public int getBrandid() {
        return brandid;
    }

    public void setBrandid(int brandid) {
        this.brandid = brandid;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
