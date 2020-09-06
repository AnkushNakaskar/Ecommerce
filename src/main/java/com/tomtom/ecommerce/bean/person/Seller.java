package com.tomtom.ecommerce.bean.person;

import java.io.Serializable;


public class Seller extends Person implements Serializable {

    private static final long SerialVersionUID = 1l;


    @Override
    public PersonCategoryType getPersonCategory() {
        return PersonCategoryType.SELLER;
    }
}
