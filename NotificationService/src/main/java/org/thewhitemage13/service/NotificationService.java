package org.thewhitemage13.service;

import jakarta.validation.Valid;
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

/**
 * Service class for managing notifications.
 * <p>
 * This class implements the {@link NotificationRepositoryInterface} and provides methods for creating, updating, and retrieving
 * notifications. It is responsible for the business logic related to notifications, including setting the status of notifications
 * and mapping entity objects to data transfer objects (DTOs).
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Creates a notification when an order is created, with details like user ID and message.</li>
 *     <li>Updates the read status of a notification.</li>
 *     <li>Retrieves a list of notifications for a specific user, mapping them to {@link NotificationDTO} objects.</li>
 * </ul>
 *
 * @see NotificationRepositoryInterface
 * @see NotificationRepository
 * @see Notification
 * @see NotificationDTO
 * @see NotificationNotFoundException
 * @see OrderCreatedEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Transactional
@Service
public class NotificationService implements NotificationRepositoryInterface {
    private final NotificationRepository notificationRepository;

    /**
     * Constructs a {@link NotificationService} instance with the provided {@link NotificationRepository}.
     * <p>
     * The constructor injects the {@link NotificationRepository} for interacting with the database.
     * </p>
     *
     * @param notificationRepository the {@link NotificationRepository} for accessing notification data
     */
    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Creates a new notification based on the provided {@link OrderCreatedEvent}.
     * <p>
     * This method sets the notification's read status to false, associates it with the user ID from the
     * {@link OrderCreatedEvent}, and saves it to the database.
     * </p>
     *
     * @param notification the {@link Notification} object to be created
     * @param orderCreatedEvent the {@link OrderCreatedEvent} containing the order details
     */
    @Override
    public void createNotification(@Valid Notification notification, OrderCreatedEvent orderCreatedEvent) {
        notification.setRead(false);
        notification.setUserId(orderCreatedEvent.getUserId());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setMessage(notification.getMessage());
        notificationRepository.save(notification);
    }

    /**
     * Updates the read status of a notification.
     * <p>
     * This method updates the "read" status of a notification based on its ID. If the notification is not found,
     * a {@link NotificationNotFoundException} is thrown.
     * </p>
     *
     * @param notificationId the ID of the notification to update
     * @param status the new read status of the notification
     * @throws NotificationNotFoundException if no notification with the specified ID is found
     */
    @Override
    public void updateStatus(Long notificationId, boolean status) throws NotificationNotFoundException {
        Notification update = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with id = %s not found".formatted(notificationId)));
        update.setRead(status);
        notificationRepository.save(update);
    }

    /**
     * Retrieves all notifications associated with a specific user ID.
     * <p>
     * This method retrieves a list of {@link NotificationDTO} objects representing notifications for the user.
     * If no notifications are found for the user, a {@link NotificationNotFoundException} is thrown.
     * </p>
     *
     * @param userId the ID of the user whose notifications are to be retrieved
     * @return a list of {@link NotificationDTO} objects representing the user's notifications
     * @throws NotificationNotFoundException if no notifications are found for the specified user
     */
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
