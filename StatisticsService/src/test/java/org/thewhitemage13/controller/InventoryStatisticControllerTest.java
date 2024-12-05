package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.service.InventoryStatisticService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryStatisticControllerTest {
    @Mock
    private InventoryStatisticService inventoryStatisticService;
    @InjectMocks
    private InventoryStatisticController inventoryStatisticController;

    @Test
    void getAllInventoryStatisticsByMessage_shouldReturnList_whenServiceReturnsData() {
        String message = "testMessage";
        List<InventoryStatistic> statistics = Arrays.asList(new InventoryStatistic(), new InventoryStatistic());
        when(inventoryStatisticService.getAllInventoryStatisticsByMessage(message)).thenReturn(statistics);

        ResponseEntity<List<InventoryStatistic>> response = inventoryStatisticController.getAllInventoryStatisticsByMessage(message);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(inventoryStatisticService, times(1)).getAllInventoryStatisticsByMessage(message);
    }

    @Test
    void getAllInventoryStatisticsByMessage_shouldReturn404_whenStatisticsNotFoundExceptionIsThrown() {
        String message = "nonexistentMessage";
        when(inventoryStatisticService.getAllInventoryStatisticsByMessage(message))
                .thenThrow(new StatisticsNotFoundException("Statistics not found"));

        ResponseEntity<List<InventoryStatistic>> response = inventoryStatisticController.getAllInventoryStatisticsByMessage(message);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(inventoryStatisticService, times(1)).getAllInventoryStatisticsByMessage(message);
    }

    @Test
    void getAllInventoryStatistics_shouldReturnList_whenServiceReturnsData() {
        List<InventoryStatistic> statistics = Arrays.asList(new InventoryStatistic(), new InventoryStatistic());
        when(inventoryStatisticService.getAllInventoryStatistics()).thenReturn(statistics);

        ResponseEntity<List<InventoryStatistic>> response = inventoryStatisticController.getAllInventoryStatistics();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(inventoryStatisticService, times(1)).getAllInventoryStatistics();
    }

    @Test
    void getAllInventoryStatistics_shouldReturn500_whenExceptionIsThrown() {
        when(inventoryStatisticService.getAllInventoryStatistics()).thenThrow(new RuntimeException("Internal error"));

        ResponseEntity<List<InventoryStatistic>> response = inventoryStatisticController.getAllInventoryStatistics();

        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(inventoryStatisticService, times(1)).getAllInventoryStatistics();
    }

    @Test
    void deleteInventoryStatisticById_shouldReturn200_whenDeletionIsSuccessful() {
        Long statisticId = 1L;

        ResponseEntity<String> response = inventoryStatisticController.deleteInventoryStatisticById(statisticId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Successfully deleted statistic", response.getBody());
        verify(inventoryStatisticService, times(1)).deleteInventoryStatisticById(statisticId);
    }

    @Test
    void deleteInventoryStatisticById_shouldReturn404_whenStatisticsNotFoundExceptionIsThrown() {
        Long statisticId = 1L;
        doThrow(new StatisticsNotFoundException("Statistics not found"))
                .when(inventoryStatisticService).deleteInventoryStatisticById(statisticId);

        ResponseEntity<String> response = inventoryStatisticController.deleteInventoryStatisticById(statisticId);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Statistics with id = 1 not found", response.getBody());
        verify(inventoryStatisticService, times(1)).deleteInventoryStatisticById(statisticId);
    }

    @Test
    void deleteInventoryStatisticById_shouldReturn500_whenExceptionIsThrown() {
        Long statisticId = 1L;
        doThrow(new RuntimeException("Internal error"))
                .when(inventoryStatisticService).deleteInventoryStatisticById(statisticId);

        ResponseEntity<String> response = inventoryStatisticController.deleteInventoryStatisticById(statisticId);

        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(inventoryStatisticService, times(1)).deleteInventoryStatisticById(statisticId);
    }
}