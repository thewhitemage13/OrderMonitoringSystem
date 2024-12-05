package org.thewhitemage13.exception;

/**
 * Custom exception thrown when a product is not found in the system.
 * <p>
 * This exception is used to indicate that a product with a specific ID or name could not be
 * found in the database. It is a {@link RuntimeException}, meaning it is an unchecked exception.
 * </p>
 *
 * <h2>Features:</h2>
 * <ul>
 *     <li>Provides multiple constructors to create an exception with or without a message and cause.</li>
 *     <li>Extends {@link RuntimeException} to represent an unchecked exception.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception is typically thrown when a product lookup operation (e.g., by ID or name) fails to
 * find the requested product in the system. It is commonly used in service layers to signal missing data
 * and can be handled or logged appropriately.
 * </p>
 *
 * <h2>Constructors:</h2>
 * <ul>
 *     <li>{@link ProductNotFoundException()} - Default constructor.</li>
 *     <li>{@link ProductNotFoundException(String message)} - Constructor with a custom message.</li>
 * </ul>
 *
 * @see RuntimeException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class ProductNotFoundException extends RuntimeException{

    /**
     * Default constructor for {@link ProductNotFoundException}.
     * Calls the superclass constructor with no arguments.
     */
    public ProductNotFoundException() {
        super();
    }

    /**
     * Constructor that accepts a custom message.
     *
     * @param message the detail message that explains the reason for the exception
     */
    public ProductNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor that accepts both a custom message and a cause.
     *
     * @param message the detail message that explains the reason for the exception
     * @param cause the cause of the exception
     */
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that accepts a cause.
     *
     * @param cause the cause of the exception
     */
    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }

}
