package org.thewhitemage13;

import java.math.BigDecimal;

/**
 * Represents an event triggered when a product is created or updated.
 * <p>
 * This class contains all the essential information about a product, such as its ID, name, quantity, and price.
 * It is used in event-driven systems to communicate changes or additions of products in the inventory system.
 * </p>
 *
 * <h2>Key Fields:</h2>
 * <ul>
 *     <li><b>id</b>: The unique identifier for the product.</li>
 *     <li><b>name</b>: The name of the product.</li>
 *     <li><b>quantity</b>: The available quantity of the product in stock.</li>
 *     <li><b>price</b>: The price of a single unit of the product.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This class is used to encapsulate the details of a product when a new product is created or when an existing
 * product's information is updated. The event it represents can be used to notify other parts of the system or
 * trigger downstream actions, such as updating inventory or notifying users.
 * </p>
 *
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class ProductCreateEvent {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;

    public ProductCreateEvent() {
    }

    public ProductCreateEvent(Long id, String name, Long quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductCreateEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
