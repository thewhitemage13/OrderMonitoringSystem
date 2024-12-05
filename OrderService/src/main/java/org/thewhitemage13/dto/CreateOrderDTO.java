package org.thewhitemage13.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) for creating an order.
 * <p>
 * This class is used for transferring order creation data between the client and server.
 * It includes validation constraints to ensure that the data is properly validated before
 * being processed. The DTO contains the user ID, address, product ID, and count of items
 * associated with the order.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Contains user ID, address, product ID, and count of items for creating an order.</li>
 *     <li>Validates fields using annotations such as {@link NotNull}, {@link Size}, and {@link Min}.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This DTO is typically used in REST APIs to capture the details required to create an order.
 * It is validated automatically when used with frameworks like Spring for input validation.
 * </p>
 *
 * @see jakarta.validation.constraints.NotNull
 * @see jakarta.validation.constraints.Size
 * @see jakarta.validation.constraints.Min
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
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO implements Serializable {

    /**
     * The unique identifier of the user placing the order.
     * This field cannot be {@code null}.
     */
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    /**
     * The delivery address for the order.
     * This field cannot be {@code null} and must be between 5 and 255 characters in length.
     */
    @NotNull(message = "Address cannot be null")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

    /**
     * The unique identifier of the product being ordered.
     * This field cannot be {@code null}.
     */
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    /**
     * The number of items for the product in the order.
     * This field cannot be {@code null} and must be at least 1.
     */
    @NotNull(message = "Count of items cannot be null")
    @Min(value = 1, message = "Count of items must be at least 1")
    private Long countOfItems;

    /**
     * Provides a string representation of the {@link CreateOrderDTO}.
     * <p>
     * This method returns a string containing the values of {@code userId}, {@code address},
     * {@code productId}, and {@code countOfItems} for easier debugging and logging.
     * </p>
     *
     * @return a string representation of the {@link CreateOrderDTO}
     */
    @Override
    public String toString() {
        return "CreateOrderDTO{" +
                "userId=" + userId +
                ", address='" + address + '\'' +
                ", productId=" + productId +
                ", countOfItems=" + countOfItems +
                '}';
    }
}
