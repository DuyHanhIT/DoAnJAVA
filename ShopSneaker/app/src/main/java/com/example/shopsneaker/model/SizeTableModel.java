package com.example.shopsneaker.model;

import java.util.List;

public class SizeTableModel {
    boolean success;
    String message;
    List<SizeTable> result;

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

    public List<SizeTable> getResult() {
        return result;
    }

    public void setResult(List<SizeTable> result) {
        this.result = result;
    }
}
