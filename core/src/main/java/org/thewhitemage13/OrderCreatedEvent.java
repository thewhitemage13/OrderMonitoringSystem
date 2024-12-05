package org.thewhitemage13;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents an event that is triggered when an order is created.
 * <p>
 * This class encapsulates all the necessary details related to an order creation event, including information about
 * the user who placed the order, the items in the order, the shipping address, the order's status, the quantity of items,
 * the product ID, the total price of the order, and timestamps for when the order was created and last updated.
 * </p>
 *
 * <h2>Key Fields:</h2>
 * <ul>
 *     <li><b>id</b>: Unique identifier for the order.</li>
 *     <li><b>userId</b>: The ID of the user who created the order.</li>
 *     <li><b>items</b>: A description or list of items included in the order.</li>
 *     <li><b>address</b>: The shipping address associated with the order.</li>
 *     <li><b>status</b>: The current status of the order (e.g., "pending", "shipped").</li>
 *     <li><b>countOfItems</b>: The total number of items in the order.</li>
 *     <li><b>productId</b>: The ID of the product being ordered.</li>
 *     <li><b>totalPrice</b>: The total price of the order.</li>
 *     <li><b>createdAt</b>: Timestamp indicating when the order was created.</li>
 *     <li><b>updatedAt</b>: Timestamp indicating when the order was last updated.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Instances of this class are used in event-driven systems to represent the creation of an order. They carry all the
 * necessary data related to the order and can be used for further processing, such as updating inventory, notifying
 * users, or triggering downstream systems.
 * </p>
 *
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class OrderCreatedEvent {
    private Long id;
    private Long userId;
    private String items;
    private String address;
    private String status;
    private Long countOfItems;
    private Long productId;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long id, Long userId, String items, String address, String status, Long countOfItems, Long productId, BigDecimal totalPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.address = address;
        this.status = status;
        this.countOfItems = countOfItems;
        this.productId = productId;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCountOfItems() {
        return countOfItems;
    }

    public void setCountOfItems(Long countOfItems) {
        this.countOfItems = countOfItems;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "id=" + id +
                ", userId=" + userId +
                ", items='" + items + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", countOfItems=" + countOfItems +
                ", productId=" + productId +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
