package com.example.shopsneaker.model;

public class searchhistory
{
    int accountid;
    String keyword;

    public searchhistory(int accountid, String keyword) {
        this.accountid = accountid;
        this.keyword = keyword;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
