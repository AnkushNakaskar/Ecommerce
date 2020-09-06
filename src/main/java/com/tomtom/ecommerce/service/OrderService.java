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
    public void removeProduct(Long productId, Long orderId) {
        productMappingRepository.deleteMappingOrderIdAndProductId(orderId, productId);
    }


    public Order getOrder(Long orderId, OrderStage stage) {
        OrderEntity orderEntity = orderRepository.findFirstByIdAndStage(orderId, stage);
        if (orderEntity == null) {
            return null;
        }
        List<Long> productIds = getOrderProductIds(orderId);
        if (productIds == null) return null;
        List<Product> orderProducts = productService.getProducts(productIds);
        Long cost = orderProducts.stream().map(product -> {
            return  product.getPrice();
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

    public Long createNewOrder(Long userId, Long productId) {
        OrderEntity orderEntity = createNewOrderForCart(userId);
        addProductIntoCartOrder(productId, orderEntity.getId());
        return orderEntity.getId();
    }

    private void addProductIntoCartOrder(Long productId, Long orderId) {
        OrderProductMappingEntity mappingEntity = new OrderProductMappingEntity();
        mappingEntity.setProductId(productId);
        mappingEntity.setOrderId(orderId);
        productMappingRepository.save(mappingEntity);
    }

    private OrderEntity createNewOrderForCart(Long userId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStage(OrderStage.IN_CART);
        orderEntity.setUserId(userId);
        orderEntity = orderRepository.save(orderEntity);
        return orderEntity;
    }

    @Transactional
    public void placeOrder(Long orderId) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
        if (optionalOrderEntity.isPresent()) {
            List<Long> productIds = getOrderProductIds(orderId);
            productService.markUnavailable(productIds);
            optionalOrderEntity.get().setStage(OrderStage.PLACED);
            orderRepository.save(optionalOrderEntity.get());
        }
    }

    private List<Long> getOrderProductIds(Long orderId) {
        List<OrderProductMappingEntity> mappingEntities = productMappingRepository.findByOrderId(orderId);
        if (mappingEntities.isEmpty()) {
            return null;
        }

        List<Long> productIds = mappingEntities.stream().map(OrderProductMappingEntity::getProductId).collect(Collectors.toList());
        return productIds;
    }
}
