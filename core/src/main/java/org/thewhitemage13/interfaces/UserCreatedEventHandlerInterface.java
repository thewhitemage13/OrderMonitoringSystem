package org.thewhitemage13.interfaces;

/**
 * Interface for handling user creation events.
 * <p>
 * This interface defines the method for processing events triggered when a new user is created. Implementations
 * of this interface are responsible for handling the necessary actions or workflows that need to take place
 * when a new user is created in the system.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #create()} - Processes the creation of a new user in the system.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * Implement this interface in components that need to respond to user creation events. For example, after a new user
 * is created, additional tasks may be required, such as sending a welcome email, creating associated records in other systems,
 * or triggering other workflows related to the user.
 * </p>
 *
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserCreatedEventHandlerInterface {

    /**
     * Method to handle the event when a new user is created.
     * <p>
     * This method is triggered when a new user is created in the system. The implementation of this method should
     * handle the necessary processing, such as sending notifications, creating associated records, or any other
     * actions required when a new user is registered.
     * </p>
     */
    void create();
}
