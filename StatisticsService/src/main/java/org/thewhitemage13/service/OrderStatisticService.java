package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.entity.OrderStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.interfaces.OrderStatisticServiceInterface;
import org.thewhitemage13.repository.OrderStatisticRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for managing {@link OrderStatistic} entities.
 * <p>
 * This class provides methods for creating, updating, retrieving, and deleting order statistics. It also supports
 * retrieving all statistics and managing them by date.
 * </p>
 *
 * @see OrderStatistic
 * @see OrderCreatedEvent
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Transactional
@Service
public class OrderStatisticService implements OrderStatisticServiceInterface {
    private final OrderStatisticRepository orderStatisticRepository;

    /**
     * Constructs an {@link OrderStatisticService} with the given {@link OrderStatisticRepository}.
     *
     * @param orderStatisticRepository the repository for accessing order statistics data
     */
    @Autowired
    public OrderStatisticService(OrderStatisticRepository orderStatisticRepository) {
        this.orderStatisticRepository = orderStatisticRepository;
    }

    /**
     * Creates or updates an order statistic based on the provided {@link OrderCreatedEvent}.
     * <p>
     * If a statistic already exists for the current date, it will be updated by incrementing the total number of
     * orders, parcels in transit, and the total revenue. If no statistic exists for the current date, a new one will
     * be created with initial values.
     * </p>
     *
     * @param orderCreatedEvent the {@link OrderCreatedEvent} containing the details for the statistic update
     */
    @Override
    public void createOrderStatistic(OrderCreatedEvent orderCreatedEvent) {
        LocalDate now = LocalDate.now();

        OrderStatistic getStatistic = orderStatisticRepository.findByDate(now);
        if (getStatistic == null) {
            getStatistic = new OrderStatistic();
            getStatistic.setDate(now);
            getStatistic.setTotalOrders(1L);
            getStatistic.setParcelsInTransit(1L);
            getStatistic.setTotalRevenue(orderCreatedEvent.getTotalPrice());
        } else {
            getStatistic.setTotalOrders(getStatistic.getTotalOrders() + 1);
            getStatistic.setParcelsInTransit(getStatistic.getParcelsInTransit() + 1);
            getStatistic.setTotalRevenue(getStatistic.add(orderCreatedEvent.getTotalPrice()));
        }

        orderStatisticRepository.save(getStatistic);
    }

    /**
     * Updates the order statistic for the current date based on the provided {@link OrderCreatedEvent}.
     * <p>
     * If a statistic already exists, the number of parcels in transit is decreased, and the number of packages
     * received is increased. If no statistic exists, a new one is created with an initial value of 1 for packages
     * received.
     * </p>
     *
     * @param orderCreatedEvent the {@link OrderCreatedEvent} containing the details for the statistic update
     */
    @Override
    public void updateOrderStatistic(OrderCreatedEvent orderCreatedEvent) {
        LocalDate now = LocalDate.now();

        OrderStatistic getStatistic = orderStatisticRepository.findByDate(now);

        if (getStatistic == null) {

            getStatistic = new OrderStatistic();
            getStatistic.setDate(now);
            getStatistic.setPackagesReceived(1L);

        } else {
            getStatistic.setParcelsInTransit(getStatistic.getParcelsInTransit() - 1);

            getStatistic.setPackagesReceived(getStatistic.getPackagesReceived() + 1);
        }

        orderStatisticRepository.save(getStatistic);
    }

    /**
     * Deletes the {@link OrderStatistic} for the specified date.
     * <p>
     * If no statistic exists for the given date, a {@link StatisticsNotFoundException} is thrown.
     * </p>
     *
     * @param date the date of the order statistic to be deleted
     * @throws StatisticsNotFoundException if no statistic is found for the given date
     */
    @Override
    public void deleteOrderStatisticByDate(LocalDate date) {
        OrderStatistic deleteOrderStatistic = orderStatisticRepository.getByDate(date).orElseThrow(() -> new StatisticsNotFoundException("Statistic with date = %s not found".formatted(date)));
        orderStatisticRepository.delete(deleteOrderStatistic);
    }

    /**
     * Retrieves the {@link OrderStatistic} for the specified date.
     * <p>
     * If no statistic exists for the given date, a {@link StatisticsNotFoundException} is thrown.
     * </p>
     *
     * @param date the date of the order statistic to be retrieved
     * @return the {@link OrderStatistic} for the specified date
     * @throws StatisticsNotFoundException if no statistic is found for the given date
     */
    @Override
    public OrderStatistic getOrderStatisticByDate(LocalDate date) {
        return orderStatisticRepository.getByDate(date).orElseThrow(() -> new StatisticsNotFoundException("Statistic with date = %s not found".formatted(date)));
    }

    /**
     * Retrieves all {@link OrderStatistic} entities.
     *
     * @return a list of all {@link OrderStatistic} entities
     */
    @Override
    public List<OrderStatistic> getAllOrderStatistics() {
        return orderStatisticRepository.findAll();
    }
}
