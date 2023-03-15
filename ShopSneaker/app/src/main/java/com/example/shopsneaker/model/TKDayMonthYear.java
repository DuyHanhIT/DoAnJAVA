package com.example.shopsneaker.model;

public class TKDayMonthYear {
    public TKDayMonthYear(String day, int orderday, Double totalday, String month, int ordermonth, Double totalmonth, String year, int orderyear, Double totalyear, int orderdtt, Double totaldtt, int orderctt, Double totalctt, int orderdh, Double totaldh) {
        this.day = day;
        this.orderday = orderday;
        this.totalday = totalday;
        this.month = month;
        this.ordermonth = ordermonth;
        this.totalmonth = totalmonth;
        this.year = year;
        this.orderyear = orderyear;
        this.totalyear = totalyear;
        this.orderdtt = orderdtt;
        this.totaldtt = totaldtt;
        this.orderctt = orderctt;
        this.totalctt = totalctt;
        this.orderdh = orderdh;
        this.totaldh = totaldh;
    }

    private String day;
    private int orderday;
    private Double totalday;
    private String month;
    private int ordermonth;
    private Double totalmonth;
    private String year;
    private int orderyear;
    private Double totalyear;
    private int orderdtt;
    private Double totaldtt;
    private int orderctt;
    private Double totalctt;
    private int orderdh;
    private Double totaldh;

    public TKDayMonthYear() {
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getOrderday() {
        return orderday;
    }

    public void setOrderday(int orderday) {
        this.orderday = orderday;
    }

    public Double getTotalday() {
        return totalday;
    }

    public void setTotalday(Double totalday) {
        this.totalday = totalday;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getOrdermonth() {
        return ordermonth;
    }

    public void setOrdermonth(int ordermonth) {
        this.ordermonth = ordermonth;
    }

    public Double getTotalmonth() {
        return totalmonth;
    }

    public void setTotalmonth(Double totalmonth) {
        this.totalmonth = totalmonth;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getOrderyear() {
        return orderyear;
    }

    public void setOrderyear(int orderyear) {
        this.orderyear = orderyear;
    }

    public Double getTotalyear() {
        return totalyear;
    }

    public void setTotalyear(Double totalyear) {
        this.totalyear = totalyear;
    }

    public int getOrderdtt() {
        return orderdtt;
    }

    public void setOrderdtt(int orderdtt) {
        this.orderdtt = orderdtt;
    }

    public Double getTotaldtt() {
        return totaldtt;
    }

    public void setTotaldtt(Double totaldtt) {
        this.totaldtt = totaldtt;
    }

    public int getOrderctt() {
        return orderctt;
    }

    public void setOrderctt(int orderctt) {
        this.orderctt = orderctt;
    }

    public Double getTotalctt() {
        return totalctt;
    }

    public void setTotalctt(Double totalctt) {
        this.totalctt = totalctt;
    }

    public int getOrderdh() {
        return orderdh;
    }

    public void setOrderdh(int orderdh) {
        this.orderdh = orderdh;
    }

    public Double getTotaldh() {
        return totaldh;
    }

    public void setTotaldh(Double totaldh) {
        this.totaldh = totaldh;
    }
}
