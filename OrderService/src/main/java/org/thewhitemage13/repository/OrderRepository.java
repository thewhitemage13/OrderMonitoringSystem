package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
