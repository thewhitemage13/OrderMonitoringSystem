package org.thewhitemage13.interfaces;

import org.thewhitemage13.ProductCreateEvent;

/**
 * Interface for handling events related to adding a new product to the system.
 * <p>
 * This interface defines the method to handle an event where a new product is created
 * and needs to be processed or added to the system.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #addProduct(ProductCreateEvent)} - Handles the addition of a new product based on the provided event data.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Implement this interface in components that need to handle the addition of a product,
 * such as product service or event listeners. The {@link ProductCreateEvent} contains
 * all necessary information about the new product (e.g., product ID, name, quantity, and price).
 * </p>
 *
 * @see ProductCreateEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface AddProductEventHandlerInterface {

    /**
     * Method to handle adding a new product based on the provided event.
     * <p>
     * The event contains all the necessary details of the new product, which can then
     * be processed, validated, and stored in the system. This method should be called
     * when a new product needs to be added after an event is triggered.
     * </p>
     *
     * @param productCreateEvent the event containing the details of the new product
     */
    void addProduct(ProductCreateEvent productCreateEvent);
}
