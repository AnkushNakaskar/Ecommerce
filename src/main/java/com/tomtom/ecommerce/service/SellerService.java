package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.person.Seller;
import com.tomtom.ecommerce.dao.SellerRepository;
import com.tomtom.ecommerce.entity.SellerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerService {


    @Autowired
    private SellerRepository sellerRepository;



    public List<Seller> getAllSeller(){
        List<SellerEntity> entities = sellerRepository.findAll();
        if(entities!=null || entities.isEmpty()){
            return entities.stream().map(entity->{
                Seller seller =new Seller();
                seller.setId(entity.getId());
                seller.setName(entity.getName());
                return seller;
            }).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }

    public void saveOrUpdateSeller(Seller seller){
        SellerEntity entity  =new SellerEntity();
        entity.setName(seller.getName());
        entity.setStatus(Boolean.TRUE);
        sellerRepository.save(entity);
    }

    public Seller getSeller(Long sellerId) {
        Optional<SellerEntity> optionalSellerEntity = sellerRepository.findById(sellerId);
        if(optionalSellerEntity.isPresent()){
            Seller seller =new Seller();
            seller.setName(optionalSellerEntity.get().getName());
            seller.setId(optionalSellerEntity.get().getId());
            return seller;
        }
        return null;
    }
}
