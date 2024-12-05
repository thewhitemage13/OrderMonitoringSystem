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
class OrderUpdateEventHandlerTest {
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private OrderUpdateEventHandler handler;

    @Test
    void testUpdate_Success() {
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setUserId(1L);

        assertDoesNotThrow(() -> handler.update(event));

        verify(notificationService, times(1)).createNotification(any(Notification.class), eq(event));
    }

//    @Test
//    void testUpdate_ExceptionHandling() {
//        // Arrange
//        OrderCreatedEvent event = new OrderCreatedEvent();
//        event.setUserId(1L);
//
//
//        doThrow(new RuntimeException("Error creating notification"))
//                .when(notificationService)
//                .createNotification(any(Notification.class), eq(event));
//
//        // Act & Assert
//        assertDoesNotThrow(() -> handler.update(event));
//
//        // Verify that the exception was handled
//        verify(notificationService, times(1)).createNotification(any(Notification.class), eq(event));
//    }
}