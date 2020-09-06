package com.tomtom.ecommerce.bean;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private static final long SerialVersionUID = 1l;

    private Long orderId;

    private List<Product> products;

    private Long cost;


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }
}
