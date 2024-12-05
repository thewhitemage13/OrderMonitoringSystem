package org.thewhitemage13.processor;

import org.springframework.stereotype.Component;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.dto.ShowOrderDTO;
import org.thewhitemage13.entity.Order;
import org.thewhitemage13.interfaces.OrderProcessorInterface;

/**
 * The {@code OrderProcessor} class is responsible for processing orders
 * and converting order entities into Data Transfer Objects (DTOs) and events.
 * <p>
 * This class implements the {@link OrderProcessorInterface} and provides methods
 * to create a {@link ShowOrderDTO} object from an {@link Order} entity,
 * as well as to initialize an {@link OrderCreatedEvent} from the same entity.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Converting an {@link Order} entity to a {@link ShowOrderDTO} for presentation.</li>
 *     <li>Initializing an {@link OrderCreatedEvent} for order-related events.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This component is used to transform order data for various use cases such as displaying
 * order information or publishing order-related events (e.g., order creation).
 * </p>
 *
 * @see OrderProcessorInterface
 * @see ShowOrderDTO
 * @see OrderCreatedEvent
 * @see Order
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
public class OrderProcessor implements OrderProcessorInterface {

    /**
     * Converts an {@link Order} entity to a {@link ShowOrderDTO}.
     * <p>
     * This method creates a {@link ShowOrderDTO} object which is a representation of
     * an order entity suitable for displaying order details to the user or for
     * transferring order data between layers.
     * </p>
     *
     * @param order the {@link Order} entity to be converted
     * @return a {@link ShowOrderDTO} representing the given order
     */
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
                        order.getCountOfItems()
                );
    }

    /**
     * Initializes an {@link OrderCreatedEvent} from an {@link Order} entity.
     * <p>
     * This method creates an {@link OrderCreatedEvent} object that is used to publish
     * events related to order creation. The event carries relevant order data like
     * user ID, order items, and status.
     * </p>
     *
     * @param order the {@link Order} entity to initialize the event from
     * @return an {@link OrderCreatedEvent} initialized with the order data
     */
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
