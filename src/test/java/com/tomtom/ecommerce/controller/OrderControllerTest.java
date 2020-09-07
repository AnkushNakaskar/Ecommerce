package com.tomtom.ecommerce.controller;

import com.tomtom.ecommerce.bean.Order;
import com.tomtom.ecommerce.constant.OrderStage;
import com.tomtom.ecommerce.dao.OrderController;
import com.tomtom.ecommerce.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class OrderControllerTest {

    private static final Long USER_ID = 1L;
    private static final Long ORDER_ID = 11L;
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private OrderService orderService;


    @Test
    public void testGetAllOrders() throws Exception {

        List<Order> orders = Arrays.asList(getDummyOrderBean());
        Mockito.when(orderService.getAllOrder()).thenReturn(orders);
        this.mockMvc.perform(get("/orders/" )).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void testGetAllOrdersForUser() throws Exception {

        List<Order> orders = Arrays.asList(getDummyOrderBean());
        Mockito.when(orderService.getOrderForUser(USER_ID)).thenReturn(orders);
        this.mockMvc.perform(get("/orders?userId="+USER_ID )).andExpect(status().isOk()).andDo(print());
    }

    private Order getDummyOrderBean() {
        Order order =new Order();
        order.setUserId(USER_ID);
        order.setId(ORDER_ID);
        order.setOrderStage(OrderStage.IN_CART);
        return order;
    }


}
