package org.thewhitemage13.processor;

import org.springframework.stereotype.Component;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.Product;

@Component
public class ProductProcessor {

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
