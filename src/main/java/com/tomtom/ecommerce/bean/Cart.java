package com.tomtom.ecommerce.bean;

import java.io.Serializable;

public class Cart implements Serializable {

    private static final long SerialVersionUID = 1l;

    private Long userId;

    private Order order;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
