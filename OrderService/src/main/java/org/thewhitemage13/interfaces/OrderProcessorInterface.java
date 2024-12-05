package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.dto.ShowOrderDTO;
import org.thewhitemage13.entity.Order;

/**
 * Interface for processing and converting order-related data.
 * <p>
 * This interface defines methods for converting an {@link Order} entity into a {@link ShowOrderDTO} object
 * and initializing an {@link OrderCreatedEvent}. Implementations of this interface should handle the business
 * logic for transforming and processing orders in the application.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Converts an {@link Order} entity to a {@link ShowOrderDTO} object.</li>
 *     <li>Initializes an {@link OrderCreatedEvent} based on the order information.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This interface is typically used in services or components responsible for handling orders, converting them
 * to appropriate DTOs for API responses, and emitting events related to the creation of new orders.
 * </p>
 *
 * @see Order
 * @see ShowOrderDTO
 * @see OrderCreatedEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface OrderProcessorInterface {

    /**
     * Converts an {@link Order} entity into a {@link ShowOrderDTO} for display purposes.
     *
     * @param order the {@link Order} entity to convert
     * @return a {@link ShowOrderDTO} representing the order information
     */
    ShowOrderDTO returnShowOrderDto(Order order);

    /**
     * Initializes an {@link OrderCreatedEvent} based on the provided {@link Order}.
     *
     * @param order the {@link Order} entity used to initialize the event
     * @return an {@link OrderCreatedEvent} containing the order details
     */
    OrderCreatedEvent orderEventInitialize(Order order);
}
