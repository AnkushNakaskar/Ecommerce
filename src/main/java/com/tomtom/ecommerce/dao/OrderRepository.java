package com.tomtom.ecommerce.dao;


import com.tomtom.ecommerce.constant.OrderStage;
import com.tomtom.ecommerce.entity.OrderEntity;
import com.tomtom.ecommerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findFirstByIdAndStage(Long orderId, OrderStage stage);
    List<OrderEntity> findAllByUserId(Long userId);


}
