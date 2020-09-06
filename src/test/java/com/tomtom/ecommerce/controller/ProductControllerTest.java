package com.tomtom.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.ecommerce.bean.Product;
import com.tomtom.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ProductService productService;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetAllSProducts() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(new LinkedList<>());
        this.mockMvc.perform(get("/products")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testGetProduct() throws Exception {
        Product product = new Product();
        product.setName("test");
        product.setId(1L);
        Mockito.when(productService.getProduct(123L)).thenReturn(product);
        this.mockMvc.perform(get("/products/" + 123l)).andExpect(status().isOk()).andDo(print());
    }



}
