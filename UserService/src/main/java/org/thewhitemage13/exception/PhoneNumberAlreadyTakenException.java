package org.thewhitemage13.exception;

/**
 * Exception thrown when the specified phone number is already registered to another user.
 * <p>
 * This exception helps enforce the uniqueness of phone numbers in the system.
 * </p>
 *
 * @see RuntimeException
 */
public class PhoneNumberAlreadyTakenException extends RuntimeException {
    public PhoneNumberAlreadyTakenException() {
        super();
    }

    public PhoneNumberAlreadyTakenException(String message) {
        super(message);
    }

    public PhoneNumberAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNumberAlreadyTakenException(Throwable cause) {
        super(cause);
    }
}
