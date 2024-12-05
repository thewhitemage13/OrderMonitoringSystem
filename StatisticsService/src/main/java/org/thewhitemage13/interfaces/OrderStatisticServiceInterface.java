package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.entity.OrderStatistic;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for managing {@link OrderStatistic} objects.
 * <p>
 * This interface defines the core operations for creating, updating, retrieving, and deleting order statistics.
 * </p>
 *
 * @see OrderStatistic
 * @see OrderCreatedEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface OrderStatisticServiceInterface {

    /**
     * Creates a new order statistic based on the provided {@link OrderCreatedEvent}.
     * <p>
     * This method is used to create an order statistic when a new order is placed.
     * </p>
     *
     * @param orderCreatedEvent the {@link OrderCreatedEvent} containing the details of the newly created order
     */
    void createOrderStatistic(OrderCreatedEvent orderCreatedEvent);

    /**
     * Updates an existing order statistic based on the provided {@link OrderCreatedEvent}.
     * <p>
     * This method is used to update an order statistic when an existing order is modified.
     * </p>
     *
     * @param orderCreatedEvent the {@link OrderCreatedEvent} containing the updated details of the order
     */
    void updateOrderStatistic(OrderCreatedEvent orderCreatedEvent);

    /**
     * Deletes the order statistic for the specified date.
     * <p>
     * This method is used to remove an order statistic based on its associated date.
     * </p>
     *
     * @param date the date of the {@link OrderStatistic} to be deleted
     */
    void deleteOrderStatisticByDate(LocalDate date);

    /**
     * Retrieves the order statistic for the specified date.
     * <p>
     * This method fetches the order statistic associated with a particular date.
     * </p>
     *
     * @param date the date for which the order statistic is to be retrieved
     * @return the {@link OrderStatistic} for the specified date
     */
    OrderStatistic getOrderStatisticByDate(LocalDate date);

    /**
     * Retrieves all order statistics.
     * <p>
     * This method fetches all order statistics stored in the system.
     * </p>
     *
     * @return a list of all {@link OrderStatistic} objects
     */
    List<OrderStatistic> getAllOrderStatistics();
}
