package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.entity.OrderStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.interfaces.OrderStatisticServiceInterface;
import org.thewhitemage13.repository.OrderStatisticRepository;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class OrderStatisticService implements OrderStatisticServiceInterface {
    private final OrderStatisticRepository orderStatisticRepository;

    @Autowired
    public OrderStatisticService(OrderStatisticRepository orderStatisticRepository) {
        this.orderStatisticRepository = orderStatisticRepository;
    }

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

    @Override
    public void deleteOrderStatisticByDate(LocalDate date) {
        OrderStatistic deleteOrderStatistic = orderStatisticRepository.getByDate(date).orElseThrow(() -> new StatisticsNotFoundException("Statistic with date = %s not found".formatted(date)));
        orderStatisticRepository.delete(deleteOrderStatistic);
    }

    @Override
    public OrderStatistic getOrderStatisticByDate(LocalDate date) {
        return orderStatisticRepository.getByDate(date).orElseThrow(() -> new StatisticsNotFoundException("Statistic with date = %s not found".formatted(date)));
    }

    @Override
    public List<OrderStatistic> getAllOrderStatistics() {
        return orderStatisticRepository.findAll();
    }
}
