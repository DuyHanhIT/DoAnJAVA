package com.example.shopsneaker.model;

import java.util.List;

public class RatesModel {
    boolean success;
    String message;
    List<Rates> result;

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

    public List<Rates> getResult() {
        return result;
    }

    public void setResult(List<Rates> result) {
        this.result = result;
    }


}

