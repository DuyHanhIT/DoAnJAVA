package com.example.shopsneaker.model;

public class OrderDetails {
    public OrderDetails() {

    }

    public Integer getOrderId() {
        return orderid;
    }

    public void setOrderId(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public OrderDetails(Integer orderid, Integer id, Integer quantity, String size, Integer price, Integer total, String name, String images, int statusid) {
        this.orderid = orderid;
        this.id = id;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
        this.total = total;
        this.name = name;
        this.images = images;
        this.statusid = statusid;
    }

    public Integer orderid;
    public Integer id;
    public Integer quantity;
    public String size;
    public Integer price;
    public Integer total;
    public String name;
    public String images;

    public int getStatusid() {
        return statusid;
    }

    public void setStatusid(int statusid) {
        this.statusid = statusid;
    }

    public int statusid;

}
