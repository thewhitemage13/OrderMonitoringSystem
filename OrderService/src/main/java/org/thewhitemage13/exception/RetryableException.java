package org.thewhitemage13.exception;

/**
 * Exception thrown when an error occurs that is retryable.
 * <p>
 * This exception indicates that the error encountered can be retried, meaning that subsequent attempts
 * to perform the same operation may succeed. It is typically used to signal temporary errors, such as
 * network issues or service unavailability, that may resolve themselves with retries.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link RuntimeException}, making it an unchecked exception.</li>
 *     <li>Used to indicate errors that are temporary and can be retried.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception is useful in scenarios where certain errors are temporary and retrying the operation
 * could resolve the issue. Common examples include transient network failures or temporary unavailability
 * of an external service.
 * </p>
 *
 * @see RuntimeException
 * @see Exception
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class RetryableException extends RuntimeException {

    /**
     * Constructs a new {@link RetryableException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public RetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link RetryableException} with the specified cause.
     *
     * @param cause the cause of the exception (a {@link Throwable} that triggered this exception)
     */
    public RetryableException(Throwable cause) {
        super(cause);
    }

}
