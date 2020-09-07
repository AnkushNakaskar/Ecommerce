package com.tomtom.ecommerce.dao;

import com.tomtom.ecommerce.bean.Order;
import com.tomtom.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) Long userId){
        try {
            List<Order> response =null;
            if(userId!=null){
                response =orderService.getOrderForUser(userId);
            }else{
                response =orderService.getAllOrder();
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            System.out.println("Exception in placing the order for userId : "+userId+ " is : "+e);
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
