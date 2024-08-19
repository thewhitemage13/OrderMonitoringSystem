package org.thewhitemage13.interfaces;

import org.thewhitemage13.ProductCreateEvent;

public interface UpdateStockEventHandlerInterface {
    void updateStock(ProductCreateEvent productCreateEvent);
}
