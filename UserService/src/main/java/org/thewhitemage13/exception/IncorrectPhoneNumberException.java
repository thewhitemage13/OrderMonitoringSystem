package org.thewhitemage13.exception;

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
