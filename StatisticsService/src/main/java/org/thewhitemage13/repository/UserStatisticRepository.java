package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.UserStatistic;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserStatisticRepository extends JpaRepository<UserStatistic, Long> {
    UserStatistic findByCreatedDate(LocalDate createdDate);
    Optional<UserStatistic> getByCreatedDate(LocalDate createdDate);
}
