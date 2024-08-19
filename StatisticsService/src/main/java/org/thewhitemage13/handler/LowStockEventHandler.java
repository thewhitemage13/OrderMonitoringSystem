package org.thewhitemage13.handler;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.interfaces.LowStockEventHandlerInterface;
import org.thewhitemage13.service.InventoryStatisticService;

@Component
@KafkaListener(topics = "low.stock")
public class LowStockEventHandler implements LowStockEventHandlerInterface {
    private final InventoryStatisticService inventoryStatisticService;

    public LowStockEventHandler(InventoryStatisticService inventoryStatisticService) {
        this.inventoryStatisticService = inventoryStatisticService;
    }

    @Override
    @KafkaHandler
    public void lowStock(ProductCreateEvent productCreateEvent) {
        InventoryStatistic inventoryStatistic = new InventoryStatistic();
        inventoryStatistic.setMessage("Low Stock");
        inventoryStatisticService.createInventoryStatistic(inventoryStatistic, productCreateEvent);
    }
}
