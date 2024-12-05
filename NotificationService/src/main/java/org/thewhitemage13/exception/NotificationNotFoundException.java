package org.thewhitemage13.exception;

/**
 * Custom exception class to indicate that a notification was not found.
 * <p>
 * This exception is typically thrown when an attempt is made to access a
 * notification that does not exist or cannot be found in the system.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Extends {@link Exception}, making it a checked exception.</li>
 *     <li>Can be constructed with a message, a cause, or both, providing flexibility for exception handling.</li>
 * </ul>
 *
 * @see Exception
 * @see Throwable
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class NotificationNotFoundException extends Exception {

    /**
     * Constructs a new {@link NotificationNotFoundException} with {@code null} as its detail message.
     * <p>
     * The cause is not initialized, and may be {@code null}.
     * </p>
     */
    public NotificationNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@link NotificationNotFoundException} with the specified detail message.
     * <p>
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     * </p>
     *
     * @param message the detail message explaining the reason for the exception
     */
    public NotificationNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link NotificationNotFoundException} with the specified detail message and cause.
     * <p>
     * This constructor allows chaining of exceptions to preserve the root cause.
     * </p>
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause the cause of the exception (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method)
     */
    public NotificationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link NotificationNotFoundException} with the specified cause.
     * <p>
     * The detail message is set to {@code (cause == null ? null : cause.toString())}.
     * </p>
     *
     * @param cause the cause of the exception (which is saved for later retrieval by the
     *              {@link Throwable#getCause()} method)
     */
    public NotificationNotFoundException(Throwable cause) {
        super(cause);
    }
}
