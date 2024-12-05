package org.thewhitemage13.exception;

/**
 * Custom exception to indicate that statistics were not found.
 * <p>
 * This exception is thrown when an attempt is made to access statistics that do not exist.
 * It extends {@link RuntimeException}, making it an unchecked exception. This exception is typically
 * used in scenarios where the requested statistics are missing, deleted, or unavailable.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class StatisticsNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@link StatisticsNotFoundException} with no detail message or cause.
     * <p>
     * This constructor allows you to create an instance of the exception without any additional context.
     * </p>
     */
    public StatisticsNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@link StatisticsNotFoundException} with the specified detail message.
     * <p>
     * This constructor allows you to provide a specific error message explaining the cause of the exception.
     * </p>
     *
     * @param message the detail message explaining the exception
     */
    public StatisticsNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link StatisticsNotFoundException} with the specified detail message and cause.
     * <p>
     * This constructor allows you to provide both a message and the original {@link Throwable} that caused
     * this exception.
     * </p>
     *
     * @param message the detail message explaining the exception
     * @param cause the cause of the exception
     */
    public StatisticsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link StatisticsNotFoundException} with the specified cause.
     * <p>
     * This constructor allows you to pass the original {@link Throwable} that caused this exception.
     * </p>
     *
     * @param cause the cause of the exception
     */
    public StatisticsNotFoundException(Throwable cause) {
        super(cause);
    }
}
