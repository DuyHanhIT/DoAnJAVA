package com.example.shopsneaker.model;

public class Admin {
    private String ManagerName;
    private String ManagerImages;

    public Admin(String managerName, String managerImages) {
        ManagerName = managerName;
        ManagerImages = managerImages;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }

    public String getManagerImages() {
        return ManagerImages;
    }

    public void setManagerImages(String managerImages) {
        ManagerImages = managerImages;
    }
}
