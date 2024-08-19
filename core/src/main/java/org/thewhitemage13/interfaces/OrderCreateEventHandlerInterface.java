package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;

public interface OrderCreateEventHandlerInterface {
    void create(OrderCreatedEvent orderCreatedEvent);
}
