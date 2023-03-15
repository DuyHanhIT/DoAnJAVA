package com.example.shopsneaker.model;

public class SearchHistoryModel {
    boolean success;
    String message;
    java.util.List<searchhistory> result;

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

    public java.util.List<searchhistory> getResult() {
        return result;
    }

    public void setResult(java.util.List<searchhistory> result) {
        this.result = result;
    }
}
