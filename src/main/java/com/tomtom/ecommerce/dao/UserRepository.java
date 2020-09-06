package com.tomtom.ecommerce.dao;


import com.tomtom.ecommerce.entity.SellerEntity;
import com.tomtom.ecommerce.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


}
