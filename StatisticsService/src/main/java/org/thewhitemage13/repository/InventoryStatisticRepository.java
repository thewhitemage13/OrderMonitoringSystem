package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.InventoryStatistic;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryStatisticRepository extends JpaRepository<InventoryStatistic, Long> {
    Optional<List<InventoryStatistic>> findAllByMessage(String message);
}
