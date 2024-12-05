package org.thewhitemage13.exception;

/**
 * Exception thrown when an email address does not match the required format.
 * <p>
 * This exception is used for validation purposes.
 * </p>
 *
 * @see RuntimeException
 */
public class IncorrectEmailFormatException extends RuntimeException{

    public IncorrectEmailFormatException() {
        super();
    }

    public IncorrectEmailFormatException(String message) {
        super(message);
    }

    public IncorrectEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectEmailFormatException(Throwable cause) {
        super(cause);
    }
}
