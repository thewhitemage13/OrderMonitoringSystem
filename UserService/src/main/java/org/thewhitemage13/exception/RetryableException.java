package org.thewhitemage13.exception;

/**
 * Exception thrown when an operation is eligible for retries.
 * <p>
 * This exception is typically used in messaging or transactional workflows where retries are supported.
 * </p>
 *
 * @see RuntimeException
 */
public class RetryableException extends RuntimeException {
    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }
}
