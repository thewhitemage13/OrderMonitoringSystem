package org.thewhitemage13.handler;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.InventoryStatistic;
import org.thewhitemage13.interfaces.AddProductEventHandlerInterface;
import org.thewhitemage13.service.InventoryStatisticService;

/**
 * Handles the {@link ProductCreateEvent} by processing the event and creating an inventory statistic.
 * <p>
 * This class listens for events on the "add.product" Kafka topic and triggers the processing of product
 * creation by creating a corresponding {@link InventoryStatistic} entity. The handler is responsible for
 * updating the inventory statistics after a product is added.
 * </p>
 *
 * @see ProductCreateEvent
 * @see InventoryStatistic
 * @see InventoryStatisticService
 * @see AddProductEventHandlerInterface
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "add.product")
public class AddProductEventHandler implements AddProductEventHandlerInterface {
    private final InventoryStatisticService inventoryStatisticService;

    /**
     * Constructs a new {@link AddProductEventHandler} instance.
     * <p>
     * The constructor initializes the handler with an instance of {@link InventoryStatisticService},
     * which is used to create inventory statistics upon receiving a product creation event.
     * </p>
     *
     * @param inventoryStatisticService the service used for creating inventory statistics
     */
    public AddProductEventHandler(InventoryStatisticService inventoryStatisticService) {
        this.inventoryStatisticService = inventoryStatisticService;
    }

    /**
     * Handles the {@link ProductCreateEvent} by creating an {@link InventoryStatistic} for the added product.
     * <p>
     * When a product is added, the event triggers this method, which creates a new inventory statistic
     * record. The message for the inventory statistic is set to "Product added", and the statistic is
     * then saved using the {@link InventoryStatisticService}.
     * </p>
     *
     * @param productCreateEvent the event containing details about the product that was created
     */
    @Override
    @KafkaHandler
    public void addProduct(ProductCreateEvent productCreateEvent) {
        System.out.println(productCreateEvent.getId());
        InventoryStatistic inventoryStatistic = new InventoryStatistic();
        inventoryStatistic.setMessage("Product added");
        inventoryStatisticService.createInventoryStatistic(inventoryStatistic, productCreateEvent);
    }

}
