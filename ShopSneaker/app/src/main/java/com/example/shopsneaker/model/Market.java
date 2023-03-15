package com.example.shopsneaker.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Market implements Serializable {

    public Market() {

    }

    public Market(Integer id, String shoesname, String price, String size, String description, int accountid, int statusid, String name, String statusname, List<Images> images) {
        this.id = id;
        this.shoesname = shoesname;
        this.price = price;
        this.size = size;
        this.description = description;
        this.accountid = accountid;
        this.statusid = statusid;
        this.name = name;
        this.statusname = statusname;
        this.images = images;
    }

    public Integer id;
    public String shoesname;
    public String price;
    public String size;
    public String description;
    public int accountid;
    public int statusid;
    public String name;
    public String statusname;
    public List<Images> images;

    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getShoesname() {
        return shoesname;
    }

    public void setShoesname(String shoesname) {
        this.shoesname = shoesname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

}
