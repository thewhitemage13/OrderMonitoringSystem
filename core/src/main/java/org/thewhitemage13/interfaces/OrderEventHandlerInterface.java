package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;

/**
 * Interface for handling order update events.
 * <p>
 * This interface defines the method to handle events triggered when an order is updated. Implementations
 * of this interface will process the details of the updated order and take appropriate actions, such as
 * updating inventory, recalculating total prices, or notifying other systems.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #update(OrderCreatedEvent)} - Handles the update of an order and processes the related actions.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Implement this interface in components that need to process or respond to order update events,
 * such as inventory management systems, order processing systems, or notification systems. The
 * {@link OrderCreatedEvent} contains the details of the updated order, including product information and quantity.
 * </p>
 *
 * @see OrderCreatedEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface OrderEventHandlerInterface {

    /**
     * Method to handle the event when an order is updated.
     * <p>
     * This method will be called when an existing order is updated. It will receive an event containing the details
     * of the updated order, such as product ID, quantity, and other relevant information. The implementation of this
     * method should handle the processing logic for the order update.
     * </p>
     *
     * @param orderCreatedEvent the event containing the details of the updated order
     */
    void update(OrderCreatedEvent orderCreatedEvent);
}
