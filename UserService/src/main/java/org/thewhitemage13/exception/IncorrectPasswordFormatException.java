package org.thewhitemage13.exception;

/**
 * Exception thrown when a password does not meet the required format or policy.
 * <p>
 * This exception is typically used during user registration or updates to enforce password rules.
 * </p>
 *
 * @see RuntimeException
 */
public class IncorrectPasswordFormatException extends RuntimeException{
    public IncorrectPasswordFormatException() {
        super();
    }

    public IncorrectPasswordFormatException(String message) {
        super(message);
    }

    public IncorrectPasswordFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPasswordFormatException(Throwable cause) {
        super(cause);
    }
}
