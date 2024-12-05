package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;

/**
 * Interface for handling order update events.
 * <p>
 * This interface defines the method for processing events triggered when an order is updated. Implementations
 * of this interface are responsible for handling the updates to the order, such as recalculating prices, updating inventory,
 * or notifying external systems.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #update(OrderCreatedEvent)} - Processes the update of an order based on the details provided in the event.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Implement this interface in components that need to respond to order update events. For example, inventory management
 * systems might adjust stock levels, or order processing systems might update the status of an order. The
 * {@link OrderCreatedEvent} object will contain the details of the updated order, including product information and quantities.
 * </p>
 *
 * @see OrderCreatedEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface OrderUpdateEventHandlerInterface {

    /**
     * Method to handle the event when an order is updated.
     * <p>
     * This method will be triggered when an existing order is updated. The {@link OrderCreatedEvent} contains the
     * updated information, such as product details, quantities, and any relevant changes to the order.
     * The implementation of this method should handle the necessary processing for updating the order.
     * </p>
     *
     * @param orderCreatedEvent the event containing the details of the updated order
     */
    void update(OrderCreatedEvent orderCreatedEvent);
}
