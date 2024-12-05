package org.thewhitemage13.exception;

/**
 * Exception thrown for errors that are retryable.
 * <p>
 * This exception is typically used to signal conditions where retrying an operation may succeed,
 * and further attempts should be made. It extends {@link RuntimeException} to provide unchecked
 * exception handling in the application.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Signals an error that is retryable and may succeed if retried.</li>
 *     <li>Extends {@link RuntimeException} to provide flexibility in error handling.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception can be thrown in scenarios where an operation has failed but retrying it might resolve
 * the issue (e.g., network issues, temporary external service failures, etc.).
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class RetryableException extends RuntimeException {

    /**
     * Constructs a new {@code RetryableException} with the specified detail message.
     * <p>
     * This constructor allows the exception to carry a descriptive message explaining
     * the cause of the error.
     * </p>
     *
     * @param message the detail message
     */
    public RetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code RetryableException} with the specified cause.
     * <p>
     * This constructor allows the exception to be chained to another throwable (e.g., a lower-level exception).
     * </p>
     *
     * @param cause the cause of the exception
     */
    public RetryableException(Throwable cause) {
        super(cause);
    }

}
