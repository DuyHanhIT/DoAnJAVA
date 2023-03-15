package com.example.shopsneaker.model;

public class User {
    Integer accountid;
    String username;
    String password;
    int rolesid;
    String name;
    String address;
    String phone;
    byte enabled;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte getEnabled() {
        return enabled;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    //

    public User() {
    }

    public User(Integer accountid, String username, String password, int rolesid, String name, String address, String phone, byte enabled) {
        this.accountid = accountid;
        this.username = username;
        this.password = password;
        this.rolesid = rolesid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.enabled = enabled;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getRolesid() {
        return rolesid;
    }

    public void setRolesid(int rolesid) {
        this.rolesid = rolesid;
    }



}
