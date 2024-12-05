package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.Order;

/**
 * The {@code OrderRepository} interface provides CRUD operations for the {@link Order} entity.
 * <p>
 * This repository extends the {@link JpaRepository} interface, which provides methods
 * for saving, deleting, and retrieving {@link Order} entities.
 * </p>
 * <p>
 * The repository does not require implementation, as {@link JpaRepository} provides
 * the implementation of these methods at runtime.
 * </p>
 *
 * @see JpaRepository
 * @see Order
 * @version 1.0.0
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
