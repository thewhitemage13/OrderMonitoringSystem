package org.thewhitemage13.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Represents a product entity in the system.
 * <p>
 * This class is a JPA entity that maps to the "products" table in the database.
 * It stores key information about a product, including its unique ID, name, quantity, and price.
 * The class also includes validation constraints to ensure the integrity of product data.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identifier for the product using auto-generated ID.</li>
 *     <li>Validation constraints for product name, quantity, and price to ensure valid data.</li>
 *     <li>Methods to modify product quantity.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Table(name = "products")
@Entity
public class Product {

    /**
     * Unique identifier for the product.
     * <p>
     * This field is the primary key in the "products" table and is automatically
     * generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the product.
     * <p>
     * This field cannot be blank and must not exceed 100 characters in length.
     * </p>
     */
    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    private String name;

    /**
     * Quantity of the product in stock.
     * <p>
     * This field cannot be null and must be zero or positive.
     * </p>
     */
    @NotNull(message = "Quantity cannot be null")
    @PositiveOrZero(message = "Quantity must be zero or positive")
    private Long quantity;

    /**
     * Price of the product.
     * <p>
     * This field cannot be null and must be a positive value greater than zero.
     * The value must be a valid monetary amount with up to two decimal places.
     * </p>
     */
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount")
    private BigDecimal price;

    /**
     * Default constructor for the {@code Product} entity.
     * <p>
     * Initializes a new product instance without setting any fields.
     * </p>
     */
    public Product() {
    }

    /**
     * Constructs a new {@code Product} with the specified values.
     * <p>
     * Initializes a product with the provided ID, name, quantity, and price.
     * </p>
     *
     * @param id       the unique identifier of the product
     * @param name     the name of the product
     * @param quantity the quantity of the product in stock
     * @param price    the price of the product
     */
    public Product(Long id, String name, Long quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Gets the unique identifier of the product.
     *
     * @return the product ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the product.
     *
     * @param id the product ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the product.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the quantity of the product in stock.
     *
     * @return the product quantity
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in stock.
     *
     * @param quantity the product quantity
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the product.
     *
     * @return the product price
     */
    public BigDecimal getPrice() {
        return price;
    }


    /**
     * Sets the price of the product.
     *
     * @param price the product price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Decreases the quantity of the product by a specified amount.
     * <p>
     * This method reduces the quantity of the product in stock.
     * </p>
     *
     * @param quantity the quantity to subtract
     */
    public void minQuantity(Long quantity) {
        this.quantity -= quantity;
    }

    /**
     * Increases the quantity of the product by a specified amount.
     * <p>
     * This method adds to the quantity of the product in stock.
     * </p>
     *
     * @param quantity the quantity to add
     */
    public void plusQuantity(int quantity) {
        this.quantity += quantity;
    }

    /**
     * Returns a string representation of this product.
     * <p>
     * The string contains the product's {@code id}, {@code name}, {@code quantity}, and {@code price}.
     * </p>
     *
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
