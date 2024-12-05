package org.thewhitemage13.processor;

import org.springframework.stereotype.Component;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.Product;

/**
 * Processor class for converting Product entities into ProductCreateEvent.
 * <p>
 * This class is responsible for transforming Product objects into ProductCreateEvent instances,
 * which can be sent to Kafka or used in other messaging scenarios.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Transforms a Product entity into a ProductCreateEvent.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This class provides a utility method to create a ProductCreateEvent from a Product entity.
 * It is intended to be used in services or handlers where product data needs to be published or transferred as an event.
 * </p>
 *
 * @see ProductCreateEvent
 * @see Product
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
public class ProductProcessor {

    /**
     * Converts a Product entity into a ProductCreateEvent.
     *
     * @param product the Product entity to convert
     * @return the corresponding ProductCreateEvent
     */
    public ProductCreateEvent getProductCreateEvent(Product product) {
        return new ProductCreateEvent
                (
                        product.getId(),
                        product.getName(),
                        product.getQuantity(),
                        product.getPrice()
                );
    }
}
