package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;

/**
 * Interface for handling order creation events.
 * <p>
 * This interface defines the method to handle events triggered when an order is created. Implementations
 * of this interface will process the details of the created order and take appropriate actions, such as
 * updating inventory or notifying other systems.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #create(OrderCreatedEvent)} - Handles the creation of an order and processes the related actions.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Implement this interface in components that need to process or respond to order creation events,
 * such as inventory management systems, order processing systems, or notification systems. The
 * {@link OrderCreatedEvent} contains the details of the order, including product information and quantity.
 * </p>
 *
 * @see OrderCreatedEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface OrderCreateEventHandlerInterface {

    /**
     * Method to handle the event when an order is created.
     * <p>
     * This method will be called when a new order is placed. It will receive an event containing the details
     * of the order, such as product ID, quantity, and other relevant information. The implementation of this
     * method should handle the processing logic for the order.
     * </p>
     *
     * @param orderCreatedEvent the event containing the details of the created order
     */
    void create(OrderCreatedEvent orderCreatedEvent);
}
