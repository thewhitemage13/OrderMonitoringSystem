package org.thewhitemage13.exception;

/**
 * Exception thrown when the specified email address is already in use.
 * <p>
 * This exception is typically used to indicate a conflict during user creation or update operations.
 * </p>
 *
 * @see Exception
 */
public class EmailBusyException extends Exception {

    /**
     * Constructs a new {@code EmailBusyException} with no detail message.
     */
    public EmailBusyException() {
        super();
    }

    /**
     * Constructs a new {@code EmailBusyException} with the specified detail message.
     *
     * @param message the detail message
     */
    public EmailBusyException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code EmailBusyException} with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public EmailBusyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code EmailBusyException} with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public EmailBusyException(Throwable cause) {
        super(cause);
    }
}
