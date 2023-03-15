package com.example.shopsneaker.model;

import java.io.Serializable;
import java.util.Date;

public class Shoes implements Serializable {

    public Shoes(Integer id, String name, java.util.Date dateUpdate, Integer price, String images, byte shoesnew, Integer stock, Integer purchased, Integer review, Float rate, Integer categoryId, String description, String updateBy, Integer brandId, int salesprice, java.util.Date startday, java.util.Date endday) {
        this.id = id;
        this.name = name;
        this.dateUpdate = dateUpdate;
        this.price = price;
        this.images = images;
        this.shoesnew = shoesnew;
        this.stock = stock;
        this.purchased = purchased;
        this.review = review;
        this.rate = rate;
        this.categoryId = categoryId;
        this.description = description;
        this.updateBy = updateBy;
        this.brandId = brandId;
        this.salesprice = salesprice;
        this.startday = startday;
        this.endday = endday;
    }

    public Integer id;
    public String name;
    public Date dateUpdate;
    public Integer price;
    public String images;
    public byte shoesnew;
    public Integer stock;
    public Integer purchased;
    public Integer review;
    public Float rate;
    public Integer categoryId;
    public String description;
    public String updateBy;
    public Integer brandId;
    public int salesprice;
    public Date startday,endday;
    Integer percent;

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public byte getShoesNew() {
        return shoesnew;
    }

    public void getShoesNew(byte shoesnew) {
        this.shoesnew = shoesnew;
    }

    public int getSaleprice() {
        return salesprice;
    }

    public void setSaleprice(int salesprice) {
        this.salesprice = salesprice;
    }

    public Date getStartday() {
        return startday;
    }

    public void setStartday(Date startday) {
        this.startday = startday;
    }

    public Date getEndday() {
        return endday;
    }

    public void setEndday(Date endday) {
        this.endday = endday;
    }



    public Integer getShoeId() {
        return id;
    }

    public void setShoeId(Integer id) {
        this.id = id;
    }

    public String getShoeName() {
        return name;
    }

    public void setShoeName(String name) {
        this.name = name;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
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



    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPurchased() {
        return purchased;
    }

    public void setPurchased(Integer purchased) {
        this.purchased = purchased;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Shoes() {

    }





}
