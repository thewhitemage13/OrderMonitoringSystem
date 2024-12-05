package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.dto.NotificationDTO;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.exception.NotificationNotFoundException;
import org.thewhitemage13.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;
    @InjectMocks
    private NotificationService notificationService;

    @Test
    void testCreateNotification_Success() {
        // Arrange
        Notification notification = new Notification();
        notification.setMessage("Order created successfully");
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setUserId(1L);

        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        // Act
        notificationService.createNotification(notification, event);

        // Assert
        assertFalse(notification.isRead());
        assertEquals(1L, notification.getUserId());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testUpdateStatus_Success() throws NotificationNotFoundException {
        // Arrange
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setRead(false);

        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(notification));

        // Act
        notificationService.updateStatus(1L, true);

        // Assert
        assertTrue(notification.isRead());
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void testUpdateStatus_NotificationNotFound() {
        // Arrange
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        NotificationNotFoundException exception = assertThrows(
                NotificationNotFoundException.class,
                () -> notificationService.updateStatus(1L, true)
        );
        assertEquals("Notification with id = 1 not found", exception.getMessage());
        verify(notificationRepository, times(1)).findById(1L);
        verify(notificationRepository, never()).save(any(Notification.class));
    }

    @Test
    void testGetNotificationsByUserId_Success() throws NotificationNotFoundException {
        // Arrange
        Notification notification1 = new Notification();
        notification1.setUserId(1L);
        notification1.setMessage("Order created");
        notification1.setRead(false);
        notification1.setCreatedAt(LocalDateTime.now());

        Notification notification2 = new Notification();
        notification2.setUserId(1L);
        notification2.setMessage("Order shipped");
        notification2.setRead(true);
        notification2.setCreatedAt(LocalDateTime.now());

        List<Notification> notifications = List.of(notification1, notification2);
        when(notificationRepository.findAllByUserId(anyLong())).thenReturn(Optional.of(notifications));

        // Act
        List<NotificationDTO> result = notificationService.getNotificationsByUserId(1L);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Order created", result.get(0).getMessage());
        assertEquals("Order shipped", result.get(1).getMessage());
        verify(notificationRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void testGetNotificationsByUserId_NotificationNotFound() {
        // Arrange
        when(notificationRepository.findAllByUserId(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        NotificationNotFoundException exception = assertThrows(
                NotificationNotFoundException.class,
                () -> notificationService.getNotificationsByUserId(1L)
        );
        assertEquals("Notifications for user with id = 1 is not found", exception.getMessage());
        verify(notificationRepository, times(1)).findAllByUserId(1L);
    }

}