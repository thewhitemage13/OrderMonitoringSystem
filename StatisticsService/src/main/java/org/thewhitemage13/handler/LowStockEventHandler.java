package org.thewhitemage13.handler;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.interfaces.LowStockEventHandlerInterface;
import org.thewhitemage13.service.InventoryStatisticService;

/**
 * Handles the {@link ProductCreateEvent} when a product reaches low stock levels.
 * <p>
 * This class listens for events on the "low.stock" Kafka topic and processes the event by creating an
 * {@link InventoryStatistic} with the message "Low Stock" whenever a product's stock falls below a certain threshold.
 * </p>
 *
 * @see ProductCreateEvent
 * @see InventoryStatistic
 * @see InventoryStatisticService
 * @see LowStockEventHandlerInterface
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "low.stock")
public class LowStockEventHandler implements LowStockEventHandlerInterface {
    private final InventoryStatisticService inventoryStatisticService;

    /**
     * Constructs a new {@link LowStockEventHandler} instance.
     * <p>
     * The constructor initializes the handler with an instance of {@link InventoryStatisticService},
     * which is used to create inventory statistics upon detecting low stock levels.
     * </p>
     *
     * @param inventoryStatisticService the service used for creating inventory statistics
     */
    public LowStockEventHandler(InventoryStatisticService inventoryStatisticService) {
        this.inventoryStatisticService = inventoryStatisticService;
    }

    /**
     * Handles the {@link ProductCreateEvent} when a product has low stock.
     * <p>
     * When a product reaches low stock, this method creates a new {@link InventoryStatistic} with the
     * message "Low Stock" and saves it using the {@link InventoryStatisticService}.
     * </p>
     *
     * @param productCreateEvent the event containing details about the product with low stock
     */
    @Override
    @KafkaHandler
    public void lowStock(ProductCreateEvent productCreateEvent) {
        if (productCreateEvent == null) {
            return;
        }
        InventoryStatistic inventoryStatistic = new InventoryStatistic();
        inventoryStatistic.setMessage("Low Stock");
        inventoryStatisticService.createInventoryStatistic(inventoryStatistic, productCreateEvent);
    }
}
