package org.thewhitemage13.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity class representing a notification in the system.
 * <p>
 * This class maps to the "notifications" table in the database and provides
 * validation constraints for its fields to ensure data integrity.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Represents a notification entity with fields such as user ID, message,
 *     read status, and creation timestamp.</li>
 *     <li>Includes validation constraints for fields to ensure proper values
 *     (e.g., non-null, positive, and size limits).</li>
 *     <li>Uses JPA annotations to map the class and its fields to the database.</li>
 * </ul>
 *
 * @see jakarta.persistence.Entity
 * @see jakarta.validation.constraints
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
@Entity
public class Notification {

    /**
     * The unique identifier for the notification.
     * <p>
     * This is the primary key of the "notifications" table and is automatically
     * generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique identifier of the user associated with this notification.
     * <p>
     * Must be a positive number and cannot be {@code null}.
     * </p>
     */
    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    /**
     * The content of the notification message.
     * <p>
     * Cannot be blank and must not exceed 255 characters in length.
     * </p>
     */
    @NotBlank(message = "Message cannot be blank")
    @Size(max = 255, message = "Message cannot exceed 255 characters")
    private String message;

    /**
     * Indicates whether the notification has been read by the user.
     * <p>
     * A value of {@code true} means the notification is read, while {@code false} means it is unread.
     * </p>
     */
    private boolean read;

    /**
     * The timestamp indicating when the notification was created.
     * <p>
     * This field cannot be {@code null}.
     * </p>
     */
    @NotNull(message = "Created At cannot be null")
    private LocalDateTime createdAt;
}
