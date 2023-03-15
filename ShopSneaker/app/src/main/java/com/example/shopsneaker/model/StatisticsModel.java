package com.example.shopsneaker.model;

public class StatisticsModel {
    boolean success;
    String message;
    java.util.List<com.example.shopsneaker.model.statistics> result;

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

    public java.util.List<com.example.shopsneaker.model.statistics> getResult() {
        return result;
    }

    public void setResult(java.util.List<com.example.shopsneaker.model.statistics> result) {
        this.result = result;
    }
}
