package org.thewhitemage13.exception;

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
