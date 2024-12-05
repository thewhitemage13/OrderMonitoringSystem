package org.thewhitemage13.exception;

/**
 * Custom exception to indicate a non-retryable error.
 * <p>
 * This exception is thrown when an error occurs that cannot be retried. It extends {@link RuntimeException},
 * making it an unchecked exception. It is typically used in scenarios where retrying the operation will not resolve
 * the issue (e.g., invalid input, missing required resources).
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class NonRetryableException extends RuntimeException {

    /**
     * Constructs a new {@link NonRetryableException} with the specified detail message.
     * <p>
     * This constructor allows you to provide a specific error message explaining the cause of the exception.
     * </p>
     *
     * @param message the detail message explaining the exception
     */
    public NonRetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link NonRetryableException} with the specified cause.
     * <p>
     * This constructor allows you to pass the original {@link Throwable} that caused this exception.
     * </p>
     *
     * @param cause the cause of the exception
     */
    public NonRetryableException(Throwable cause) {
        super(cause);
    }

}
