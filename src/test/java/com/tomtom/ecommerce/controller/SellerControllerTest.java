package com.tomtom.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.ecommerce.bean.Product;
import com.tomtom.ecommerce.bean.person.Seller;
import com.tomtom.ecommerce.service.ProductService;
import com.tomtom.ecommerce.service.SellerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = SellerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private SellerService sellerService;


    @MockBean
    private ProductService productService;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetAllSellers() throws Exception {
        Mockito.when(sellerService.getAllSeller()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/sellers")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testGetSeller() throws Exception {
        Seller seller = new Seller();
        seller.setName("test");
        seller.setId(1L);
        Mockito.when(sellerService.getSeller(123L)).thenReturn(seller);
        this.mockMvc.perform(get("/sellers/" + 123l)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testaddNewSeller() throws Exception {
        Seller seller = new Seller();
        seller.setName("test");
        seller.setId(1L);
        this.mockMvc.perform(post("/sellers").contentType("application/json").content(objectMapper.writeValueAsString(seller))).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testGetAllProductOfSeller() throws Exception {
        List<Product> products =new LinkedList<>();
        Product product =new Product();
        product.setId(123L);
        product.setSellerId(1L);
        product.setPrice(20L);
        product.setAvailable(true);
        products.add(product);
        Mockito.when(productService.getAllProductForSeller(1L)).thenReturn(products);
        this.mockMvc.perform(get("/sellers/" + 1+"/products")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testAddNewProductFromSeller() throws Exception {
        Product product =new Product();
        product.setId(123L);
        product.setSellerId(1L);
        product.setPrice(20L);
        product.setAvailable(true);
        this.mockMvc.perform(get("/sellers/" + 1+"/products")).andExpect(status().isOk()).andDo(print());
    }


    @Test
    public void testDeleteProductFromSeller() throws Exception {
        Product product =new Product();
        product.setId(123L);
        product.setSellerId(1L);
        product.setPrice(20L);
        product.setAvailable(true);
        this.mockMvc.perform(delete("/sellers/" + 1+"/products/"+123)).andExpect(status().isOk()).andDo(print());
    }

}
