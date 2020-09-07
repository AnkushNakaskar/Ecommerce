package com.tomtom.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomtom.ecommerce.bean.Cart;
import com.tomtom.ecommerce.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CartController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CartService cartService;

    private static final Long USER_ID = 123L;


    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetCartDetails() throws Exception {
        Mockito.when(cartService.getCartDetails(USER_ID)).thenReturn(new Cart());
        this.mockMvc.perform(get("/users/" + 123L + "/cart")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testAddNewProduct() throws Exception {
        Long productId = 124L;
        this.mockMvc.perform(put("/users/" + USER_ID + "/cart/addProduct?productId=" + productId +"&count="+2)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testRemoveProduct() throws Exception {
        Long productId = 124L;
        this.mockMvc.perform(put("/users/" + USER_ID + "/cart/removeProduct?productId=" + productId+"&count="+2)).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testPlaceOrder() throws Exception {
        this.mockMvc.perform(post("/users/" + USER_ID + "/cart/placeOrder")).andExpect(status().isOk()).andDo(print());
    }

}
