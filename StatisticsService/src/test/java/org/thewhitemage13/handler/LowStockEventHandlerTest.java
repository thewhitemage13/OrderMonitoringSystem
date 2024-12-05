package org.thewhitemage13.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.service.InventoryStatisticService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LowStockEventHandlerTest {
    @Mock
    private InventoryStatisticService inventoryStatisticService;
    @InjectMocks
    private LowStockEventHandler lowStockEventHandler;

    @Test
    void lowStock_shouldInvokeInventoryStatisticService_withCorrectParameters() {
        // Arrange
        ProductCreateEvent event = new ProductCreateEvent();
        event.setId(1L);
        event.setName("Test Product");
        event.setQuantity(5L);

        // Act
        lowStockEventHandler.lowStock(event);

        // Assert
        ArgumentCaptor<InventoryStatistic> inventoryStatisticCaptor = ArgumentCaptor.forClass(InventoryStatistic.class);
        ArgumentCaptor<ProductCreateEvent> productCreateEventCaptor = ArgumentCaptor.forClass(ProductCreateEvent.class);

        // Verify that the inventoryStatisticService method was called once
        verify(inventoryStatisticService, times(1)).createInventoryStatistic(
                inventoryStatisticCaptor.capture(),
                productCreateEventCaptor.capture()
        );

        // Capture and assert the parameters
        InventoryStatistic capturedInventoryStatistic = inventoryStatisticCaptor.getValue();
        ProductCreateEvent capturedEvent = productCreateEventCaptor.getValue();

        assertNotNull(capturedInventoryStatistic);
        assertEquals("Low Stock", capturedInventoryStatistic.getMessage());
        assertEquals(event, capturedEvent);
    }
    @Test
    void lowStock_shouldCorrectlyHandleNullProductCreateEvent() {
        // Act
        lowStockEventHandler.lowStock(null);

        // Assert
        // Since we passed null, we expect no interaction with the inventoryStatisticService
        verify(inventoryStatisticService, times(0)).createInventoryStatistic(any(), any());
    }
}