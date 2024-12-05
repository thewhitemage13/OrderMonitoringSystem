package org.thewhitemage13.handler;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.interfaces.OrderEventHandlerInterface;
import org.thewhitemage13.service.OrderStatisticService;

/**
 * Handles the {@link OrderCreatedEvent} for order creation and update events.
 * <p>
 * This class listens for events on the "order.created" and "order.updated" Kafka topics and processes them
 * to create or update order statistics using the {@link OrderStatisticService}.
 * </p>
 *
 * @see OrderCreatedEvent
 * @see OrderStatisticService
 * @see OrderEventHandlerInterface
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
public class OrderEventHandler implements OrderEventHandlerInterface {
    private final OrderStatisticService orderStatisticService;

    /**
     * Constructs a new {@link OrderEventHandler} instance.
     * <p>
     * The constructor initializes the handler with an instance of {@link OrderStatisticService},
     * which is used to create or update order statistics based on incoming order events.
     * </p>
     *
     * @param orderStatisticService the service used for managing order statistics
     */
    public OrderEventHandler(OrderStatisticService orderStatisticService) {
        this.orderStatisticService = orderStatisticService;
    }

    /**
     * Handles the {@link OrderCreatedEvent} when an order is created.
     * <p>
     * This method listens for the "order.created" topic and processes the event by creating an order statistic.
     * </p>
     *
     * @param orderCreatedEvent the event containing details about the newly created order
     */
    @KafkaListener(topics = "order.created")
    public void create(OrderCreatedEvent orderCreatedEvent) {
        orderStatisticService.createOrderStatistic(orderCreatedEvent);
    }

    /**
     * Handles the {@link OrderCreatedEvent} when an order is updated.
     * <p>
     * This method listens for the "order.updated" topic and processes the event by updating the existing
     * order statistic.
     * </p>
     *
     * @param orderCreatedEvent the event containing details about the updated order
     */
    @Override
    @KafkaListener(topics = "order.updated")
    public void update(OrderCreatedEvent orderCreatedEvent) {
        orderStatisticService.updateOrderStatistic(orderCreatedEvent);
    }
}
