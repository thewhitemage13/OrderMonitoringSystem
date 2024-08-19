package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.OrderStatistic;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OrderStatisticRepository extends JpaRepository<OrderStatistic, Long> {
    OrderStatistic findByDate(LocalDate date);
    Optional<OrderStatistic> getByDate(LocalDate date);
}
