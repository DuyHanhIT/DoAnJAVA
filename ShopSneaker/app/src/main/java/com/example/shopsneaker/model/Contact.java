package com.example.shopsneaker.model;

public class Contact {
    public Contact(String namecontact, String desCrip, int images) {
        Namecontact = namecontact;
        DesCrip = desCrip;
        Images = images;
    }

    public String getNamecontact() {
        return Namecontact;
    }

    public void setNamecontact(String namecontact) {
        Namecontact = namecontact;
    }

    public String getDesCrip() {
        return DesCrip;
    }

    public void setDesCrip(String desCrip) {
        DesCrip = desCrip;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }

    private String Namecontact;
    private String DesCrip;
    private int Images;


}
