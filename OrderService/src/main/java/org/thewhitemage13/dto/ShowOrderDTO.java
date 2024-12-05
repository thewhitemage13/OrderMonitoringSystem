package org.thewhitemage13.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for displaying order details.
 * <p>
 * This class is used for transferring detailed order information, including the user's ID,
 * items in the order, address, status, total sum, product ID, and item count. It includes
 * validation constraints to ensure that the data is properly validated before being processed.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Contains user ID, items, address, status, total sum, product ID, and count of items.</li>
 *     <li>Validates fields using annotations such as {@link NotNull}, {@link Size}, {@link Min}, and {@link DecimalMin}.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This DTO is typically used in REST APIs to capture and transfer order details, such as
 * for displaying order information to the user. It is validated automatically when used
 * with frameworks like Spring for input validation.
 * </p>
 *
 * @see jakarta.validation.constraints.NotNull
 * @see jakarta.validation.constraints.Size
 * @see jakarta.validation.constraints.Min
 * @see jakarta.validation.constraints.DecimalMin
 * @see java.io.Serializable
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowOrderDTO implements Serializable {

    /**
     * The unique identifier of the user who placed the order.
     * This field cannot be {@code null}.
     */
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    /**
     * A list of items included in the order.
     * This field cannot be {@code null} and must contain at least one item.
     */
    @NotNull(message = "Items cannot be null")
    @Size(min = 1, message = "Items list cannot be empty")
    private String items;

    /**
     * The delivery address for the order.
     * This field cannot be {@code null} and must be between 5 and 255 characters in length.
     */
    @NotNull(message = "Address cannot be null")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

    /**
     * The current status of the order.
     * This field cannot be {@code null} and must be between 3 and 20 characters in length.
     */
    @NotNull(message = "Status cannot be null")
    @Size(min = 3, max = 20, message = "Status must be between 3 and 20 characters")
    private String status;

    /**
     * The total sum of the order.
     * This field cannot be {@code null} and must be greater than or equal to 0.01.
     */
    @NotNull(message = "Total sum cannot be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Total sum must be greater than 0")
    private BigDecimal totalSum;

    /**
     * The unique identifier of the product included in the order.
     * This field cannot be {@code null}.
     */
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    /**
     * The number of items of the product in the order.
     * This field cannot be {@code null} and must be at least 1.
     */
    @NotNull(message = "Count of items cannot be null")
    @Min(value = 1, message = "Count of items must be at least 1")
    private Long countOfItems;

    /**
     * Provides a string representation of the {@link ShowOrderDTO}.
     * <p>
     * This method returns a string containing the values of {@code userId}, {@code items},
     * {@code address}, {@code status}, {@code totalSum}, {@code productId}, and {@code countOfItems}
     * for easier debugging and logging.
     * </p>
     *
     * @return a string representation of the {@link ShowOrderDTO}
     */
    @Override
    public String toString() {
        return "ShowOrderDTO{" +
                "userId=" + userId +
                ", items='" + items + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", totalSum=" + totalSum +
                ", productId=" + productId +
                ", countOfItems=" + countOfItems +
                '}';
    }
}
