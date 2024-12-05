package org.thewhitemage13.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.service.OrderStatisticService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderEventHandlerTest {
    @Mock
    private OrderStatisticService orderStatisticService;
    @InjectMocks
    private OrderEventHandler orderEventHandler;

    @Test
    void shouldCallCreateOrderStatisticWhenOrderCreatedEventReceived() {
        // Arrange
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        // Установим данные для event (например, id, количество, цена)
        orderCreatedEvent.setId(1L);
        orderCreatedEvent.setTotalPrice(BigDecimal.valueOf(100.0));


        // Act
        orderEventHandler.create(orderCreatedEvent); // Вызываем метод обработчика

        // Assert
        verify(orderStatisticService, times(1)).createOrderStatistic(orderCreatedEvent);
    }

    @Test
    void shouldCallUpdateOrderStatisticWhenOrderUpdatedEventReceived() {
        // Arrange
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        // Установим данные для event
        orderCreatedEvent.setId(1L);
        orderCreatedEvent.setTotalPrice(BigDecimal.valueOf(150.0));

        // Act
        orderEventHandler.update(orderCreatedEvent); // Вызываем метод обработчика

        // Assert
        verify(orderStatisticService, times(1)).updateOrderStatistic(orderCreatedEvent);
    }
}