package com.tomtom.ecommerce.bean.person;

import java.io.Serializable;

public abstract class Person  implements Serializable {

    private static final long SerialVersionUID = 1l;


    private Long id;

    private String name;

    public abstract PersonCategoryType getPersonCategory();

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

    enum PersonCategoryType {
        USER,SELLER,ADMIN
    }

}
