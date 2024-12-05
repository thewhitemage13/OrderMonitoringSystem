package org.thewhitemage13.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

/**
 * Service class for managing orders in the application.
 * <p>
 * This class provides methods for creating, updating, and retrieving orders. It integrates with
 * external services for inventory and user validation, processes Kafka events, and manages order data
 * in the database.
 * </p>
 *
 * <h2>Features:</h2>
 * <ul>
 *     <li>Create new orders with validation for products and users.</li>
 *     <li>Update order status and notify via Kafka messaging.</li>
 *     <li>Retrieve orders with caching support for optimized performance.</li>
 * </ul>
 *
 * @see Order
 * @see CreateOrderDTO
 * @see ShowOrderDTO
 * @see OrderProcessor
 * @see InventoryClient
 * @see UserClient
 * @see KafkaTemplate
 * @see OrderRepository
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Transactional
@Service
public class OrderService implements OrderServiceInterface {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<Long, OrderCreatedEvent> kafkaTemplate;
    private final InventoryClient inventoryClient;
    private final UserClient userClient;
    private final OrderProcessor orderProcessor;

    /**
     * Constructs an instance of {@code OrderService} with required dependencies.
     *
     * @param orderRepository the repository for managing order persistence
     * @param kafkaTemplate the Kafka template for publishing order events
     * @param inventoryClient the client for interacting with the inventory service
     * @param userClient the client for interacting with the user service
     * @param orderProcessor the processor for transforming order data
     */
    @Autowired
    public OrderService(OrderRepository orderRepository, KafkaTemplate<Long, OrderCreatedEvent> kafkaTemplate, InventoryClient inventoryClient, UserClient userClient, OrderProcessor orderProcessor) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.inventoryClient = inventoryClient;
        this.userClient = userClient;
        this.orderProcessor = orderProcessor;
    }

    /**
     * Creates a new order.
     * <p>
     * Validates product availability, retrieves pricing and user information, and calculates the total cost.
     * Saves the order in the database and publishes an event to the Kafka topic.
     * </p>
     *
     * @param createOrderDTO the DTO containing order creation details
     * @throws ProductNotFoundException if the product is not available
     * @throws UserNotFoundException if the user is not found
     */
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

    /**
     * Updates the status of an existing order.
     * <p>
     * Retrieves the order by its ID, updates its status, saves the changes, and publishes an event to Kafka.
     * Evicts the cached data for the specified order.
     * </p>
     *
     * @param orderId the ID of the order to be updated
     * @param status the new status of the order
     * @throws OrderNotFoundException if the order is not found
     */
    @CacheEvict(cacheNames = "orders", key = "#orderId")
    @Override
    public void updateOrderStatus(Long orderId, String status) throws OrderNotFoundException {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with id = %s not found".formatted(orderId)));

        order.setStatus(status);

        kafkaTemplate.send("order.updated", order.getId(), orderProcessor.orderEventInitialize(order));

        orderRepository.save(order);
    }

    /**
     * Retrieves all orders.
     * <p>
     * Fetches all orders from the database and caches the results.
     * </p>
     *
     * @return a list of all orders
     */
    @Cacheable(cacheNames = "orders")
    @Override
    public List<Order> showAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves a specific order by its ID.
     * <p>
     * Fetches the order details and processes it into a DTO for display. The result is cached.
     * </p>
     *
     * @param orderId the ID of the order to retrieve
     * @return a {@link ShowOrderDTO} representing the order details
     * @throws OrderNotFoundException if the order is not found
     */
    @Cacheable(cacheNames = "orders", key = "#orderId")
    @Override
    public ShowOrderDTO showOrderById(Long orderId) throws OrderNotFoundException {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with id = %s not found".formatted(orderId)));

        return orderProcessor.returnShowOrderDto(order);
    }
}
