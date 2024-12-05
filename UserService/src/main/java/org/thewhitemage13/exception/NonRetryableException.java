package org.thewhitemage13.exception;

/**
 * Exception thrown when an operation is non-retryable due to a fatal or logical error.
 * <p>
 * This exception is typically used in messaging or task retry scenarios.
 * </p>
 *
 * @see RuntimeException
 */
public class NonRetryableException extends RuntimeException {
    public NonRetryableException(String message) {
        super(message);
    }

    public NonRetryableException(Throwable cause) {
        super(cause);
    }
}
