package com.example.shopsneaker.model;

import java.util.List;

public class GioHang {
    public GioHang() {

    }
    public GioHang(Integer id, String name, Integer price, String images, Integer purchased) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;
        this.purchased = purchased;
    }
    public GioHang(Integer id, String name, Integer price, String images, String size,Integer purchased) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;
        this.size = size;
        this.purchased = purchased;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShoeName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    public Integer getPurchased() {
        return purchased;
    }
    public List<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    public void setPurchased(Integer purchased) {
        this.purchased = purchased;
    }

    public Integer id;
    public String name;
    public Integer price;
    public String images;
    public String size;
    public Integer purchased;
    public List<Images> imagesList;

    public String getName() {
        return name;
    }

}
