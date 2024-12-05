package org.thewhitemage13.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.service.InventoryStatisticService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateStockEventHandlerTest {
    @Mock
    private InventoryStatisticService inventoryStatisticService;
    @InjectMocks
    private UpdateStockEventHandler updateStockEventHandler;

    @Test
    void shouldCallCreateInventoryStatisticWhenUpdateStockEventReceived() {
        ProductCreateEvent productCreateEvent = new ProductCreateEvent();

        productCreateEvent.setId(1L);
        productCreateEvent.setName("Product Name");
        productCreateEvent.setQuantity(10L);

        updateStockEventHandler.updateStock(productCreateEvent);

        verify(inventoryStatisticService, times(1))
                .createInventoryStatistic(any(InventoryStatistic.class), eq(productCreateEvent));
    }
}