package org.thewhitemage13.exception;

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
