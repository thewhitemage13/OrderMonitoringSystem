package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.interfaces.OrderCreateEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

@Component
@KafkaListener(topics = "order.created")
public class OrderCreateEventHandler implements OrderCreateEventHandlerInterface {
    private final NotificationService notificationService;

    @Autowired
    public OrderCreateEventHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    @KafkaHandler
    public void create(OrderCreatedEvent orderCreatedEvent) {
        Notification notification = new Notification();
        notification.setMessage("Order created");
        notificationService.createNotification(notification ,orderCreatedEvent);
    }
}
