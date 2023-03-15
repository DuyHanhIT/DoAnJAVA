package com.example.shopsneaker.model;

import com.google.gson.annotations.SerializedName;

public class Images {
    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Images(int imgid, int id, String image) {
        this.imgid = imgid;
        this.id = id;
        this.image = image;
    }

    private int imgid;
    private int id;
    private String image;
}
