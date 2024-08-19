package org.thewhitemage13.interfaces;

import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.entity.OrderStatistic;

import java.time.LocalDate;
import java.util.List;

public interface OrderStatisticServiceInterface {
    void createOrderStatistic(OrderCreatedEvent orderCreatedEvent);
    void updateOrderStatistic(OrderCreatedEvent orderCreatedEvent);
    void deleteOrderStatisticByDate(LocalDate date);
    OrderStatistic getOrderStatisticByDate(LocalDate date);
    List<OrderStatistic> getAllOrderStatistics();
}
