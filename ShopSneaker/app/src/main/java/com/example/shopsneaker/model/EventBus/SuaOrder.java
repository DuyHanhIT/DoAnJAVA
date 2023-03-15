package com.example.shopsneaker.model.EventBus;

import com.example.shopsneaker.model.Order;

public class SuaOrder {
    Order order;

    public SuaOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
