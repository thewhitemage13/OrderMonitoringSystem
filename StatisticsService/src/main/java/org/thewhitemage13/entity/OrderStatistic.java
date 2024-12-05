package org.thewhitemage13.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing order statistics.
 * <p>
 * This class is used to store and manage data related to order statistics, such as total orders,
 * total revenue, packages received, and parcels in transit. It is mapped to a database table
 * using JPA annotations and includes validation constraints for all fields.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks total orders and revenue for a specific date.</li>
 *     <li>Maintains data about received packages and parcels in transit.</li>
 *     <li>Supports safe addition of revenue through the {@link #add(BigDecimal)} method.</li>
 * </ul>
 *
 * <h2>Field Constraints:</h2>
 * <ul>
 *     <li><b>id</b>: Auto-generated unique identifier for each statistic.</li>
 *     <li><b>date</b>: Required; represents the date of the statistic.</li>
 *     <li><b>totalOrders</b>: Required; minimum value of 0.</li>
 *     <li><b>totalRevenue</b>: Required; minimum value of 0.</li>
 *     <li><b>packagesReceived</b>: Required; minimum value of 0.</li>
 *     <li><b>parcelsInTransit</b>: Required; minimum value of 0.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class OrderStatistic implements Serializable {

    /**
     * The unique identifier for the order statistic.
     * <p>
     * This field is auto-generated using the identity strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The date associated with the order statistic.
     * <p>
     * This field is required and must not be null.
     * </p>
     */
    @NotNull(message = "Date is required")
    private LocalDate date;

    /**
     * The total number of orders for the specified date.
     * <p>
     * This field is required and must be at least 0.
     * </p>
     */
    @NotNull(message = "Total orders cannot be null")
    @Min(value = 0, message = "Total orders must be at least 0")
    private Long totalOrders = 0L;

    /**
     * The total revenue generated for the specified date.
     * <p>
     * This field is required and must be at least 0.
     * </p>
     */
    @NotNull(message = "Total revenue cannot be null")
    @Min(value = 0, message = "Total revenue must be at least 0")
    private BigDecimal totalRevenue;

    /**
     * The number of packages received on the specified date.
     * <p>
     * This field is required and must be at least 0.
     * </p>
     */
    @NotNull(message = "Packages received cannot be null")
    @Min(value = 0, message = "Packages received must be at least 0")
    private Long packagesReceived = 0L;

    /**
     * The number of parcels currently in transit.
     * <p>
     * This field is required and must be at least 0.
     * </p>
     */
    @NotNull(message = "Parcels in transit cannot be null")
    @Min(value = 0, message = "Parcels in transit must be at least 0")
    private Long parcelsInTransit = 0L;

    /**
     * Adds a specified amount to the total revenue.
     * <p>
     * This method safely adds the given amount to the {@link #totalRevenue} field.
     * The input amount must not be null.
     * </p>
     *
     * @param amount the amount to add to the total revenue
     * @return the updated total revenue
     * @throws IllegalArgumentException if the amount is null
     */
    public BigDecimal add(@NotNull(message = "Amount cannot be null") BigDecimal amount) {
        return totalRevenue.add(amount);
    }
}
