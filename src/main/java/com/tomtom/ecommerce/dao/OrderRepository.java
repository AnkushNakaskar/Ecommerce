package com.tomtom.ecommerce.dao;


import com.tomtom.ecommerce.constant.OrderStage;
import com.tomtom.ecommerce.entity.OrderEntity;
import com.tomtom.ecommerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findFirstByIdAndStage(Long orderId, OrderStage stage);
}
