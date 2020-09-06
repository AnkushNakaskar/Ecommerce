package com.tomtom.ecommerce.service;

import com.tomtom.ecommerce.bean.Product;
import com.tomtom.ecommerce.dao.ProductRepository;
import com.tomtom.ecommerce.entity.ProductEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceTest {

    private static final Long SELLER_ID = 1L;
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private static final Long PRODUCT_ID = 123L;


    @Test
    public void testGetAllProducts() {
        List<ProductEntity> entities = new LinkedList<>();
        entities.add(getDummyProductEntity());
        Mockito.when(productRepository.findAll()).thenReturn(entities);
        List<Product> response = productService.getAllProducts();
        Assert.assertFalse(response.isEmpty());
        Assert.assertEquals(response.get(0).getId(), entities.get(0).getId());
    }


    @Test
    public void testNoProductPresent() {
        Mockito.when(productRepository.findAll()).thenReturn(new LinkedList<>());
        List<Product> response = productService.getAllProducts();
        Assert.assertNull(response);
    }

    @Test
    public void testAddNewProduct() {
        productService.addNewProduct(getDummyProductBean());
        Mockito.verify(productRepository, Mockito.times(1)).save(Mockito.any(ProductEntity.class));
    }

    @Test
    public void testGetProductIfPresent() {
        ProductEntity productEntity = getDummyProductEntity();
        Mockito.when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productEntity));
        Product product = productService.getProduct(PRODUCT_ID);
        Assert.assertNotNull(product);
        Assert.assertEquals(product.getId(), productEntity.getId());
    }

    @Test
    public void testGetProductIfNotPresent() {
        Mockito.when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());
        Product product = productService.getProduct(PRODUCT_ID);
        Assert.assertNull(product);

    }

    @Test
    public void testMarkUnavailable() {
        List<Long> productIds = Arrays.asList(PRODUCT_ID);

        List<ProductEntity> entities = new LinkedList<>();
        entities.add(getDummyProductEntity());

        Mockito.when(productRepository.findAllById(productIds)).thenReturn(entities);
        productService.markUnavailable(productIds);
        Mockito.verify(productRepository, Mockito.times(1)).saveAll(Mockito.anyCollection());
    }

    @Test
    public void testGetAllProductForSeller() {
        List<ProductEntity> entities = new LinkedList<>();
        entities.add(getDummyProductEntity());
        Mockito.when(productRepository.findBySellerId(SELLER_ID)).thenReturn(entities);

        List<Product> sellerProducts = productService.getAllProductForSeller(SELLER_ID);
        Assert.assertNotNull(sellerProducts);
        Assert.assertEquals(entities.get(0).getId(), sellerProducts.get(0).getId());
    }

    @Test
    public void testGetAllProductForSellerEmpty() {
        Mockito.when(productRepository.findBySellerId(SELLER_ID)).thenReturn(new LinkedList<>());
        List<Product> sellerProducts = productService.getAllProductForSeller(SELLER_ID);
        Assert.assertNull(sellerProducts);
    }

    @Test
    public void testGetAllProductsByIds() {
        List<Long> productIds = Arrays.asList(PRODUCT_ID);
        List<ProductEntity> entities = new LinkedList<>();
        entities.add(getDummyProductEntity());
        Mockito.when(productRepository.findAllById(productIds)).thenReturn(entities);
        List<Product> products = productService.getProducts(productIds);
        Assert.assertNotNull(products);
        Assert.assertEquals(products.get(0).getId(), entities.get(0).getId());
    }

    @Test
    public void testGetAllProductsByIdsEmptyResult() {
        List<Long> productIds = Arrays.asList(PRODUCT_ID);
        Mockito.when(productRepository.findAllById(productIds)).thenReturn(new LinkedList<>());
        List<Product> products = productService.getProducts(productIds);
        Assert.assertTrue(products.isEmpty());
    }

    @Test
    public void testIsProductAvailableWhenProductNotPresent() {
        Assert.assertFalse(productService.isProductAvailable(PRODUCT_ID));
    }

    @Test
    public void testIsProductAvailableWhenProductIsPresentNotAvailable() {
        ProductEntity productEntity = getDummyProductEntity();
        productEntity.setAvailable(false);
        Mockito.when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productEntity));
        Assert.assertFalse(productService.isProductAvailable(PRODUCT_ID));
    }

    @Test
    public void testIsProductAvailableWhenProductIsPresentAvailable() {
        ProductEntity productEntity = getDummyProductEntity();
        Mockito.when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(productEntity));
        Assert.assertTrue(productService.isProductAvailable(PRODUCT_ID));
    }

    @Test
    public void testRemoveProduct() {
        productService.removeProduct(SELLER_ID, PRODUCT_ID);
        Mockito.verify(productRepository, Mockito.times(1)).deleteProduct(SELLER_ID, PRODUCT_ID);
    }


    private ProductEntity getDummyProductEntity() {
        ProductEntity entity = new ProductEntity();
        entity.setId(1L);
        entity.setName("Test");
        entity.setAvailable(true);
        entity.setPrice(20L);
        entity.setCount(1);
        return entity;
    }


    private Product getDummyProductBean() {
        Product entity = new Product();
        entity.setId(1L);
        entity.setName("Test");
        entity.setAvailable(true);
        entity.setPrice(20L);
        return entity;
    }

}
