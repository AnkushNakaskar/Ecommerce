package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.Product;
import com.tomtom.ecommerce.dao.ProductRepository;
import com.tomtom.ecommerce.entity.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;



    public Product getProduct(Long productId) {
        Optional<ProductEntity> entityOptional = productRepository.findById(productId);
        if (entityOptional.isPresent()) {
            return getProductFromEntity(entityOptional.get());
        }
        return null;
    }



    public void markUnavailable(List<Long> productIds) {
        List<ProductEntity> productEntities = productRepository.findAllById(productIds);
        if (!productEntities.isEmpty()) {
            productEntities.stream().forEach(productEntity ->{
              if(productEntity.getCount()==null || productEntity.getCount()<=1){
                  productEntity.setAvailable(Boolean.FALSE);
              }else {
                  productEntity.setCount(productEntity.getCount()-1);
              }
            });
            productRepository.saveAll(productEntities);
        }
    }


    public List<Product> getAllProductForSeller(Long sellerId) {
        List<ProductEntity> entities = productRepository.findBySellerId(sellerId);
        if (!entities.isEmpty()) {
            return entities.stream().map(entity -> getProductFromEntity(entity)).collect(Collectors.toList());
        }
        return null;
    }



    public List<Product> getProducts(List<Long> productIds) {
        List<ProductEntity> entities = productRepository.findAllById(productIds);
        if (entities.isEmpty()) {
            return new ArrayList<>(0);
        }
        return entities.stream().map(entity -> getProductFromEntity(entity)).collect(Collectors.toList());
    }


    public Boolean isProductAvailable(Long productId) {
        Optional<ProductEntity> productOptional = productRepository.findById(productId);
        return productOptional.isPresent() && productOptional.get().getAvailable() && productOptional.get().getCount()!=null && productOptional.get().getCount()>0;
    }

    public List<Product> getAllProducts() {
        List<ProductEntity> entities = productRepository.findAll();
        if (entities.isEmpty()) {
            return null;
        }
        return entities.stream().map(this::getProductFromEntity).collect(Collectors.toList());

    }

    private Product getProductFromEntity(ProductEntity entity) {
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setAvailable(entity.getAvailable());
        product.setPrice(entity.getPrice());
        product.setSellerId(entity.getSellerId());
        product.setCount(entity.getCount());
        return product;
    }

    public void addNewProduct(Product product) {
        ProductEntity entity =productRepository.findBySellerIdAndName(product.getSellerId(),product.getName());
        if(entity==null){
            entity =new ProductEntity();
            entity.setCount(0);
        }
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setSellerId(product.getSellerId());
        entity.setCount(entity.getCount()+1);
        productRepository.save(entity);
    }

    public void removeProduct(Long sellerId, Long productId) {
        productRepository.deleteProduct(sellerId,productId);
    }
}
