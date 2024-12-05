package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.interfaces.OrderUpdateEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Kafka event handler for handling {@link OrderCreatedEvent} messages related to order updates.
 * <p>
 * This class listens to the "order.updated" Kafka topic and handles the event by creating a
 * new notification indicating that an order has been updated. It uses the {@link NotificationService}
 * to persist the notification.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Listens to the "order.updated" Kafka topic.</li>
 *     <li>Handles the creation of a notification when an order is updated.</li>
 *     <li>Interacts with the {@link NotificationService} to create and store the notification.</li>
 * </ul>
 *
 * @see KafkaListener
 * @see KafkaHandler
 * @see OrderCreatedEvent
 * @see NotificationService
 * @see Notification
 * @see OrderUpdateEventHandlerInterface
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "order.updated")
public class OrderUpdateEventHandler implements OrderUpdateEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@link OrderUpdateEventHandler} with the specified {@link NotificationService}.
     *
     * @param notificationService the service used to create and manage notifications
     */
    @Autowired
    public OrderUpdateEventHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link OrderCreatedEvent} and creates a new {@link Notification} to indicate
     * that an order has been updated.
     * <p>
     * The notification is then passed to the {@link NotificationService} to be persisted.
     * </p>
     *
     * @param orderCreatedEvent the event representing the update of an order
     */
    @Override
    @KafkaHandler
    public void update(OrderCreatedEvent orderCreatedEvent) {
        Notification notification = new Notification();
        notification.setMessage("Order updated");
        notificationService.createNotification(notification ,orderCreatedEvent);
    }
}
