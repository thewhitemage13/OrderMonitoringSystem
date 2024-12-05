package org.thewhitemage13.exception;

/**
 * Custom exception thrown when a user is not found in the system.
 * <p>
 * This exception is used to indicate that a user with a specific ID or username could not be
 * found in the database. It extends {@link RuntimeException}, meaning it is an unchecked exception.
 * </p>
 *
 * <h2>Features:</h2>
 * <ul>
 *     <li>Multiple constructors that allow customization of the exception message and cause.</li>
 *     <li>Extends {@link RuntimeException} to represent an unchecked exception.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This exception is typically thrown in service or repository layers when a user lookup operation
 * (e.g., by ID or username) fails to find the requested user. It helps signal a missing user
 * in the system, which can then be handled or logged accordingly.
 * </p>
 *
 * <h2>Constructors:</h2>
 * <ul>
 *     <li>{@link UserNotFoundException()} - Default constructor without any message or cause.</li>
 *     <li>{@link UserNotFoundException(String message)} - Constructor with a custom error message.</li>
 * </ul>
 *
 * @see RuntimeException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Default constructor for {@link UserNotFoundException}.
     * Calls the superclass constructor with no arguments.
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Constructor with a custom message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with both a custom message and cause.
     *
     * @param message the detail message explaining the cause of the exception
     * @param cause the cause of the exception
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with a cause.
     *
     * @param cause the cause of the exception
     */
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
