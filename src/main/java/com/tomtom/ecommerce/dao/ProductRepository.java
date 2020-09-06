package com.tomtom.ecommerce.dao;


import com.tomtom.ecommerce.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findBySellerId(Long sellerId);
    ProductEntity findBySellerIdAndName(Long sellerId, String name);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM product WHERE sellerId = :sellerId and id = :productId", nativeQuery = true)
    int deleteProduct(@Param("sellerId") Long sellerId, @Param("productId") Long productId);
}
