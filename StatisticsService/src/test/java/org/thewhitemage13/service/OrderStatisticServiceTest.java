package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.entity.OrderStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.repository.OrderStatisticRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderStatisticServiceTest {
    @Mock
    private OrderStatisticRepository orderStatisticRepository;
    @InjectMocks
    private OrderStatisticService orderStatisticService;

    @Test
    void createOrderStatistic_shouldCreateNewStatistic_whenStatisticNotExists() {
        // Arrange
        LocalDate now = LocalDate.now();
        OrderCreatedEvent event = new OrderCreatedEvent();


        when(orderStatisticRepository.findByDate(now)).thenReturn(null);

        // Act
        orderStatisticService.createOrderStatistic(event);

        // Assert
        verify(orderStatisticRepository, times(1)).save(Mockito.any(OrderStatistic.class));
    }

//    @Test
//    void createOrderStatistic_shouldUpdateExistingStatistic_whenStatisticExists() {
//        // Arrange
//        LocalDate now = LocalDate.now();
//        OrderCreatedEvent event = new OrderCreatedEvent();
//        event
//        OrderStatistic existingStatistic = new OrderStatistic();
//        existingStatistic.setDate(now);
//        existingStatistic.setTotalOrders(1L);
//        existingStatistic.setParcelsInTransit(1L);
//        existingStatistic.setTotalRevenue(BigDecimal.ONE);
//        when(orderStatisticRepository.findByDate(now)).thenReturn(existingStatistic);
//
//        // Act
//        orderStatisticService.createOrderStatistic(event);
//
//        // Assert
//        assertEquals(2, existingStatistic.getTotalOrders());
//        assertEquals(2, existingStatistic.getParcelsInTransit());
//        assertEquals(BigDecimal.ONE, existingStatistic.getTotalRevenue());
//        verify(orderStatisticRepository, times(1)).save(existingStatistic);
//    }

    @Test
    void updateOrderStatistic_shouldCreateNewStatistic_whenStatisticNotExists() {
        // Arrange
        LocalDate now = LocalDate.now();
        OrderCreatedEvent event = new OrderCreatedEvent();
        when(orderStatisticRepository.findByDate(now)).thenReturn(null);

        // Act
        orderStatisticService.updateOrderStatistic(event);

        // Assert
        verify(orderStatisticRepository, times(1)).save(Mockito.any(OrderStatistic.class));
    }

    @Test
    void updateOrderStatistic_shouldUpdateExistingStatistic_whenStatisticExists() {
        // Arrange
        LocalDate now = LocalDate.now();
        OrderCreatedEvent event = new OrderCreatedEvent();
        OrderStatistic existingStatistic = new OrderStatistic();
        existingStatistic.setDate(now);
        existingStatistic.setParcelsInTransit(5L);
        existingStatistic.setPackagesReceived(2L);
        when(orderStatisticRepository.findByDate(now)).thenReturn(existingStatistic);

        // Act
        orderStatisticService.updateOrderStatistic(event);

        // Assert
        assertEquals(4, existingStatistic.getParcelsInTransit());
        assertEquals(3, existingStatistic.getPackagesReceived());
        verify(orderStatisticRepository, times(1)).save(existingStatistic);
    }

    @Test
    void deleteOrderStatisticByDate_shouldDeleteStatistic_whenStatisticExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        OrderStatistic statistic = new OrderStatistic();
        when(orderStatisticRepository.getByDate(date)).thenReturn(Optional.of(statistic));

        // Act
        orderStatisticService.deleteOrderStatisticByDate(date);

        // Assert
        verify(orderStatisticRepository, times(1)).delete(statistic);
    }

    @Test
    void deleteOrderStatisticByDate_shouldThrowException_whenStatisticNotExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        when(orderStatisticRepository.getByDate(date)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticsNotFoundException exception = assertThrows(StatisticsNotFoundException.class,
                () -> orderStatisticService.deleteOrderStatisticByDate(date));
        assertEquals("Statistic with date = 2023-12-01 not found", exception.getMessage());
    }

    @Test
    void getOrderStatisticByDate_shouldReturnStatistic_whenStatisticExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        OrderStatistic statistic = new OrderStatistic();
        when(orderStatisticRepository.getByDate(date)).thenReturn(Optional.of(statistic));

        // Act
        OrderStatistic result = orderStatisticService.getOrderStatisticByDate(date);

        // Assert
        assertNotNull(result);
        assertEquals(statistic, result);
    }

    @Test
    void getOrderStatisticByDate_shouldThrowException_whenStatisticNotExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        when(orderStatisticRepository.getByDate(date)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticsNotFoundException exception = assertThrows(StatisticsNotFoundException.class,
                () -> orderStatisticService.getOrderStatisticByDate(date));
        assertEquals("Statistic with date = 2023-12-01 not found", exception.getMessage());
    }

    @Test
    void getAllOrderStatistics_shouldReturnAllStatistics() {
        // Arrange
        List<OrderStatistic> statistics = List.of(new OrderStatistic(), new OrderStatistic());
        when(orderStatisticRepository.findAll()).thenReturn(statistics);

        // Act
        List<OrderStatistic> result = orderStatisticService.getAllOrderStatistics();

        // Assert
        assertEquals(2, result.size());
        verify(orderStatisticRepository, times(1)).findAll();
    }
}