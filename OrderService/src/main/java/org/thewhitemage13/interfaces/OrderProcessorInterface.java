package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.dto.ShowOrderDTO;
import org.thewhitemage13.entity.Order;

public interface OrderProcessorInterface {
    ShowOrderDTO returnShowOrderDto(Order order);
    OrderCreatedEvent orderEventInitialize(Order order);
}
