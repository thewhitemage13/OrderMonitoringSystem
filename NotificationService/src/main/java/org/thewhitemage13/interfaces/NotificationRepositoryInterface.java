package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.dto.NotificationDTO;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.exception.NotificationNotFoundException;

import java.util.List;

public interface NotificationRepositoryInterface {
    void createNotification(Notification notification, OrderCreatedEvent orderCreatedEvent);
    void updateStatus(Long id, boolean status) throws NotificationNotFoundException;
    List<NotificationDTO> getNotificationsByUserId(Long userId) throws NotificationNotFoundException;
}
