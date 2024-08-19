package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.interfaces.OrderUpdateEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

@Component
@KafkaListener(topics = "order.updated")
public class OrderUpdateEventHandler implements OrderUpdateEventHandlerInterface {
    private final NotificationService notificationService;

    @Autowired
    public OrderUpdateEventHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    @KafkaHandler
    public void update(OrderCreatedEvent orderCreatedEvent) {
        Notification notification = new Notification();
        notification.setMessage("Order updated");
        notificationService.createNotification(notification ,orderCreatedEvent);
    }
}
