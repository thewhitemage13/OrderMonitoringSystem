package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.interfaces.UpdateStockEventHandlerInterface;
import org.thewhitemage13.service.InventoryStatisticService;

/**
 * Handles the {@link ProductCreateEvent} for updating stock-related statistics.
 * <p>
 * This class listens for events on the "update.stock" Kafka topic and processes them to create inventory statistics
 * indicating that the stock has been updated, using the {@link InventoryStatisticService}.
 * </p>
 *
 * @see ProductCreateEvent
 * @see InventoryStatisticService
 * @see UpdateStockEventHandlerInterface
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "update.stock")
public class UpdateStockEventHandler implements UpdateStockEventHandlerInterface {
    private final InventoryStatisticService inventoryStatisticService;

    /**
     * Constructs a new {@link UpdateStockEventHandler} instance.
     * <p>
     * The constructor initializes the handler with an instance of {@link InventoryStatisticService},
     * which is used to create inventory statistics based on the product update event.
     * </p>
     *
     * @param inventoryStatisticService the service used for managing inventory statistics
     */
    @Autowired
    public UpdateStockEventHandler(InventoryStatisticService inventoryStatisticService) {
        this.inventoryStatisticService = inventoryStatisticService;
    }

    /**
     * Handles the {@link ProductCreateEvent} when the stock is updated.
     * <p>
     * This method listens for the "update.stock" topic and processes the event by creating an inventory statistic
     * indicating that the stock has been updated.
     * </p>
     *
     * @param productCreateEvent the event containing details about the updated product
     */
    @Override
    @KafkaHandler
    public void updateStock(ProductCreateEvent productCreateEvent) {
        InventoryStatistic inventoryStatistic = new InventoryStatistic();
        inventoryStatistic.setMessage("Updated Stock");
        inventoryStatisticService.createInventoryStatistic(inventoryStatistic, productCreateEvent);
    }
}
