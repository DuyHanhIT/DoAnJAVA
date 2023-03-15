package com.example.shopsneaker.model.EventBus;

public class AccountEvent {
    com.example.shopsneaker.model.User user;

    public AccountEvent(com.example.shopsneaker.model.User user) {
        this.user = user;
    }

    public com.example.shopsneaker.model.User getUser() {
        return user;
    }

    public void setUser(com.example.shopsneaker.model.User user) {
        this.user = user;
    }
}
