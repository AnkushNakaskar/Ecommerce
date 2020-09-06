package com.tomtom.ecommerce.controller;

import com.tomtom.ecommerce.bean.Cart;
import com.tomtom.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId}/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCartDetails(@PathVariable Long userId) {
        try{
            Cart cart =cartService.getCartDetails(userId);
            return  new ResponseEntity<>(cart,HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Exception in getting card details :: "+ e);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/addProduct/{productId}/{count}")
    public ResponseEntity<String> addProduct(@PathVariable Long userId, @PathVariable Long productId, @PathVariable Integer count){
        try {
            cartService.addProduct(userId, productId,count);
            return new ResponseEntity<>("Success ..!!!",HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Exception in adding the product in cart for user : "+userId + " exception :: "+e);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/removeProduct/{productId}/{count}")
    public ResponseEntity<String> removeProduct(@PathVariable Long userId, @PathVariable Long productId, @PathVariable Integer count){
        try {
            cartService.removeProduct(userId, productId,count);
            return new ResponseEntity<>("Success ..!!!",HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Exception in removing the product in cart for user : "+userId + " exception :: "+e);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@PathVariable Long userId){
        try {
            cartService.placeOrder(userId);
            return new ResponseEntity<>("Success..!!!",HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Exception in placing the order for userId : "+userId+ " is : "+e);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
