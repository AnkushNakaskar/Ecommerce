package com.tomtom.ecommerce.controller;

import com.tomtom.ecommerce.bean.Product;
import com.tomtom.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        try {
            List<Product> products =productService.getAllProducts();
            return new ResponseEntity<>(products,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Exception in fetching all the products .."+e);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId){
        try{
            Product product = productService.getProduct(productId);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Exception in fetching the product for id : "+productId + " IS : "+e);
        }
        return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
