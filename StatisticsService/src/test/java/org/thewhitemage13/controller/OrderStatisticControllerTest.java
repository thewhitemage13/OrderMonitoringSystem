package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.entity.OrderStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.service.OrderStatisticService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderStatisticControllerTest {
    @Mock
    private OrderStatisticService orderStatisticService;
    @InjectMocks
    private OrderStatisticController orderStatisticController;

    @Test
    void deleteOrderStatistic_shouldReturn200_whenDeletionIsSuccessful() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);

        // Act
        ResponseEntity<String> response = orderStatisticController.deleteOrderStatistic(date);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deleted order statistic", response.getBody());
        verify(orderStatisticService, times(1)).deleteOrderStatisticByDate(date);
    }

    @Test
    void deleteOrderStatistic_shouldReturn404_whenStatisticsNotFoundExceptionIsThrown() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        doThrow(new StatisticsNotFoundException("Statistics not found"))
                .when(orderStatisticService).deleteOrderStatisticByDate(date);

        // Act
        ResponseEntity<String> response = orderStatisticController.deleteOrderStatistic(date);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Statistics not found", response.getBody());
        verify(orderStatisticService, times(1)).deleteOrderStatisticByDate(date);
    }

    @Test
    void deleteOrderStatistic_shouldReturn500_whenExceptionIsThrown() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        doThrow(new RuntimeException("Internal error"))
                .when(orderStatisticService).deleteOrderStatisticByDate(date);

        // Act
        ResponseEntity<String> response = orderStatisticController.deleteOrderStatistic(date);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal error", response.getBody());
        verify(orderStatisticService, times(1)).deleteOrderStatisticByDate(date);
    }

    @Test
    void getByDate_shouldReturnOrderStatistic_whenStatisticExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        OrderStatistic statistic = new OrderStatistic();
        when(orderStatisticService.getOrderStatisticByDate(date)).thenReturn(statistic);

        // Act
        ResponseEntity<OrderStatistic> response = orderStatisticController.getByDate(date);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(statistic, response.getBody());
        verify(orderStatisticService, times(1)).getOrderStatisticByDate(date);
    }

    @Test
    void getByDate_shouldReturn404_whenStatisticsNotFoundExceptionIsThrown() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        when(orderStatisticService.getOrderStatisticByDate(date))
                .thenThrow(new StatisticsNotFoundException("Statistics not found"));

        // Act
        ResponseEntity<OrderStatistic> response = orderStatisticController.getByDate(date);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(orderStatisticService, times(1)).getOrderStatisticByDate(date);
    }

    @Test
    void getByDate_shouldReturn500_whenExceptionIsThrown() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        when(orderStatisticService.getOrderStatisticByDate(date)).thenThrow(new RuntimeException("Internal error"));

        // Act
        ResponseEntity<OrderStatistic> response = orderStatisticController.getByDate(date);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(orderStatisticService, times(1)).getOrderStatisticByDate(date);
    }

    @Test
    void getAll_shouldReturnListOfOrderStatistics_whenDataExists() {
        // Arrange
        List<OrderStatistic> statistics = Arrays.asList(new OrderStatistic(), new OrderStatistic());
        when(orderStatisticService.getAllOrderStatistics()).thenReturn(statistics);

        // Act
        ResponseEntity<List<OrderStatistic>> response = orderStatisticController.getAll();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(orderStatisticService, times(1)).getAllOrderStatistics();
    }

    @Test
    void getAll_shouldReturn500_whenExceptionIsThrown() {
        // Arrange
        when(orderStatisticService.getAllOrderStatistics()).thenThrow(new RuntimeException("Internal error"));

        // Act
        ResponseEntity<List<OrderStatistic>> response = orderStatisticController.getAll();

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(orderStatisticService, times(1)).getAllOrderStatistics();
    }
}