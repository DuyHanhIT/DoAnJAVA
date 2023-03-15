package com.example.shopsneaker.model;

import java.util.List;

public class MarketModel {
    boolean success;
    String message;
    List<Market> result;

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

    public List<Market> getResult() {
        return result;
    }

    public void setResult(List<Market> result) {
        this.result = result;
    }
}
