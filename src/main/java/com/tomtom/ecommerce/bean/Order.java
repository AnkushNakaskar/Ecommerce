package com.tomtom.ecommerce.bean;

import com.tomtom.ecommerce.constant.OrderStage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {

    private static final long SerialVersionUID = 1l;

    private Long id;

    private Long userId ;

    private List<Product> products;

    private Long cost;

    private OrderStage orderStage;

    private Date createTime;

    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OrderStage getOrderStage() {
        return orderStage;
    }

    public void setOrderStage(OrderStage orderStage) {
        this.orderStage = orderStage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
