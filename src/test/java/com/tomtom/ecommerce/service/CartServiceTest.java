package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.Cart;
import com.tomtom.ecommerce.bean.Order;
import com.tomtom.ecommerce.bean.Product;
import com.tomtom.ecommerce.constant.OrderStage;
import com.tomtom.ecommerce.dao.CartRepository;
import com.tomtom.ecommerce.entity.CartEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

@RunWith(SpringJUnit4ClassRunner.class)
public class CartServiceTest {

    private static final Long USER_ID = 1L;
    private static final Long ORDER_ID =11L ;
    private static final Long PRODUCT_ID = 111L;
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderService orderService;

    @Test
    public void testGetCartWhenNoProductInCart(){

        Cart cart = cartService.getCartDetails(USER_ID);
        Assert.assertNull(cart.getOrder());
        Assert.assertEquals(USER_ID,cart.getUserId());
    }

    @Test
    public void testGetCartWhenProductInCart(){
        Mockito.when(cartRepository.findFirstByUserIdOrderByCreateTimeDesc(USER_ID)).thenReturn(getDummyCartEntity());
        Mockito.when(orderService.getOrder(ORDER_ID, OrderStage.IN_CART)).thenReturn(getDummyOrder());
        Cart cart = cartService.getCartDetails(USER_ID);
        Assert.assertNotNull(cart.getOrder());
        Assert.assertEquals(USER_ID,cart.getUserId());
        Assert.assertEquals(ORDER_ID,cart.getOrder().getId());
    }

    @Test
    public void testAddFirstProduct(){
        Mockito.when(orderService.createNewOrder(USER_ID,PRODUCT_ID, 1)).thenReturn(ORDER_ID);
        cartService.addProduct(USER_ID,PRODUCT_ID, 1);
        Mockito.verify(cartRepository,Mockito.times(1)).save(Mockito.any(CartEntity.class));
        Mockito.verify(orderService,Mockito.times(0)).addProduct(PRODUCT_ID,ORDER_ID);
    }

    @Test
    public void testAddProduct(){
        Mockito.when(cartRepository.findFirstByUserIdOrderByCreateTimeDesc(USER_ID)).thenReturn(getDummyCartEntity());
        Mockito.when(orderService.isCartOrder(ORDER_ID)).thenReturn(true);
        cartService.addProduct(USER_ID,PRODUCT_ID, 1);
        Mockito.verify(cartRepository,Mockito.times(1)).save(Mockito.any(CartEntity.class));
        Mockito.verify(orderService,Mockito.times(1)).addProduct(PRODUCT_ID,ORDER_ID);
        Mockito.verify(orderService,Mockito.times(0)).createNewOrder(USER_ID,ORDER_ID, 1);

    }

    @Test
    public void testRemoveProductIFProductIsNotPresent(){
        cartService.removeProduct(USER_ID,PRODUCT_ID, 1);
        Mockito.verify(orderService,Mockito.times(0)).removeProduct(PRODUCT_ID,ORDER_ID, 1);
    }

    @Test
    public void testRemoveProductIFProductIsPresent(){
        Mockito.when(cartRepository.findFirstByUserIdOrderByCreateTimeDesc(USER_ID)).thenReturn(getDummyCartEntity());
        Mockito.when(orderService.isCartOrder(ORDER_ID)).thenReturn(true);
        cartService.removeProduct(USER_ID,PRODUCT_ID, 1);
        Mockito.verify(orderService,Mockito.times(1)).removeProduct(PRODUCT_ID,ORDER_ID, 1);
    }

    @Test
    public void testPlacedOrderWhenCartOrder(){
        Mockito.when(cartRepository.findFirstByUserIdOrderByCreateTimeDesc(USER_ID)).thenReturn(getDummyCartEntity());
        Mockito.when(orderService.isCartOrder(ORDER_ID)).thenReturn(true);
        cartService.placeOrder(USER_ID);
        Mockito.verify(orderService,Mockito.times(1)).placeOrder(ORDER_ID, USER_ID);
    }


    @Test
    public void testPlacedOrderWhenNotCartOrder(){
        cartService.placeOrder(USER_ID);
        Mockito.verify(orderService,Mockito.times(0)).placeOrder(ORDER_ID, USER_ID);
    }




    private Order getDummyOrder(){
        Order order =new Order();
        order.setCost(20L);
        order.setProducts(Arrays.asList(getDummyProductBean()));
        order.setId(ORDER_ID);
        return order;
    }

    private Product getDummyProductBean() {
        Product entity = new Product();
        entity.setId(PRODUCT_ID);
        entity.setName("Test");
        entity.setAvailable(true);
        entity.setPrice(20L);
        return entity;
    }

    private CartEntity getDummyCartEntity() {
        CartEntity cartEntity =new CartEntity();
        cartEntity.setUserId(USER_ID);
        cartEntity.setOrderId(ORDER_ID);
        cartEntity.setId(1111111L);
        return cartEntity;
    }

}
