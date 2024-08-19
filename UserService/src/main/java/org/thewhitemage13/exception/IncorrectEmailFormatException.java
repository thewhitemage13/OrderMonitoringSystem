package org.thewhitemage13.exception;

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
