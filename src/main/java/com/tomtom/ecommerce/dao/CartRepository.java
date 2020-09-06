package com.tomtom.ecommerce.dao;


import com.tomtom.ecommerce.entity.CartEntity;
import com.tomtom.ecommerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findFirstByUserIdOrderByCreateTimeDesc(Long userId);
}
