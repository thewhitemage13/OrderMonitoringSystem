package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.entity.UserStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.interfaces.UserStatisticServiceInterface;
import org.thewhitemage13.repository.UserStatisticRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing {@link UserStatistic} entities.
 * <p>
 * This class provides methods for creating, retrieving, deleting, and listing user statistics, which track the
 * number of users created on a given date.
 * </p>
 *
 * @see UserStatistic
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Transactional
@Service
public class UserStatisticService implements UserStatisticServiceInterface {
    private final UserStatisticRepository userStatisticRepository;

    /**
     * Constructs a {@link UserStatisticService} with the given {@link UserStatisticRepository}.
     *
     * @param userStatisticRepository the repository for accessing user statistics data
     */
    @Autowired
    public UserStatisticService(UserStatisticRepository userStatisticRepository) {
        this.userStatisticRepository = userStatisticRepository;
    }

    /**
     * Creates or updates the user statistics for the current date.
     * <p>
     * If a statistic already exists for the current date, it will be updated by incrementing the count of users created.
     * If no statistic exists, a new one will be created with an initial value of 1.
     * </p>
     */
    @Override
    public void createUserStatistic() {
        LocalDate now = LocalDate.now();

        UserStatistic getStatistic = userStatisticRepository.findByCreatedDate(now);
        if (getStatistic == null) {
            getStatistic = new UserStatistic();
            getStatistic.setCreatedDate(now);
            getStatistic.setCountOfUserCreated(1L);
        } else {
            getStatistic.setCountOfUserCreated(getStatistic.getCountOfUserCreated() + 1L);
            getStatistic.setCreatedDate(LocalDate.now());
        }

        userStatisticRepository.save(getStatistic);
    }

    /**
     * Deletes the {@link UserStatistic} for the specified date.
     * <p>
     * If no statistic exists for the given date, a {@link StatisticsNotFoundException} is thrown.
     * </p>
     *
     * @param date the date of the user statistic to be deleted
     * @throws StatisticsNotFoundException if no statistic is found for the given date
     */
    @Override
    public void deleteUserStatisticByDate(LocalDate date) {
        UserStatistic delete = userStatisticRepository.getByCreatedDate(date).orElseThrow(() -> new StatisticsNotFoundException("Statistic with id = %s not found".formatted(date)));
        userStatisticRepository.delete(delete);
    }

    /**
     * Retrieves the {@link UserStatistic} for the specified date.
     * <p>
     * If no statistic exists for the given date, a {@link StatisticsNotFoundException} is thrown.
     * </p>
     *
     * @param date the date of the user statistic to be retrieved
     * @return the {@link UserStatistic} for the specified date
     * @throws StatisticsNotFoundException if no statistic is found for the given date
     */
    @Override
    public UserStatistic getUserStatisticByDate(LocalDate date) {
        return userStatisticRepository.findByCreatedDate(date);
    }

    /**
     * Retrieves all {@link UserStatistic} entities.
     *
     * @return a list of all {@link UserStatistic} entities
     */
    @Override
    public List<UserStatistic> getAllUserStatistics() {
        return userStatisticRepository.findAll();
    }
}
