package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreateOrderDTO;
import org.thewhitemage13.dto.ShowOrderDTO;
import org.thewhitemage13.entity.Order;
import org.thewhitemage13.exception.OrderNotFoundException;

import java.util.List;

public interface OrderServiceInterface {
    void createOrder(CreateOrderDTO createOrderDTO);
    void updateOrderStatus(Long orderId, String status) throws OrderNotFoundException;
    List<Order> showAllOrders();
    ShowOrderDTO showOrderById(Long orderId) throws OrderNotFoundException;
}
