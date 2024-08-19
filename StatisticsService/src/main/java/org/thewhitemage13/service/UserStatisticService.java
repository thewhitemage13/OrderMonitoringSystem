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

@Transactional
@Service
public class UserStatisticService implements UserStatisticServiceInterface {
    private final UserStatisticRepository userStatisticRepository;

    @Autowired
    public UserStatisticService(UserStatisticRepository userStatisticRepository) {
        this.userStatisticRepository = userStatisticRepository;
    }

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

    @Override
    public void deleteUserStatisticByDate(LocalDate date) {
        UserStatistic delete = userStatisticRepository.getByCreatedDate(date).orElseThrow(() -> new StatisticsNotFoundException("Statistic with id = %s not found".formatted(date)));
        userStatisticRepository.delete(delete);
    }

    @Override
    public UserStatistic getUserStatisticByDate(LocalDate date) {
        return userStatisticRepository.findByCreatedDate(date);
    }

    @Override
    public List<UserStatistic> getAllUserStatistics() {
        return userStatisticRepository.findAll();
    }
}
