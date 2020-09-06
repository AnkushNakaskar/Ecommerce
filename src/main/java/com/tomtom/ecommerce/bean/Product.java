package com.tomtom.ecommerce.bean;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class Product implements Serializable {

    private static final long SerialVersionUID = 1l;

    private Long id;

    @NotEmpty(message = "name of product should not be empty")
    private String name;

    @NotNull(message = "price should not be null")
    private Long price;

    @NotNull(message = "sellerId should not be null")
    private Long sellerId;

    private Boolean isAvailable;

    @NotNull(message = "count should not be null")
    private Integer count;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
