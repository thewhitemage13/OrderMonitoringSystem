package org.thewhitemage13.exception;

/**
 * Exception thrown when an order is not found in the system.
 * <p>
 * This exception is used to signal that a requested order could not be found in the database or system.
 * It may be thrown when attempting to retrieve, update, or delete an order that does not exist.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link Exception}, making it a checked exception.</li>
 *     <li>Typically used in scenarios where an order is expected but cannot be found.</li>
 *     <li>Provides multiple constructors for different ways to initialize the exception.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception is useful when an operation involves retrieving or manipulating an order, and the order
 * does not exist. It provides a clear signal to the calling code that the order is missing and helps handle
 * this scenario gracefully.
 * </p>
 *
 * @see Exception
 * @see RuntimeException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class OrderNotFoundException extends Exception {

    /**
     * Constructs a new {@link OrderNotFoundException} with {@code null} as its detail message.
     */
    public OrderNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@link OrderNotFoundException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public OrderNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link OrderNotFoundException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause the cause of the exception (a {@link Throwable} that triggered this exception)
     */
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link OrderNotFoundException} with the specified cause.
     *
     * @param cause the cause of the exception (a {@link Throwable} that triggered this exception)
     */
    public OrderNotFoundException(Throwable cause) {
        super(cause);
    }
}
