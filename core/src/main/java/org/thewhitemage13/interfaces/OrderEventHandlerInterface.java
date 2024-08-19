package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;

public interface OrderEventHandlerInterface {
    void update(OrderCreatedEvent orderCreatedEvent);
}
