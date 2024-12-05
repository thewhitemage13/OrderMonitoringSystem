package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.dto.NotificationDTO;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.exception.NotificationNotFoundException;

import java.util.List;

/**
 * Interface for managing notifications in the system.
 * <p>
 * This interface defines methods for creating, updating, and retrieving notifications.
 * It supports operations such as creating a notification when an order is created,
 * updating the status of a notification, and fetching notifications based on a user ID.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Creating a new notification when an order is created.</li>
 *     <li>Updating the read status of a notification.</li>
 *     <li>Retrieving a list of notifications for a specific user.</li>
 * </ul>
 *
 * @see Notification
 * @see NotificationDTO
 * @see OrderCreatedEvent
 * @see NotificationNotFoundException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface NotificationRepositoryInterface {

    /**
     * Creates a new notification based on the provided {@link OrderCreatedEvent}.
     * <p>
     * This method creates a notification that is associated with an order creation event.
     * </p>
     *
     * @param notification the {@link Notification} object to be created
     * @param orderCreatedEvent the {@link OrderCreatedEvent} containing order details
     */
    void createNotification(Notification notification, OrderCreatedEvent orderCreatedEvent);

    /**
     * Updates the read status of a notification.
     * <p>
     * This method updates the "read" status of a notification identified by its ID.
     * If no notification with the given ID is found, a {@link NotificationNotFoundException} is thrown.
     * </p>
     *
     * @param id the ID of the notification to be updated
     * @param status the new status to set for the notification (true for read, false for unread)
     * @throws NotificationNotFoundException if no notification with the specified ID is found
     */
    void updateStatus(Long id, boolean status) throws NotificationNotFoundException;

    /**
     * Retrieves a list of notifications associated with a specific user ID.
     * <p>
     * This method fetches all notifications for the given user. If no notifications are found,
     * a {@link NotificationNotFoundException} is thrown.
     * </p>
     *
     * @param userId the ID of the user whose notifications are to be retrieved
     * @return a list of {@link NotificationDTO} objects containing the user's notifications
     * @throws NotificationNotFoundException if no notifications are found for the specified user
     */
    List<NotificationDTO> getNotificationsByUserId(Long userId) throws NotificationNotFoundException;
}
