package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.Cart;
import com.tomtom.ecommerce.bean.Order;
import com.tomtom.ecommerce.constant.OrderStage;
import com.tomtom.ecommerce.dao.CartRepository;
import com.tomtom.ecommerce.entity.CartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {


    @Autowired
    private OrderService orderService;

    @Autowired
    private CartRepository cartRepository;


    public Cart getCartDetails(Long userId){
        CartEntity entity = cartRepository.findFirstByUserIdOrderByCreateTimeDesc(userId);
        Cart cart =new Cart();
        cart.setUserId(userId);
        if(entity!=null){
            Order order =orderService.getOrder(entity.getOrderId(), OrderStage.IN_CART);
            cart.setOrder(order);
        }
        return cart;
    }

    public void addProduct(Long userId, Long productId, Integer count){
        CartEntity entity = cartRepository.findFirstByUserIdOrderByCreateTimeDesc(userId);
        if(entity==null || !orderService.isCartOrder(entity.getOrderId())){
            Long orderId =orderService.createNewOrder(userId,productId,count);
            entity =new CartEntity();
            entity.setOrderId(orderId);
            entity.setUserId(userId);

        }else{
            orderService.addProduct( productId,entity.getOrderId());
        }
        cartRepository.save(entity);
    }

    @Transactional
    public void removeProduct(Long userId,Long productId){
        CartEntity entity = cartRepository.findFirstByUserIdOrderByCreateTimeDesc(userId);
        if(entity!=null &&  orderService.isCartOrder(entity.getOrderId())){
            orderService.removeProduct(productId, entity.getOrderId());
        }
    }

    public void placeOrder(Long userId){
        CartEntity entity = cartRepository.findFirstByUserIdOrderByCreateTimeDesc(userId);
        if(entity!=null &&  orderService.isCartOrder(entity.getOrderId())) {
            orderService.placeOrder(entity.getOrderId());
        }
    }

}
