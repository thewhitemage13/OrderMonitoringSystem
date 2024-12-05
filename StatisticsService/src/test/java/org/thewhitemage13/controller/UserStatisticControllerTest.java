package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.entity.UserStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.service.UserStatisticService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserStatisticControllerTest {
    @Mock
    private UserStatisticService userStatisticService;
    @InjectMocks
    private UserStatisticController userStatisticController;

    @Test
    void getAll_shouldReturnListOfUserStatistics_whenDataExists() {
        // Arrange
        List<UserStatistic> statistics = Arrays.asList(new UserStatistic(), new UserStatistic());
        when(userStatisticService.getAllUserStatistics()).thenReturn(statistics);

        // Act
        ResponseEntity<List<UserStatistic>> response = userStatisticController.getAll();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(userStatisticService, times(1)).getAllUserStatistics();
    }

    @Test
    void getAll_shouldReturn500_whenExceptionIsThrown() {
        // Arrange
        when(userStatisticService.getAllUserStatistics()).thenThrow(new RuntimeException("Internal error"));

        // Act
        ResponseEntity<List<UserStatistic>> response = userStatisticController.getAll();

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userStatisticService, times(1)).getAllUserStatistics();
    }

    @Test
    void getByDate_shouldReturnUserStatistic_whenStatisticExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        UserStatistic statistic = new UserStatistic();
        when(userStatisticService.getUserStatisticByDate(date)).thenReturn(statistic);

        // Act
        ResponseEntity<UserStatistic> response = userStatisticController.getByDate(date);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(statistic, response.getBody());
        verify(userStatisticService, times(1)).getUserStatisticByDate(date);
    }

    @Test
    void getByDate_shouldReturn404_whenStatisticsNotFoundExceptionIsThrown() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        when(userStatisticService.getUserStatisticByDate(date))
                .thenThrow(new StatisticsNotFoundException("Statistics not found"));

        // Act
        ResponseEntity<UserStatistic> response = userStatisticController.getByDate(date);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userStatisticService, times(1)).getUserStatisticByDate(date);
    }

    @Test
    void getByDate_shouldReturn500_whenExceptionIsThrown() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        when(userStatisticService.getUserStatisticByDate(date)).thenThrow(new RuntimeException("Internal error"));

        // Act
        ResponseEntity<UserStatistic> response = userStatisticController.getByDate(date);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userStatisticService, times(1)).getUserStatisticByDate(date);
    }

    @Test
    void deleteUserStatisticByDate_shouldReturn200_whenDeletionIsSuccessful() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);

        // Act
        ResponseEntity<String> response = userStatisticController.deleteUserStatisticByDate(date);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deleted user statistic", response.getBody());
        verify(userStatisticService, times(1)).deleteUserStatisticByDate(date);
    }

    @Test
    void deleteUserStatisticByDate_shouldReturn404_whenStatisticsNotFoundExceptionIsThrown() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        doThrow(new StatisticsNotFoundException("User with date = " + date + " not found"))
                .when(userStatisticService).deleteUserStatisticByDate(date);

        // Act
        ResponseEntity<String> response = userStatisticController.deleteUserStatisticByDate(date);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User with date = 2023-12-01 not found", response.getBody());
        verify(userStatisticService, times(1)).deleteUserStatisticByDate(date);
    }

    @Test
    void deleteUserStatisticByDate_shouldReturn500_whenExceptionIsThrown() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        doThrow(new RuntimeException("Internal error"))
                .when(userStatisticService).deleteUserStatisticByDate(date);

        // Act
        ResponseEntity<String> response = userStatisticController.deleteUserStatisticByDate(date);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal error", response.getBody());
        verify(userStatisticService, times(1)).deleteUserStatisticByDate(date);
    }
}