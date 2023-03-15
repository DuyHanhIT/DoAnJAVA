package com.example.shopsneaker.model;

import java.util.List;

public class SalesModel {
    boolean success;
    String message;
    List<Sales> result;

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

    public List<Sales> getResult() {
        return result;
    }

    public void setResult(List<Sales> result) {
        this.result = result;
    }
}
