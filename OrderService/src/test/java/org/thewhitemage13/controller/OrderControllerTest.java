package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.dto.CreateOrderDTO;
import org.thewhitemage13.dto.ShowOrderDTO;
import org.thewhitemage13.entity.Order;
import org.thewhitemage13.exception.OrderNotFoundException;
import org.thewhitemage13.service.OrderService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderController orderController;

    @Test
    void testCreateOrder_Success() {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        ResponseEntity<String> response = orderController.createOrder(createOrderDTO);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order created", response.getBody());
    }

    @Test
    void testCreateOrder_Failure() {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        doThrow(new RuntimeException("Internal error")).when(orderService).createOrder(any(CreateOrderDTO.class));
        ResponseEntity<String> response = orderController.createOrder(createOrderDTO);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal error", response.getBody());
    }

    @Test
    void testUpdateOrderStatus_Success() {
        ResponseEntity<String> response = orderController.updateOrderStatus(1L, "DELIVERED");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order updated", response.getBody());
    }

    @Test
    void testUpdateOrderStatus_NotFound() throws Exception{
        doThrow(new OrderNotFoundException()).when(orderService).updateOrderStatus(eq(1L), eq("CANCELLED"));
        ResponseEntity<String> response = orderController.updateOrderStatus(1L, "CANCELLED");
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Order with id = 1 not found", response.getBody());
    }

    @Test
    void testGetAllOrders() {
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());
        when(orderService.showAllOrders()).thenReturn(mockOrders);
        List<Order> orders = orderController.getAllOrders();
        assertEquals(2, orders.size());
    }

    @Test
    void testGetOrderById_Success() throws Exception {
        Order order = new Order();
        order.setId(1L);
        ShowOrderDTO showOrderDTO = new ShowOrderDTO();

        when(orderService.showOrderById(1L)).thenReturn(showOrderDTO);
        ResponseEntity<String> response = orderController.getOrderById(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetOrderById_NotFound() throws Exception {
        when(orderService.showOrderById(1L)).thenThrow(new OrderNotFoundException());
        ResponseEntity<String> response = orderController.getOrderById(1L);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Order with id = 1 not found", response.getBody());
    }
}