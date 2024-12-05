package org.thewhitemage13.exception;

/**
 * Custom exception class to represent a retryable error.
 * <p>
 * This exception is thrown when an error occurs that can be retried, such as
 * a temporary issue that may resolve upon retrying the operation.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link RuntimeException}, making it an unchecked exception.</li>
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
public class RetryableException extends RuntimeException {

    /**
     * Constructs a new {@link RetryableException} with the specified detail message.
     * <p>
     * This message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     * </p>
     *
     * @param message the detail message, explaining the reason for the exception
     */
    public RetryableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link RetryableException} with the specified cause.
     * <p>
     * This constructor allows chaining of exceptions to preserve the root cause.
     * </p>
     *
     * @param cause the cause of the exception (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method)
     */
    public RetryableException(Throwable cause) {
        super(cause);
    }

}
