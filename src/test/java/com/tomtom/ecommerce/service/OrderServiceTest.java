package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.Order;
import com.tomtom.ecommerce.bean.Product;
import com.tomtom.ecommerce.constant.OrderStage;
import com.tomtom.ecommerce.dao.OrderProductMappingRepository;
import com.tomtom.ecommerce.dao.OrderRepository;
import com.tomtom.ecommerce.entity.OrderEntity;
import com.tomtom.ecommerce.entity.OrderProductMappingEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class OrderServiceTest {

    private static final Long PRODUCT_ID = 1L;
    private static final Long USER_ID = 111L;
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductMappingRepository productMappingRepository;


    @Mock
    private ProductService productService;

    private static final Long ORDER_ID = 123L;



    @Test
    public void testAddProductWhenProductIsAvailable(){
        Mockito.when(productService.isProductAvailable(PRODUCT_ID)).thenReturn(true);
        Mockito.when(productMappingRepository.findByOrderId(ORDER_ID)).thenReturn(null);
        orderService.addProduct(PRODUCT_ID,ORDER_ID);
        Mockito.verify(productMappingRepository,Mockito.times(1)).saveAll(Mockito.anyCollection());
    }


    @Test
    public void testAddProductWhenProductIsNotAvailable(){
        Mockito.when(productService.isProductAvailable(PRODUCT_ID)).thenReturn(false);
        Mockito.when(productMappingRepository.findByOrderId(ORDER_ID)).thenReturn(null);
        orderService.addProduct(PRODUCT_ID,ORDER_ID);
        Mockito.verify(productMappingRepository,Mockito.times(0)).saveAll(Mockito.anyCollection());
    }

    @Test
    public void testRemoveProduct(){
        orderService.removeProduct(PRODUCT_ID,ORDER_ID);
        Mockito.verify(productMappingRepository,Mockito.times(1)).deleteMappingOrderIdAndProductId(ORDER_ID,PRODUCT_ID);
    }

    @Test
    public void testGetOrderINCART_OrderNotPresent(){
        Order order = orderService.getOrder(ORDER_ID, OrderStage.IN_CART);
        Assert.assertNull(order);
    }

    @Test
    public void testGetOrderINCART_OrderPresentNoProductPresent(){
        OrderEntity orderEntity = getDummyOrderEntity();
        Mockito.when(orderRepository.findFirstByIdAndStage(ORDER_ID,OrderStage.IN_CART)).thenReturn(orderEntity);
        Order order = orderService.getOrder(ORDER_ID, OrderStage.IN_CART);
        Assert.assertNull(order);
    }

    @Test
    public void testGetOrderINCART_OrderPresentProductPresent(){
        OrderEntity orderEntity = getDummyOrderEntity();
        Mockito.when(orderRepository.findFirstByIdAndStage(ORDER_ID,OrderStage.IN_CART)).thenReturn(orderEntity);

        Mockito.when(productMappingRepository.findByOrderId(ORDER_ID)).thenReturn(Arrays.asList(getDummyProductOrderMappingEntity()));
        Mockito.when(productService.getProducts(Arrays.asList(PRODUCT_ID))).thenReturn(Arrays.asList(getDummyProductBean()));
        Order order = orderService.getOrder(ORDER_ID, OrderStage.IN_CART);
        Assert.assertNotNull(order);
        Assert.assertEquals(1,order.getProducts().size());
        Assert.assertEquals(PRODUCT_ID,order.getProducts().get(0).getId());
    }

    @Test
    public void testIsCartOrder(){
        boolean isCartOrder =orderService.isCartOrder(ORDER_ID);
        Assert.assertFalse(isCartOrder);
    }

    @Test
    public void testCreateNewOrder(){
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(getDummyOrderEntity());
        orderService.createNewOrder(USER_ID,PRODUCT_ID);
        Mockito.verify(productMappingRepository,Mockito.times(1)).save(Mockito.any(OrderProductMappingEntity.class));
    }

    @Test
    public void testPlaceOrder(){
        OrderEntity orderEntity =getDummyOrderEntity();
        Mockito.when(orderRepository.findById(ORDER_ID)).thenReturn(Optional.of(orderEntity));
        Mockito.when(productMappingRepository.findByOrderId(ORDER_ID)).thenReturn(Arrays.asList(getDummyProductOrderMappingEntity()));
        orderService.placeOrder(ORDER_ID);
        Mockito.verify(productService,Mockito.times(1)).markUnavailable(Mockito.anyList());
        Mockito.verify(orderRepository,Mockito.times(1)).save(Mockito.any(OrderEntity.class));
    }

    private OrderEntity getDummyOrderEntity(){
        OrderEntity entity =new OrderEntity();
        entity.setStage(OrderStage.IN_CART);
        entity.setUserId(123L);
        entity.setId(ORDER_ID);
        return entity;
    }

    private OrderProductMappingEntity getDummyProductOrderMappingEntity() {
        OrderProductMappingEntity entity = new OrderProductMappingEntity();
        entity.setId(1L);
        entity.setProductId(PRODUCT_ID);
        entity.setOrderId(ORDER_ID);
        entity.setId(20L);
        return entity;
    }

    private Product getDummyProductBean() {
        Product entity = new Product();
        entity.setId(PRODUCT_ID);
        entity.setName("Test");
        entity.setAvailable(true);
        entity.setPrice(20L);
        return entity;
    }


}
