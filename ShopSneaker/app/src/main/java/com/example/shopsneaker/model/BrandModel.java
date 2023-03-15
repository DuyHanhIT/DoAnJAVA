package com.example.shopsneaker.model;

public class BrandModel {
    boolean success;
    String message;
    java.util.List<Brand> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public java.util.List<Brand> getResult() {
        return result;
    }

    public void setResult(java.util.List<Brand> result) {
        this.result = result;
    }
}
