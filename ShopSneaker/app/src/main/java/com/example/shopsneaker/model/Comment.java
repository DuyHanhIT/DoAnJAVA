package com.example.shopsneaker.model;



public class Comment {
    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    int reviewid;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    int id;

    public Comment(int reviewid, int id, int accountid, String content, String name) {
        this.reviewid = reviewid;
        this.id = id;
        this.accountid = accountid;
        this.content = content;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int accountid;
    String content;
    String name;
}
