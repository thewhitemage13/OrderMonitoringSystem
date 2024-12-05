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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddProductEventHandlerTest {
    @Mock
    private InventoryStatisticService inventoryStatisticService;

    @InjectMocks
    private AddProductEventHandler addProductEventHandler;

    @Test
    void addProduct_shouldInvokeInventoryStatisticService_withCorrectParameters() {
        ProductCreateEvent event = new ProductCreateEvent();
        event.setId(1L);
        event.setName("Test Product");
        event.setQuantity(10L);

        addProductEventHandler.addProduct(event);

        ArgumentCaptor<InventoryStatistic> inventoryStatisticCaptor = ArgumentCaptor.forClass(InventoryStatistic.class);
        ArgumentCaptor<ProductCreateEvent> productCreateEventCaptor = ArgumentCaptor.forClass(ProductCreateEvent.class);

        verify(inventoryStatisticService, times(1)).createInventoryStatistic(
                inventoryStatisticCaptor.capture(),
                productCreateEventCaptor.capture()
        );

        InventoryStatistic capturedInventoryStatistic = inventoryStatisticCaptor.getValue();
        ProductCreateEvent capturedEvent = productCreateEventCaptor.getValue();

        assertNotNull(capturedInventoryStatistic);
        assertEquals("Product added", capturedInventoryStatistic.getMessage());
        assertEquals(event, capturedEvent);
    }

    @Test
    void addProduct_shouldPrintEventId() {
        ProductCreateEvent event = new ProductCreateEvent();
        event.setId(1L);

        addProductEventHandler.addProduct(event);

        verify(inventoryStatisticService, times(1)).createInventoryStatistic(any(), eq(event));
    }
}