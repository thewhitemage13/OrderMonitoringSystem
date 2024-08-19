package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.interfaces.UpdateStockEventHandlerInterface;
import org.thewhitemage13.service.InventoryStatisticService;


@Component
@KafkaListener(topics = "update.stock")
public class UpdateStockEventHandler implements UpdateStockEventHandlerInterface {
    private final InventoryStatisticService inventoryStatisticService;

    @Autowired
    public UpdateStockEventHandler(InventoryStatisticService inventoryStatisticService) {
        this.inventoryStatisticService = inventoryStatisticService;
    }

    @Override
    @KafkaHandler
    public void updateStock(ProductCreateEvent productCreateEvent) {
        InventoryStatistic inventoryStatistic = new InventoryStatistic();
        inventoryStatistic.setMessage("Updated Stock");
        inventoryStatisticService.createInventoryStatistic(inventoryStatistic, productCreateEvent);
    }
}
