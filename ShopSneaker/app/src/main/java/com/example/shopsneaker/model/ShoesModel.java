package com.example.shopsneaker.model;

import java.util.List;

public class ShoesModel {
    boolean success;
    String message;
    List<Shoes> result;
    
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

    public List<Shoes> getResult() {
        return result;
    }

    public void setResult(List<Shoes> result) {
        this.result = result;
    }
}
