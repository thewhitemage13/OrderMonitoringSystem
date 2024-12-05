package org.thewhitemage13.exception;

/**
 * Exception thrown when an error occurs that is non-retryable.
 * <p>
 * This exception indicates that the error encountered cannot be retried, meaning that subsequent attempts
 * to perform the same operation are unlikely to succeed. It is typically used to signal cases where the
 * error is permanent or the system cannot recover from it.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link RuntimeException}, making it an unchecked exception.</li>
 *     <li>Used to indicate errors that should not be retried.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception is useful in scenarios where certain errors require immediate action, such as user input
 * errors, invalid system states, or other conditions where retrying the operation would not resolve the issue.
 * </p>
 *
 * @see RuntimeException
 * @see Exception
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class NonRetryableException extends RuntimeException {

    /**
     * Constructs a new {@link NonRetryableException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public NonRetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link NonRetryableException} with the specified cause.
     *
     * @param cause the cause of the exception (a {@link Throwable} that triggered this exception)
     */
    public NonRetryableException(Throwable cause) {
        super(cause);
    }

}
