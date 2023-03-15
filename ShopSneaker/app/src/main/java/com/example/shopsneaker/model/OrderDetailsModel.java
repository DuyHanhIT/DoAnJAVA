package com.example.shopsneaker.model;

import java.util.List;

public class OrderDetailsModel {
    boolean success;
    String message;
    List<OrderDetails> result;

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

    public List<OrderDetails> getResult() {
        return result;
    }

    public void setResult(List<OrderDetails> result) {
        this.result = result;
    }
}
