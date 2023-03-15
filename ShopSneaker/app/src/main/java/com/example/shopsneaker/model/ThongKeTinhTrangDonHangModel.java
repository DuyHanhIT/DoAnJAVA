package com.example.shopsneaker.model;

public class ThongKeTinhTrangDonHangModel {
    boolean success;
    String message;
    java.util.List<ThongKeTinhTrangDonHang> result;

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

    public java.util.List<ThongKeTinhTrangDonHang> getResult() {
        return result;
    }

    public void setResult(java.util.List<ThongKeTinhTrangDonHang> result) {
        this.result = result;
    }
}
