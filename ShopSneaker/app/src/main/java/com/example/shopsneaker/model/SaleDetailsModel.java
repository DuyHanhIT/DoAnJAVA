package com.example.shopsneaker.model;

import java.util.List;

public class SaleDetailsModel {
    boolean success;
    String message;
    List<SaleDetails> result;

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

    public List<SaleDetails> getResult() {
        return result;
    }

    public void setResult(List<SaleDetails> result) {
        this.result = result;
    }
}
