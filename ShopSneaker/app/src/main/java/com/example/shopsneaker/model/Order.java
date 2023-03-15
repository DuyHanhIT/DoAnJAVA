package com.example.shopsneaker.model;
import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    public Integer orderid;
    public Date bookingdate;
    public Date deliverydate;
    public Integer statusid;
    public String name;
    public String address;
    public String phone;
    public String email;
    public String note;
    public Integer total;
    public Boolean payment;
    public Integer accountid;
    public String statusname;
    public Integer id;
    public Integer quantity;
    public String size;

    public Order() {
    }

    public Order(Integer orderid) {
        this.orderid = orderid;
    }

    public Order(Integer orderid, Date bookingdate, Date deliverydate, Integer statusid, String name, String address, String phone, String email, String note, Integer total, Boolean payment, Integer accountid, String statusname) {
        this.orderid = orderid;
        this.bookingdate = bookingdate;
        this.deliverydate = deliverydate;
        this.statusid = statusid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.total = total;
        this.payment = payment;
        this.accountid = accountid;
        this.statusname = statusname;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Date getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(Date bookingdate) {
        this.bookingdate = bookingdate;
    }

    public Date getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(Date deliverydate) {
        this.deliverydate = deliverydate;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean getPayment() {
        return payment;
    }

    public void setPayment(Boolean payment) {
        this.payment = payment;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }
}
