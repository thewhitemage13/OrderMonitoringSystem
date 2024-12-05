package org.thewhitemage13.exception;

/**
 * Custom exception to indicate a retryable error.
 * <p>
 * This exception is thrown when an error occurs that may be retried. It extends {@link RuntimeException},
 * making it an unchecked exception. It is typically used in scenarios where retrying the operation may resolve
 * the issue (e.g., network timeouts, temporary unavailability of a service).
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class RetryableException extends RuntimeException {

    /**
     * Constructs a new {@link RetryableException} with the specified detail message.
     * <p>
     * This constructor allows you to provide a specific error message explaining the cause of the exception.
     * </p>
     *
     * @param message the detail message explaining the exception
     */
    public RetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link RetryableException} with the specified cause.
     * <p>
     * This constructor allows you to pass the original {@link Throwable} that caused this exception.
     * </p>
     *
     * @param cause the cause of the exception
     */
    public RetryableException(Throwable cause) {
        super(cause);
    }

}
