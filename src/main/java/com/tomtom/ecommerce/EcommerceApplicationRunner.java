package com.tomtom.ecommerce;

import com.tomtom.ecommerce.dao.ProductRepository;
import com.tomtom.ecommerce.dao.SellerRepository;
import com.tomtom.ecommerce.dao.UserRepository;
import com.tomtom.ecommerce.entity.ProductEntity;
import com.tomtom.ecommerce.entity.SellerEntity;
import com.tomtom.ecommerce.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class EcommerceApplicationRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerRepository sellerRepository;


    @Autowired
    private ProductRepository productRepository;


    @Override
    public void run(String... args) throws Exception {
        UserEntity user =new UserEntity();
        user.setName("Ankush");
        user.setStatus(Boolean.TRUE);
        userRepository.save(user);


        SellerEntity sellerEntity =new SellerEntity();
        sellerEntity.setStatus(Boolean.TRUE);
        sellerEntity.setName("Seller");
        sellerEntity =sellerRepository.save(sellerEntity);


        ProductEntity entity =new ProductEntity();
        entity.setAvailable(Boolean.TRUE);
        entity.setSellerId(sellerEntity.getId());
        entity.setPrice(20L);
        entity.setCount(1);
        entity.setName("New product");


        ProductEntity entity1 =new ProductEntity();
        entity1.setAvailable(Boolean.TRUE);
        entity1.setSellerId(sellerEntity.getId());
        entity1.setPrice(20L);
        entity1.setCount(1);
        entity1.setName("New product 1");

        productRepository.save(entity);
        productRepository.save(entity1);


    }
}
