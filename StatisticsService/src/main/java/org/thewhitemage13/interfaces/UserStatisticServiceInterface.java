package org.thewhitemage13.interfaces;

import org.thewhitemage13.entity.UserStatistic;

import java.time.LocalDate;
import java.util.List;

public interface UserStatisticServiceInterface {
    void createUserStatistic();
    void deleteUserStatisticByDate(LocalDate date);
    UserStatistic getUserStatisticByDate(LocalDate date);
    List<UserStatistic> getAllUserStatistics();
}
