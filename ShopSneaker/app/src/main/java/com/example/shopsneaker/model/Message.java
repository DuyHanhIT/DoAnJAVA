package com.example.shopsneaker.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    public Message(int mesid, int sender, int receiver, String content, String name, Date timestamp) {
        this.mesid = mesid;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.name = name;
        this.timestamp = timestamp;
    }

    int mesid, sender, receiver;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;
    String name;
    Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMesid() {
        return mesid;
    }

    public void setMesid(int mesid) {
        this.mesid = mesid;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }


}
