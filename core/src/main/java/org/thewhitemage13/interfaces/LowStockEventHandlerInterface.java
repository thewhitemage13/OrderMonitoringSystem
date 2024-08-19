package org.thewhitemage13.interfaces;

import org.thewhitemage13.ProductCreateEvent;

public interface LowStockEventHandlerInterface {
    void lowStock(ProductCreateEvent productCreateEvent);
}
