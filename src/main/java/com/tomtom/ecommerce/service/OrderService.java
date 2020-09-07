package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.Order;
import com.tomtom.ecommerce.bean.Product;
import com.tomtom.ecommerce.constant.OrderStage;
import com.tomtom.ecommerce.dao.OrderProductMappingRepository;
import com.tomtom.ecommerce.dao.OrderRepository;
import com.tomtom.ecommerce.entity.OrderEntity;
import com.tomtom.ecommerce.entity.OrderProductMappingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductMappingRepository productMappingRepository;

    @Autowired
    private ProductService productService;

    public void addProduct(Long productId, Long orderId) {
        Boolean isProductAvailable = productService.isProductAvailable(productId);
        if (isProductAvailable) {
            List<OrderProductMappingEntity> mappingEntities = productMappingRepository.findByOrderId(orderId);
            if (mappingEntities ==null) {
                mappingEntities = new LinkedList<>();
            }
            OrderProductMappingEntity mappingEntity = new OrderProductMappingEntity();
            mappingEntity.setOrderId(orderId);
            mappingEntity.setProductId(productId);
            mappingEntities.add(mappingEntity);
            productMappingRepository.saveAll(mappingEntities);
        } else {
            System.out.println("Product is not available for adding :: " + productId);
        }
    }


    @Transactional
    public void removeProduct(Long productId, Long orderId, Integer count) {
        if(count==0){
            productMappingRepository.deleteMappingOrderIdAndProductId(orderId, productId);
        }else{
            productMappingRepository.updateMappingOrderIdAndProductIdWithCount(orderId, productId,count);
        }
    }


    public Order getOrder(Long orderId, OrderStage stage) {
        OrderEntity orderEntity = orderRepository.findFirstByIdAndStage(orderId, stage);
        if (orderEntity == null) {
            return null;
        }

        List<Product> orderProducts = getOrderProducts(orderId);
        if (orderProducts == null) return null;
        Long cost = orderProducts.stream().map(product -> {
            return  product.getCount() * product.getPrice();
        }).mapToLong(Long::longValue).sum();
        Order order = new Order();
        order.setProducts(orderProducts);
        order.setCost(cost);
        return order;
    }


    public boolean isCartOrder(Long orderId) {
        OrderEntity entity = orderRepository.findFirstByIdAndStage(orderId, OrderStage.IN_CART);
        return entity != null;
    }

    public Long createNewOrder(Long userId, Long productId, Integer count) {
        if(productService.isProductAvailable(productId)){
            OrderEntity orderEntity = createNewOrderForCart(userId);
            addProductIntoCartOrder(productId, orderEntity.getId(),count);
            return orderEntity.getId();
        }else {
            System.out.println("No product available for product ID "+productId);
            return null;
        }
    }

    private void addProductIntoCartOrder(Long productId, Long orderId, Integer count) {
        OrderProductMappingEntity mappingEntity = new OrderProductMappingEntity();
        mappingEntity.setProductId(productId);
        mappingEntity.setOrderId(orderId);
        mappingEntity.setProductCount(count);
        productMappingRepository.save(mappingEntity);
    }

    private OrderEntity createNewOrderForCart(Long userId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStage(OrderStage.IN_CART);
        orderEntity.setUserId(userId);
        orderEntity.setCreateTime(new Date());
        orderEntity.setUpdateTime(new Date());
        orderEntity = orderRepository.save(orderEntity);
        return orderEntity;
    }

    @Transactional
    public void placeOrder(Long orderId, Long userId) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
        if (optionalOrderEntity.isPresent()) {
            List<Product> products = getOrderProducts(orderId);
            products.stream().forEach(product -> {
                productService.markUnavailable(product.getId(),product.getCount());
            });
            optionalOrderEntity.get().setStage(OrderStage.PLACED);
            optionalOrderEntity.get().setUserId(userId);
            orderRepository.save(optionalOrderEntity.get());
        }
    }


    private List<Long> getOrderProductIds(Long orderId) {
        List<OrderProductMappingEntity> mappingEntities = productMappingRepository.findByOrderId(orderId);
        if (mappingEntities.isEmpty()) {
            return null;
        }
        return mappingEntities.stream().map(OrderProductMappingEntity::getProductId).collect(Collectors.toList());
    }
    private List<Product> getOrderProducts(Long orderId) {
        List<OrderProductMappingEntity> mappingEntities = productMappingRepository.findByOrderId(orderId);
        if (mappingEntities.isEmpty()) {
            return null;
        }

        List<Product> orderProducts = mappingEntities.stream().map(entity -> {
            Product product =productService.getProduct(entity.getProductId());
            product.setCount(entity.getProductCount());
            return product;
        }).collect(Collectors.toList());

        return orderProducts;
    }

    public List<Order> getOrderForUser(Long userId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByUserId(userId);
        if(orderEntities==null){
            return null;
        }
        return orderEntities.stream().map(orderEntity -> {
            return getOrderFromEntity(orderEntity);
        }).collect(Collectors.toList());
    }

    public List<Order> getAllOrder() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        if(orderEntities==null){
            return null;
        }
        return orderEntities.stream().map(orderEntity -> {
            return getOrderFromEntity(orderEntity);
        }).collect(Collectors.toList());

    }

    private Order getOrderFromEntity(OrderEntity orderEntity) {
        Order order =new Order();
        order.setId(orderEntity.getId());
        order.setOrderStage(orderEntity.getStage());
        order.setUserId(orderEntity.getUserId());
        order.setCreateTime(orderEntity.getCreateTime());
        order.setUpdateTime(orderEntity.getUpdateTime());
        return order;
    }


}
