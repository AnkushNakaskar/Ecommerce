package com.tomtom.ecommerce.dao;


import com.tomtom.ecommerce.entity.OrderProductMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductMappingRepository extends JpaRepository<OrderProductMappingEntity, Long> {
    List<OrderProductMappingEntity> findByOrderId(Long orderId);


    @Modifying
    @Query(value = "DELETE FROM order_product_mapping WHERE orderId = :orderId and productId = :productId", nativeQuery = true)
    int deleteMappingOrderIdAndProductId(@Param("orderId") Long orderId, @Param("productId") Long productId);


}
