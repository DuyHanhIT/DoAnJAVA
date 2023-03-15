package com.example.shopsneaker.model;

public class SaleDetails {

    Integer salesid;
    Integer id;
    Float salesprice;
    Integer updateby;
    String name;
    String images;
    Integer price;

    public SaleDetails() {

    }

    public SaleDetails(String name, String images, Float salesprice, Integer price) {
        this.salesprice = salesprice;
        this.name = name;
        this.images = images;
        this.price = price;
    }

    public SaleDetails(Integer salesid, Integer id, Float salesprice, Integer updateby, String name, String images, Integer price) {
        this.salesid = salesid;
        this.id = id;
        this.salesprice = salesprice;
        this.updateby = updateby;
        this.name = name;
        this.images = images;
        this.price = price;
    }

    public Integer getSalesid() {
        return salesid;
    }

    public void setSalesid(Integer salesid) {
        this.salesid = salesid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getSalesprice() {
        return salesprice;
    }

    public void setSalesprice(Float salesprice) {
        this.salesprice = salesprice;
    }

    public Integer getUpdateby() {
        return updateby;
    }

    public void setUpdateby(Integer updateby) {
        this.updateby = updateby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
