package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.dto.NotificationDTO;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.exception.NotificationNotFoundException;
import org.thewhitemage13.interfaces.NotificationRepositoryInterface;
import org.thewhitemage13.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class NotificationService implements NotificationRepositoryInterface {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void createNotification(Notification notification, OrderCreatedEvent orderCreatedEvent) {
        notification.setRead(false);
        notification.setUserId(orderCreatedEvent.getUserId());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setMessage(notification.getMessage());
        notificationRepository.save(notification);
    }

    @Override
    public void updateStatus(Long notificationId, boolean status) throws NotificationNotFoundException {
        Notification update = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with id = %s not found".formatted(notificationId)));
        update.setRead(status);
        notificationRepository.save(update);
    }

    @Override
    public List<NotificationDTO> getNotificationsByUserId(Long userId) throws NotificationNotFoundException {
        List<Notification> notifications =
                notificationRepository.findAllByUserId(userId)
                        .orElseThrow(() -> new NotificationNotFoundException("Notifications for user with id = %s is not found".formatted(userId)));
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setUserId(notification.getUserId());
            notificationDTO.setMessage(notification.getMessage());
            notificationDTO.setRead(notification.isRead());
            notificationDTO.setCreatedAt(notification.getCreatedAt());
            notificationDTOS.add(notificationDTO);
        }

        return notificationDTOS;
    }

}
