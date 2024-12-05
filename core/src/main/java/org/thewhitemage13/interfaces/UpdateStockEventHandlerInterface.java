package org.thewhitemage13.interfaces;

import org.thewhitemage13.ProductCreateEvent;

/**
 * Interface for handling stock update events.
 * <p>
 * This interface defines the method for processing events triggered when there is a stock update. Implementations
 * of this interface are responsible for handling updates related to product stock, such as adjusting inventory
 * levels based on new stock arrivals or updates from various systems.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #updateStock(ProductCreateEvent)} - Processes the update of product stock based on the details provided in the event.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Implement this interface in components that need to respond to stock update events. For example, after a product's
 * quantity is updated, systems might need to adjust inventory records, notify external systems, or trigger other workflows
 * related to product stock. The {@link ProductCreateEvent} object contains the details of the updated product, including
 * product ID, name, quantity, and price.
 * </p>
 *
 * @see ProductCreateEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UpdateStockEventHandlerInterface {

    /**
     * Method to handle the event when a product stock is updated.
     * <p>
     * This method is triggered when an update occurs in the stock of a product. The {@link ProductCreateEvent}
     * contains the updated information, such as the product's quantity, name, and price. The implementation of this method
     * should handle the necessary processing to reflect the stock changes, including adjusting inventory records,
     * updating databases, or notifying other services.
     * </p>
     *
     * @param productCreateEvent the event containing the details of the updated product stock
     */
    void updateStock(ProductCreateEvent productCreateEvent);
}
