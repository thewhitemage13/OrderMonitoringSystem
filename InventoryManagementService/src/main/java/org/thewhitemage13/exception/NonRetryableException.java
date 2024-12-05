package org.thewhitemage13.exception;

/**
 * Exception thrown for errors that are non-retryable.
 * <p>
 * This exception is typically used to signal conditions where retrying an operation will not succeed,
 * and thus further attempts should not be made. It extends {@link RuntimeException} to allow for
 * unchecked exception handling in the application.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Signals an error that should not be retried.</li>
 *     <li>Extends {@link RuntimeException} to provide flexibility in error handling.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception can be thrown in scenarios where an operation has failed in such a way that retrying
 * will not resolve the issue (e.g., invalid input, invalid state, etc.).
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class NonRetryableException extends RuntimeException {

    /**
     * Constructs a new {@code NonRetryableException} with the specified detail message.
     * <p>
     * This constructor allows the exception to carry a descriptive message explaining
     * the cause of the error.
     * </p>
     *
     * @param message the detail message
     */
    public NonRetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code NonRetryableException} with the specified cause.
     * <p>
     * This constructor allows the exception to be chained to another throwable (e.g., a lower-level exception).
     * </p>
     *
     * @param cause the cause of the exception
     */
    public NonRetryableException(Throwable cause) {
        super(cause);
    }

}
