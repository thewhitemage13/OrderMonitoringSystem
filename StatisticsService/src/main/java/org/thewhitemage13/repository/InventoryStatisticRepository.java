package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.InventoryStatistic;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link InventoryStatistic} entities.
 * <p>
 * This interface provides the basic CRUD operations and custom query methods for accessing
 * and manipulating inventory statistics stored in the database.
 * </p>
 *
 * @see InventoryStatistic
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface InventoryStatisticRepository extends JpaRepository<InventoryStatistic, Long> {

    /**
     * Finds all inventory statistics by a specific message.
     * <p>
     * This method retrieves all {@link InventoryStatistic} entities with the specified message.
     * </p>
     *
     * @param message the message to filter the inventory statistics by
     * @return an {@link Optional} containing a list of {@link InventoryStatistic} objects that match the message,
     *         or an empty {@link Optional} if no statistics are found
     */
    Optional<List<InventoryStatistic>> findAllByMessage(String message);
}
