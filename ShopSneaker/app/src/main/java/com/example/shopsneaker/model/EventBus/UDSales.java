package com.example.shopsneaker.model.EventBus;

import com.example.shopsneaker.model.Sales;

public class UDSales {
    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    Sales sales;

    public UDSales(Sales sales) {
        this.sales = sales;
    }
}
