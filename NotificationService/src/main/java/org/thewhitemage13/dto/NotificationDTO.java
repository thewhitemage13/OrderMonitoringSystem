package org.thewhitemage13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for representing user notifications.
 * <p>
 * This class is used to encapsulate notification details for transferring
 * data between different layers of the application, such as the service
 * layer and the client or UI layer.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Contains the user ID associated with the notification.</li>
 *     <li>Stores the notification message and its read status.</li>
 *     <li>Includes the timestamp for when the notification was created.</li>
 * </ul>
 *
 * @see java.time.LocalDateTime
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see lombok.Getter
 * @see lombok.Setter
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationDTO {

    /**
     * The unique identifier of the user associated with the notification.
     */
    private Long userId;

    /**
     * The content of the notification message.
     */
    private String message;

    /**
     * Indicates whether the notification has been read by the user.
     * <p>
     * A value of {@code true} means the notification is read, while
     * {@code false} means it is unread.
     * </p>
     */
    private boolean read;

    /**
     * The timestamp indicating when the notification was created.
     */
    private LocalDateTime createdAt;
}
