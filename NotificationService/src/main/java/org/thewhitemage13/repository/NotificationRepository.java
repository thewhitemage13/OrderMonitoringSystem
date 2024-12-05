package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thewhitemage13.entity.Notification;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Notification} entity.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for interacting with the
 * underlying database for {@link Notification} entities. It includes methods for retrieving
 * notifications based on the user ID.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Provides basic CRUD operations for {@link Notification} entities.</li>
 *     <li>Custom query to fetch all notifications for a specific user by their ID.</li>
 * </ul>
 *
 * @see JpaRepository
 * @see Notification
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Finds all notifications associated with a specific user ID.
     * <p>
     * This method retrieves all notifications for the given user ID. If no notifications are found,
     * it returns an empty {@link Optional}.
     * </p>
     *
     * @param userId the ID of the user whose notifications are to be retrieved
     * @return an {@link Optional} containing a list of {@link Notification} entities for the specified user
     */
    Optional<List<Notification>> findAllByUserId(Long userId);
}
