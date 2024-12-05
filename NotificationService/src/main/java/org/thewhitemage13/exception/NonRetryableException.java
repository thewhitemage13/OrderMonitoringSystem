package org.thewhitemage13.exception;

/**
 * Custom exception class to represent a non-retryable error.
 * <p>
 * This exception is thrown when an error occurs that should not be retried,
 * such as an irrecoverable failure in the application.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link RuntimeException} to be unchecked.</li>
 *     <li>Can be constructed with a message or a cause, providing flexibility in exception handling.</li>
 * </ul>
 *
 * @see RuntimeException
 * @see Throwable
 * @see Exception
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class NonRetryableException extends RuntimeException {

    /**
     * Constructs a new {@link NonRetryableException} with the specified detail message.
     * <p>
     * This message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     * </p>
     *
     * @param message the detail message, explaining the reason for the exception
     */
    public NonRetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link NonRetryableException} with the specified cause.
     * <p>
     * This constructor allows chaining of exceptions to preserve the root cause.
     * </p>
     *
     * @param cause the cause of the exception (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method)
     */
    public NonRetryableException(Throwable cause) {
        super(cause);
    }

}
