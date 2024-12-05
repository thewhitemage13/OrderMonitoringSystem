package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.UserStatistic;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing {@link UserStatistic} entities.
 * <p>
 * This interface provides methods for accessing and manipulating user statistics stored in the database.
 * </p>
 *
 * @see UserStatistic
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface UserStatisticRepository extends JpaRepository<UserStatistic, Long> {

    /**
     * Finds the {@link UserStatistic} by the specified creation date.
     * <p>
     * This method retrieves the user statistic for a given creation date.
     * </p>
     *
     * @param createdDate the creation date to search for
     * @return the {@link UserStatistic} for the specified creation date
     */
    UserStatistic findByCreatedDate(LocalDate createdDate);

    /**
     * Retrieves an {@link Optional} containing the {@link UserStatistic} for the specified creation date.
     * <p>
     * This method is similar to {@link #findByCreatedDate(LocalDate)}, but it returns an {@link Optional}
     * to explicitly handle the case where no statistic is found for the given creation date.
     * </p>
     *
     * @param createdDate the creation date to search for
     * @return an {@link Optional} containing the {@link UserStatistic} for the specified creation date, or empty if not found
     */
    Optional<UserStatistic> getByCreatedDate(LocalDate createdDate);
}
