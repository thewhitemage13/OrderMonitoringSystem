package org.thewhitemage13.interfaces;

import org.thewhitemage13.entity.UserStatistic;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for managing {@link UserStatistic} objects.
 * <p>
 * This interface defines the core operations for creating, retrieving, and deleting user statistics.
 * </p>
 *
 * @see UserStatistic
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserStatisticServiceInterface {

    /**
     * Creates a new user statistic.
     * <p>
     * This method is used to create a new user statistic, typically after a user is created.
     * </p>
     */
    void createUserStatistic();

    /**
     * Deletes the user statistic for the specified date.
     * <p>
     * This method is used to remove a user statistic for a particular date.
     * </p>
     *
     * @param date the date of the {@link UserStatistic} to be deleted
     */
    void deleteUserStatisticByDate(LocalDate date);

    /**
     * Retrieves the user statistic for the specified date.
     * <p>
     * This method fetches the user statistic for a given date.
     * </p>
     *
     * @param date the date for which the user statistic is to be retrieved
     * @return the {@link UserStatistic} for the specified date
     */
    UserStatistic getUserStatisticByDate(LocalDate date);

    /**
     * Retrieves all user statistics.
     * <p>
     * This method fetches all user statistics stored in the system.
     * </p>
     *
     * @return a list of all {@link UserStatistic} objects
     */
    List<UserStatistic> getAllUserStatistics();
}
