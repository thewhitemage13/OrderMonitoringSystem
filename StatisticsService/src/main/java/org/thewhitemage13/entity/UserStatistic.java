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
import java.time.LocalDate;

/**
 * Entity representing user statistics.
 * <p>
 * This class is used to store and manage statistical data about user creation events.
 * It is mapped to a database table using JPA annotations and includes validation constraints
 * for all fields.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks the total number of users created.</li>
 *     <li>Maintains the date associated with the statistic.</li>
 *     <li>Includes validation to ensure data integrity.</li>
 * </ul>
 *
 * <h2>Field Constraints:</h2>
 * <ul>
 *     <li><b>id</b>: Auto-generated unique identifier for each statistic entry.</li>
 *     <li><b>countOfUserCreated</b>: Required; represents the total count of users created, with a minimum value of 0.</li>
 *     <li><b>createdDate</b>: Required; represents the date when the statistic was recorded.</li>
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
public class UserStatistic implements Serializable {

    /**
     * The unique identifier for the user statistic entry.
     * <p>
     * This field is auto-generated using the identity strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The total number of users created.
     * <p>
     * This field is required and must be at least 0. It represents the count of users
     * created as recorded in this statistic.
     * </p>
     */
    @NotNull(message = "Count of user created cannot be null")
    @Min(value = 0, message = "Count of user created must be at least 0")
    private Long countOfUserCreated = 0L;

    /**
     * The date when this user statistic was recorded.
     * <p>
     * This field is required and must not be null.
     * </p>
     */
    @NotNull(message = "Created date is required")
    private LocalDate createdDate;
}
