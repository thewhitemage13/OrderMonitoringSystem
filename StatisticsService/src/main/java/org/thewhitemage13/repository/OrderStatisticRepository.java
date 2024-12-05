package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.OrderStatistic;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing {@link OrderStatistic} entities.
 * <p>
 * This interface provides methods for accessing and manipulating order statistics stored in the database.
 * </p>
 *
 * @see OrderStatistic
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface OrderStatisticRepository extends JpaRepository<OrderStatistic, Long> {

    /**
     * Finds the {@link OrderStatistic} by the specified date.
     * <p>
     * This method retrieves the order statistic for a given date.
     * </p>
     *
     * @param date the date to search for
     * @return the {@link OrderStatistic} for the specified date
     */
    OrderStatistic findByDate(LocalDate date);

    /**
     * Retrieves an {@link Optional} containing the {@link OrderStatistic} for the specified date.
     * <p>
     * This method is similar to {@link #findByDate(LocalDate)}, but it returns an {@link Optional}
     * to explicitly handle the case where no statistic is found for the given date.
     * </p>
     *
     * @param date the date to search for
     * @return an {@link Optional} containing the {@link OrderStatistic} for the specified date, or empty if not found
     */
    Optional<OrderStatistic> getByDate(LocalDate date);
}
