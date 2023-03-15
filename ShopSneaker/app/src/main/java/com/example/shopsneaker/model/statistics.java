package com.example.shopsneaker.model;

import java.util.Date;

public class statistics {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public statistics(int id, String name, int month, int year, int sellnumber, int turnover, int count, int day, Double total) {
        this.id = id;
        this.name = name;
        this.month = month;
        this.year = year;
        this.sellnumber = sellnumber;
        this.turnover = turnover;
        this.count = count;
        Day = day;
        this.total = total;
    }

    //    private int revenueId;
    private Integer id;
    String name;
    private int month;
    private int year;
    private int sellnumber;
    private int turnover;
    private int count;
    private int Day;
    private Double total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSellnumber() {
        return sellnumber;
    }

    public void setSellnumber(int sellnumber) {
        this.sellnumber = sellnumber;
    }

    public int getTurnover() {
        return turnover;
    }

    public void setTurnover(int turnover) {
        this.turnover = turnover;
    }

}
