package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.repository.InventoryStatisticRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryStatisticServiceTest {
    @Mock
    private InventoryStatisticRepository inventoryStatisticRepository;
    @InjectMocks
    private InventoryStatisticService inventoryStatisticService;

    @Test
    void createInventoryStatistic_shouldSaveStatistic() {
        // Arrange
        InventoryStatistic inventoryStatistic = new InventoryStatistic();
        ProductCreateEvent event = new ProductCreateEvent();
        event.setName("Product");
        event.setQuantity(10L);
        event.setId(1L);

        // Act
        inventoryStatisticService.createInventoryStatistic(inventoryStatistic, event);

        // Assert
        assertEquals("Product", inventoryStatistic.getItem());
        assertEquals(10, inventoryStatistic.getQuantity());
        assertEquals(1L, inventoryStatistic.getProductId());
        verify(inventoryStatisticRepository, times(1)).save(inventoryStatistic);
    }

    @Test
    void getAllInventoryStatisticsByMessage_shouldReturnStatistics_whenMessageExists() {
        // Arrange
        String message = "Test Message";
        List<InventoryStatistic> statistics = new ArrayList<>();
        statistics.add(new InventoryStatistic());
        when(inventoryStatisticRepository.findAllByMessage(message)).thenReturn(Optional.of(statistics));

        // Act
        List<InventoryStatistic> result = inventoryStatisticService.getAllInventoryStatisticsByMessage(message);

        // Assert
        assertEquals(1, result.size());
        verify(inventoryStatisticRepository, times(1)).findAllByMessage(message);
    }

    @Test
    void getAllInventoryStatisticsByMessage_shouldThrowException_whenMessageNotFound() {
        // Arrange
        String message = "Nonexistent Message";
        when(inventoryStatisticRepository.findAllByMessage(message)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticsNotFoundException exception = assertThrows(StatisticsNotFoundException.class,
                () -> inventoryStatisticService.getAllInventoryStatisticsByMessage(message));
        assertEquals("Statistic with massage = Nonexistent Message not found", exception.getMessage());
        verify(inventoryStatisticRepository, times(1)).findAllByMessage(message);
    }

    @Test
    void getAllInventoryStatistics_shouldReturnAllStatistics() {
        // Arrange
        List<InventoryStatistic> statistics = List.of(new InventoryStatistic(), new InventoryStatistic());
        when(inventoryStatisticRepository.findAll()).thenReturn(statistics);

        // Act
        List<InventoryStatistic> result = inventoryStatisticService.getAllInventoryStatistics();

        // Assert
        assertEquals(2, result.size());
        verify(inventoryStatisticRepository, times(1)).findAll();
    }

    @Test
    void deleteInventoryStatisticById_shouldDeleteStatistic_whenIdExists() {
        // Arrange
        Long id = 1L;
        InventoryStatistic statistic = new InventoryStatistic();
        when(inventoryStatisticRepository.findById(id)).thenReturn(Optional.of(statistic));

        // Act
        inventoryStatisticService.deleteInventoryStatisticById(id);

        // Assert
        verify(inventoryStatisticRepository, times(1)).findById(id);
        verify(inventoryStatisticRepository, times(1)).delete(statistic);
    }

    @Test
    void deleteInventoryStatisticById_shouldThrowException_whenIdNotFound() {
        // Arrange
        Long id = 1L;
        when(inventoryStatisticRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticsNotFoundException exception = assertThrows(StatisticsNotFoundException.class,
                () -> inventoryStatisticService.deleteInventoryStatisticById(id));
        assertEquals("Statistic with id = 1 not found", exception.getMessage());
        verify(inventoryStatisticRepository, times(1)).findById(id);
        verify(inventoryStatisticRepository, times(0)).delete(any());
    }
}