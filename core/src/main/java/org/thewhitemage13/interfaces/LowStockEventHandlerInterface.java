package org.thewhitemage13.interfaces;

import org.thewhitemage13.ProductCreateEvent;

/**
 * Interface for handling events related to low stock notifications.
 * <p>
 * This interface defines the method to handle events triggered when a product's stock
 * falls below a specified threshold, indicating that the product is running low on inventory.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #lowStock(ProductCreateEvent)} - Handles the low stock event and processes the product accordingly.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Implement this interface in components that need to process or respond to low stock events,
 * such as inventory management systems, alert systems, or restocking services. The {@link ProductCreateEvent}
 * contains the details of the product that is running low, such as product ID, name, quantity, and price.
 * </p>
 *
 * @see ProductCreateEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface LowStockEventHandlerInterface {

    /**
     * Method to handle low stock event triggered when a product's stock falls below a specified threshold.
     * <p>
     * This method will be called when a product has been identified as running low in inventory. It will receive
     * an event containing the product details, allowing the implementation to process the event (e.g., sending alerts
     * or initiating restocking actions).
     * </p>
     *
     * @param productCreateEvent the event containing the details of the product with low stock
     */
    void lowStock(ProductCreateEvent productCreateEvent);
}
