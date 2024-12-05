package org.thewhitemage13.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entity representing inventory statistics.
 * <p>
 * This class maps to the database table used to store inventory statistics.
 * It contains information such as the date of the statistic, the item name,
 * quantity, product ID, and an optional message for additional details.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports validation for required fields and constraints.</li>
 *     <li>Uses JPA annotations for database mapping.</li>
 *     <li>Serializable to support potential data transfer or caching.</li>
 * </ul>
 *
 * <h2>Field Constraints:</h2>
 * <ul>
 *     <li><b>id</b>: Auto-generated primary key.</li>
 *     <li><b>date</b>: Must not be null.</li>
 *     <li><b>item</b>: Required, maximum 100 characters.</li>
 *     <li><b>quantity</b>: Must not be null, minimum value of 0.</li>
 *     <li><b>productId</b>: Must not be null, minimum value of 0.</li>
 *     <li><b>message</b>: Optional, maximum 255 characters.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InventoryStatistic implements Serializable {

    /**
     * The unique identifier for the inventory statistic.
     * <p>
     * This field is auto-generated using the identity strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The date associated with the inventory statistic.
     * <p>
     * This field is required and must not be null.
     * </p>
     */
    @NotNull(message = "Date is required")
    private LocalDate date;

    /**
     * The name of the inventory item.
     * <p>
     * This field is required and must not exceed 100 characters.
     * </p>
     */
    @NotBlank(message = "Item name is required")
    @Size(max = 100, message = "Item name must be less than 100 characters")
    private String item;

    /**
     * The quantity of the inventory item.
     * <p>
     * This field is required and must be at least 0.
     * </p>
     */
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be at least 0")
    private Long quantity = 0L;

    /**
     * The product ID associated with the inventory item.
     * <p>
     * This field is required and must be at least 0.
     * </p>
     */
    @NotNull(message = "Product ID is required")
    @Min(value = 0, message = "Product ID must be at least 0")
    private Long productId = 0L;

    /**
     * An optional message providing additional details about the inventory statistic.
     * <p>
     * This field is optional but must not exceed 255 characters if provided.
     * </p>
     */
    @Size(max = 255, message = "Message must be less than 255 characters")
    private String message;
}
