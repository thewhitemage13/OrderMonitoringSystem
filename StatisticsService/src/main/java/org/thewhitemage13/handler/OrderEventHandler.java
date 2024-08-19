package org.thewhitemage13.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.interfaces.OrderEventHandlerInterface;
import org.thewhitemage13.service.OrderStatisticService;

@Component
public class OrderEventHandler implements OrderEventHandlerInterface {
    private final OrderStatisticService orderStatisticService;

    public OrderEventHandler(OrderStatisticService orderStatisticService) {
        this.orderStatisticService = orderStatisticService;
    }

    @KafkaListener(topics = "order.created")
    public void create(OrderCreatedEvent orderCreatedEvent) {
        orderStatisticService.createOrderStatistic(orderCreatedEvent);
    }

    @Override
    @KafkaListener(topics = "order.updated")
    public void update(OrderCreatedEvent orderCreatedEvent) {
        orderStatisticService.updateOrderStatistic(orderCreatedEvent);
    }
}
