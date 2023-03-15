package com.example.shopsneaker.model.EventBus;

import com.example.shopsneaker.model.Shoes;

public class SuaXoaEvent {
    Shoes shoes;

    public SuaXoaEvent(Shoes shoes) {
        this.shoes = shoes;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }
}
