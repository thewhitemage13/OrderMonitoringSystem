package org.thewhitemage13.exception;

/**
 * Exception thrown when a phone number is invalid or does not match the required format.
 * <p>
 * This exception ensures that only properly formatted phone numbers are processed.
 * </p>
 *
 * @see RuntimeException
 */
public class IncorrectPhoneNumberException extends RuntimeException {
    public IncorrectPhoneNumberException() {
        super();
    }

    public IncorrectPhoneNumberException(String message) {
        super(message);
    }

    public IncorrectPhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPhoneNumberException(Throwable cause) {
        super(cause);
    }
}
