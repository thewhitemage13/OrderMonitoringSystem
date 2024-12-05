package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.entity.UserStatistic;
import org.thewhitemage13.exception.StatisticsNotFoundException;
import org.thewhitemage13.repository.UserStatisticRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserStatisticServiceTest {
    @Mock
    private UserStatisticRepository userStatisticRepository;
    @InjectMocks
    private UserStatisticService userStatisticService;

    @Test
    void createUserStatistic_shouldCreateNewStatistic_whenStatisticNotExists() {
        // Arrange
        LocalDate now = LocalDate.now();
        when(userStatisticRepository.findByCreatedDate(now)).thenReturn(null);

        // Act
        userStatisticService.createUserStatistic();

        // Assert
        verify(userStatisticRepository, times(1)).save(Mockito.any(UserStatistic.class));
    }

    @Test
    void createUserStatistic_shouldUpdateExistingStatistic_whenStatisticExists() {
        // Arrange
        LocalDate now = LocalDate.now();
        UserStatistic existingStatistic = new UserStatistic();
        existingStatistic.setCreatedDate(now);
        existingStatistic.setCountOfUserCreated(5L);
        when(userStatisticRepository.findByCreatedDate(now)).thenReturn(existingStatistic);

        // Act
        userStatisticService.createUserStatistic();

        // Assert
        assertEquals(6L, existingStatistic.getCountOfUserCreated());
        verify(userStatisticRepository, times(1)).save(existingStatistic);
    }

    @Test
    void deleteUserStatisticByDate_shouldDeleteStatistic_whenStatisticExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        UserStatistic statistic = new UserStatistic();
        when(userStatisticRepository.getByCreatedDate(date)).thenReturn(Optional.of(statistic));

        // Act
        userStatisticService.deleteUserStatisticByDate(date);

        // Assert
        verify(userStatisticRepository, times(1)).delete(statistic);
    }

    @Test
    void deleteUserStatisticByDate_shouldThrowException_whenStatisticNotExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        when(userStatisticRepository.getByCreatedDate(date)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticsNotFoundException exception = assertThrows(StatisticsNotFoundException.class,
                () -> userStatisticService.deleteUserStatisticByDate(date));
        assertEquals("Statistic with id = 2023-12-01 not found", exception.getMessage());
    }

    @Test
    void getUserStatisticByDate_shouldReturnStatistic_whenStatisticExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        UserStatistic statistic = new UserStatistic();
        when(userStatisticRepository.findByCreatedDate(date)).thenReturn(statistic);

        // Act
        UserStatistic result = userStatisticService.getUserStatisticByDate(date);

        // Assert
        assertNotNull(result);
        assertEquals(statistic, result);
    }

    @Test
    void getUserStatisticByDate_shouldReturnNull_whenStatisticNotExists() {
        // Arrange
        LocalDate date = LocalDate.of(2023, 12, 1);
        when(userStatisticRepository.findByCreatedDate(date)).thenReturn(null);

        // Act
        UserStatistic result = userStatisticService.getUserStatisticByDate(date);

        // Assert
        assertNull(result);
    }

    @Test
    void getAllUserStatistics_shouldReturnAllStatistics() {
        // Arrange
        List<UserStatistic> statistics = List.of(new UserStatistic(), new UserStatistic());
        when(userStatisticRepository.findAll()).thenReturn(statistics);

        // Act
        List<UserStatistic> result = userStatisticService.getAllUserStatistics();

        // Assert
        assertEquals(2, result.size());
        verify(userStatisticRepository, times(1)).findAll();
    }

}