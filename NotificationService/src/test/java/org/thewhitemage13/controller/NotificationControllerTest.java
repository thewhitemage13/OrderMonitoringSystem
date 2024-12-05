package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.dto.NotificationDTO;
import org.thewhitemage13.exception.NotificationNotFoundException;
import org.thewhitemage13.service.NotificationService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private NotificationController notificationController;

    @Test
    void testUpdateNotification_Success() throws NotificationNotFoundException {
        // Arrange
        Long notificationId = 1L;
        boolean status = true;
        doNothing().when(notificationService).updateStatus(notificationId, status);

        // Act
        ResponseEntity<String> response = notificationController.updateNotification(notificationId, status);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Notification updated", response.getBody());
        verify(notificationService, times(1)).updateStatus(notificationId, status);
    }

    @Test
    void testUpdateNotification_NotFound() throws NotificationNotFoundException {
        // Arrange
        Long notificationId = 1L;
        boolean status = false;
        doThrow(new NotificationNotFoundException("Notification not found"))
                .when(notificationService).updateStatus(notificationId, status);

        // Act
        ResponseEntity<String> response = notificationController.updateNotification(notificationId, status);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Notification with id = 1 not found", response.getBody());
        verify(notificationService, times(1)).updateStatus(notificationId, status);
    }

    @Test
    void testUpdateNotification_InternalError() throws NotificationNotFoundException {
        // Arrange
        Long notificationId = 1L;
        boolean status = true;
        doThrow(new RuntimeException("Unexpected error")).when(notificationService).updateStatus(notificationId, status);

        // Act
        ResponseEntity<String> response = notificationController.updateNotification(notificationId, status);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("An error occurred"));
        verify(notificationService, times(1)).updateStatus(notificationId, status);
    }

    @Test
    void testGetNotificationByUserId_Success() throws NotificationNotFoundException {
        // Arrange
        Long userId = 1L;
        List<NotificationDTO> notifications = Arrays.asList(
                new NotificationDTO(1L, "Test notification 1", true, LocalDateTime.now()),
                new NotificationDTO(2L, "Test notification 2", false, LocalDateTime.now())
        );
        when(notificationService.getNotificationsByUserId(userId)).thenReturn(notifications);

        // Act
        ResponseEntity<List<NotificationDTO>> response = notificationController.getNotificationByUserId(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(notificationService, times(1)).getNotificationsByUserId(userId);
    }

    @Test
    void testGetNotificationByUserId_NotFound() throws NotificationNotFoundException {
        // Arrange
        Long userId = 1L;
        when(notificationService.getNotificationsByUserId(userId))
                .thenThrow(new NotificationNotFoundException("No notifications found"));

        // Act
        ResponseEntity<List<NotificationDTO>> response = notificationController.getNotificationByUserId(userId);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(notificationService, times(1)).getNotificationsByUserId(userId);
    }

    @Test
    void testGetNotificationByUserId_InternalError() throws NotificationNotFoundException {
        // Arrange
        Long userId = 1L;
        when(notificationService.getNotificationsByUserId(userId))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act
        ResponseEntity<List<NotificationDTO>> response = notificationController.getNotificationByUserId(userId);

        // Assert
        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(notificationService, times(1)).getNotificationsByUserId(userId);
    }
}