package org.thewhitemage13.processor;

import org.springframework.stereotype.Component;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.dto.ShowOrderDTO;
import org.thewhitemage13.entity.Order;
import org.thewhitemage13.interfaces.OrderProcessorInterface;

@Component
public class OrderProcessor implements OrderProcessorInterface {

    @Override
    public ShowOrderDTO returnShowOrderDto(Order order) {
        return new ShowOrderDTO
                (
                        order.getUserId(),
                        order.getItems(),
                        order.getAddress(),
                        order.getStatus(),
                        order.getTotalSum(),
                        order.getProductId(),
                        order.getCountOfItems(),
                        order.getCreatedAt(),
                        order.getUpdatedAt()
                );
    }

    @Override
    public OrderCreatedEvent orderEventInitialize(Order order) {
        return new OrderCreatedEvent
                (
                        order.getId(),
                        order.getUserId(),
                        order.getItems(),
                        order.getAddress(),
                        order.getStatus(),
                        order.getCountOfItems(),
                        order.getProductId(),
                        order.getTotalSum(),
                        order.getCreatedAt(),
                        order.getUpdatedAt()
                );
    }
}
