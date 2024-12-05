package org.thewhitemage13.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.Product;
import org.thewhitemage13.exception.ProductNotFoundException;
import org.thewhitemage13.interfaces.OrderCreateEventHandlerInterface;
import org.thewhitemage13.repository.ProductRepository;

/**
 * Handles the {@link OrderCreatedEvent} and updates the stock accordingly.
 * <p>
 * This component listens to the "order.created" Kafka topic and processes the event to update product quantities.
 * If the product's stock falls below a threshold, a "low.stock" event is sent to notify about low inventory.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Processes {@link OrderCreatedEvent} by updating the product quantity in the database.</li>
 *     <li>Publishes {@link ProductCreateEvent} to the "low.stock" Kafka topic when stock is low.</li>
 *     <li>Handles {@link ProductNotFoundException} in case the product is not found in the repository.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This handler is automatically triggered when an {@link OrderCreatedEvent} is published to the "order.created" Kafka topic.
 * The handler updates the product stock and sends relevant events based on stock conditions.
 * </p>
 *
 * @see OrderCreatedEvent
 * @see ProductCreateEvent
 * @see Product
 * @see ProductRepository
 * @see ProductNotFoundException
 * @see KafkaTemplate
 * @see OrderCreateEventHandlerInterface
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "order.created")
public class OrderCreateHandler implements OrderCreateEventHandlerInterface {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderCreateHandler.class);
    private final ProductRepository productRepository;
    private final KafkaTemplate<Long, ProductCreateEvent> kafkaTemplate;

    /**
     * Constructs an {@code OrderCreateHandler} with the specified {@link ProductRepository} and {@link KafkaTemplate}.
     *
     * @param productRepository the repository to interact with product data
     * @param kafkaTemplate the KafkaTemplate for sending {@link ProductCreateEvent} to topics
     */
    public OrderCreateHandler(ProductRepository productRepository, KafkaTemplate<Long, ProductCreateEvent> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Handles an {@link OrderCreatedEvent} by updating the corresponding product's quantity.
     * <p>
     * The handler decreases the product's quantity based on the order's count of items. If the stock falls below 100,
     * it sends a {@link ProductCreateEvent} to the "low.stock" Kafka topic. It also ensures the updated product is saved in the repository.
     * </p>
     *
     * @param orderCreatedEvent the event containing the order details
     */
    @Override
    @KafkaHandler
    public void create(OrderCreatedEvent orderCreatedEvent){
        System.out.println(orderCreatedEvent.getProductId());
        Product orderProduct = productRepository.findById(orderCreatedEvent.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product with id = %s not found".formatted(orderCreatedEvent.getProductId())));
        orderProduct.minQuantity(orderCreatedEvent.getCountOfItems());
        orderCreatedEvent.setTotalPrice(orderProduct.getPrice());

        if(orderProduct.getQuantity() < 100) {

            ProductCreateEvent productCreateEvent =
                    new ProductCreateEvent
                            (
                                    orderProduct.getId(),
                                    orderProduct.getName(),
                                    orderProduct.getQuantity(),
                                    orderProduct.getPrice()
                            );

            kafkaTemplate.send("low.stock", orderProduct.getId(), productCreateEvent);

        }
        if (orderProduct.getQuantity() < 1) {
            return;
        }
        productRepository.save(orderProduct);
    }
}
