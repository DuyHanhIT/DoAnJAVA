package com.example.shopsneaker.model;

import java.util.List;

public class SizeManagmentModel {
    boolean success;
    String message;
    List<SizeManagment> result;

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

    public List<SizeManagment> getResult() {
        return result;
    }

    public void setResult(List<SizeManagment> result) {
        this.result = result;
    }
}
