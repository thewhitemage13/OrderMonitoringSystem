package org.thewhitemage13.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.OrderCreatedEvent;
import org.thewhitemage13.dto.CreateOrderDTO;
import org.thewhitemage13.dto.ShowOrderDTO;
import org.thewhitemage13.entity.Order;
import org.thewhitemage13.exception.OrderNotFoundException;
import org.thewhitemage13.exception.ProductNotFoundException;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.client.InventoryClient;
import org.thewhitemage13.interfaces.OrderServiceInterface;
import org.thewhitemage13.client.UserClient;
import org.thewhitemage13.processor.OrderProcessor;
import org.thewhitemage13.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class OrderService implements OrderServiceInterface {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<Long, OrderCreatedEvent> kafkaTemplate;
    private final InventoryClient inventoryClient;
    private final UserClient userClient;
    private final OrderProcessor orderProcessor;

    @Autowired
    public OrderService(OrderRepository orderRepository, KafkaTemplate<Long, OrderCreatedEvent> kafkaTemplate, InventoryClient inventoryClient, UserClient userClient, OrderProcessor orderProcessor) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.inventoryClient = inventoryClient;
        this.userClient = userClient;
        this.orderProcessor = orderProcessor;
    }

    @SneakyThrows
    @Override
    public void createOrder(CreateOrderDTO createOrderDTO) {

        ResponseEntity<Boolean> response = inventoryClient.checkProductAvailability(createOrderDTO.getProductId(), createOrderDTO.getCountOfItems());
        Boolean productAvailable = response.getBody();
        if (Boolean.FALSE.equals(productAvailable)) {
            throw new ProductNotFoundException("Product with id = %s not found".formatted(createOrderDTO.getProductId()));
        }

        ResponseEntity<BigDecimal> price = inventoryClient.getPrice(createOrderDTO.getProductId());
        BigDecimal priceInStock = price.getBody();

        ResponseEntity<String> productName = inventoryClient.getProductNameById(createOrderDTO.getProductId());
        String name = productName.getBody();

        ResponseEntity<Boolean> user = userClient.checkUser(createOrderDTO.getUserId());
        Boolean userAvailable = user.getBody();

        if (Boolean.FALSE.equals(userAvailable)) {
            throw new UserNotFoundException("User with id = %s not found".formatted(createOrderDTO.getUserId()));
        }

        BigDecimal countOfItems = BigDecimal.valueOf(createOrderDTO.getCountOfItems());

        BigDecimal totalPrice = priceInStock.multiply(countOfItems);
        Order order = new Order
                (
                        createOrderDTO.getUserId(),
                        name,
                        createOrderDTO.getAddress(),
                        "LOAD",
                        totalPrice,
                        createOrderDTO.getProductId(),
                        createOrderDTO.getCountOfItems(),
                        LocalDateTime.now()
                );

        orderRepository.save(order);

        kafkaTemplate.send("order.created", order.getId(), orderProcessor.orderEventInitialize(order));

    }

    @Override
    public void updateOrderStatus(Long orderId, String status) throws OrderNotFoundException {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with id = %s not found".formatted(orderId)));

        order.setStatus(status);

        kafkaTemplate.send("order.updated", order.getId(), orderProcessor.orderEventInitialize(order));

        orderRepository.save(order);
    }

    @Override
    public List<Order> showAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public ShowOrderDTO showOrderById(Long orderId) throws OrderNotFoundException {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with id = %s not found".formatted(orderId)));

        return orderProcessor.returnShowOrderDto(order);
    }
}
