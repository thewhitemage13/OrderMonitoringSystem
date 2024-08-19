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

@Component
@KafkaListener(topics = "order.created")
public class OrderCreateHandler implements OrderCreateEventHandlerInterface {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderCreateHandler.class);
    private final ProductRepository productRepository;
    private final KafkaTemplate<Long, ProductCreateEvent> kafkaTemplate;

    public OrderCreateHandler(ProductRepository productRepository, KafkaTemplate<Long, ProductCreateEvent> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

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
