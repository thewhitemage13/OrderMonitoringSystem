package org.thewhitemage13.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.service.NotificationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderCreateEventHandlerTest {
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private OrderCreateEventHandler handler;

    @Test
    void testCreate_Success() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setUserId(1L);

        // Act
        handler.create(event);

        // Assert
        verify(notificationService, times(1)).createNotification(any(Notification.class), eq(event));
    }

    @Test
    void testCreate_ExceptionHandling() {
        // Arrange
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setUserId(1L);

        doThrow(new RuntimeException("Error creating notification"))
                .when(notificationService)
                .createNotification(any(Notification.class), eq(event));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> handler.create(event));
        assertEquals("Error creating notification", exception.getMessage());
    }
}