package org.thewhitemage13.exception;

public class StatisticsNotFoundException extends RuntimeException {
    public StatisticsNotFoundException() {
        super();
    }

    public StatisticsNotFoundException(String message) {
        super(message);
    }

    public StatisticsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatisticsNotFoundException(Throwable cause) {
        super(cause);
    }
}
