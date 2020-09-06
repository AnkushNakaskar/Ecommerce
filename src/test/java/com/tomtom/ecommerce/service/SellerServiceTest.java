package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.person.Seller;
import com.tomtom.ecommerce.dao.SellerRepository;
import com.tomtom.ecommerce.entity.SellerEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class SellerServiceTest {

    @InjectMocks
    private SellerService sellerService;

    @Mock
    private SellerRepository sellerRepository;

    private static final Long SELLER_ID = 123L;


    @Test
    public void testGetAllSeller() {
        List<SellerEntity> entities = new LinkedList<>();
        entities.add(getDummySellerEntity());
        Mockito.when(sellerRepository.findAll()).thenReturn(entities);
        List<Seller> response = sellerService.getAllSeller();
        Assert.assertFalse(response.isEmpty());
        Assert.assertEquals(response.get(0).getId(), entities.get(0).getId());
    }


    @Test
    public void testNoSellerPresent() {
        Mockito.when(sellerRepository.findAll()).thenReturn(new LinkedList<>());
        List<Seller> response = sellerService.getAllSeller();
        Assert.assertTrue(response.isEmpty());
    }

    @Test
    public void testCreateNewSeller() {
        sellerService.saveOrUpdateSeller(getDummySellerBean());
        Mockito.verify(sellerRepository, Mockito.times(1)).save(Mockito.any(SellerEntity.class));
    }

    @Test
    public void testGetSellerIfPresent() {
        SellerEntity sellerEntity = getDummySellerEntity();
        Mockito.when(sellerRepository.findById(SELLER_ID)).thenReturn(Optional.of(sellerEntity));
        Seller seller = sellerService.getSeller(SELLER_ID);
        Assert.assertNotNull(seller);
        Assert.assertEquals(seller.getId(), sellerEntity.getId());
    }

    @Test
    public void testGetSellerIfNotPresent() {
        Mockito.when(sellerRepository.findById(SELLER_ID)).thenReturn(Optional.empty());
        Seller seller = sellerService.getSeller(SELLER_ID);
        Assert.assertNull(seller);

    }

    private SellerEntity getDummySellerEntity() {
        SellerEntity entity = new SellerEntity();
        entity.setId(1L);
        entity.setName("Test");
        entity.setStatus(true);
        return entity;
    }


    private Seller getDummySellerBean() {
        Seller entity = new Seller();
        entity.setId(1L);
        entity.setName("Test");
        return entity;
    }

}
