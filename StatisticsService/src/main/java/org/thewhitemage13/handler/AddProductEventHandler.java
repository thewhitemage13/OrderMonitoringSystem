package org.thewhitemage13.handler;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.interfaces.AddProductEventHandlerInterface;
import org.thewhitemage13.service.InventoryStatisticService;

@Component
@KafkaListener(topics = "add.product")
public class AddProductEventHandler implements AddProductEventHandlerInterface {
    private final InventoryStatisticService inventoryStatisticService;

    public AddProductEventHandler(InventoryStatisticService inventoryStatisticService) {
        this.inventoryStatisticService = inventoryStatisticService;
    }

    @Override
    @KafkaHandler
    public void addProduct(ProductCreateEvent productCreateEvent) {
        System.out.println(productCreateEvent.getId());
        InventoryStatistic inventoryStatistic = new InventoryStatistic();
        inventoryStatistic.setMessage("Product added");
        inventoryStatisticService.createInventoryStatistic(inventoryStatistic, productCreateEvent);
    }

}
