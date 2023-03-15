package com.example.shopsneaker.model;

import java.util.List;

public class TKDayMonthYearModel {boolean success;
    String message;
    List<TKDayMonthYear> result;

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

    public List<TKDayMonthYear> getResult() {
        return result;
    }

    public void setResult(List<TKDayMonthYear> result) {
        this.result = result;
    }

}
