package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.interfaces.UserCreatedEventHandlerInterface;
import org.thewhitemage13.service.UserStatisticService;

/**
 * Handles the {@link org.thewhitemage13.UserCreatedEvent} by creating user statistics.
 * <p>
 * This class listens to events from the Kafka topic "user.created" and processes them by creating statistics for new users
 * through the {@link UserStatisticService}.
 * </p>
 *
 * @see UserStatisticService
 * @see UserCreatedEventHandlerInterface
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
public class UserCreatedEventHandler implements UserCreatedEventHandlerInterface {
    private final UserStatisticService userStatisticService;

    /**
     * Constructor for {@link UserCreatedEventHandler}.
     * <p>
     * Initializes the handler with the {@link UserStatisticService}, which will be used to create user statistics.
     * </p>
     *
     * @param userStatisticService the service to create user statistics
     */
    @Autowired
    public UserCreatedEventHandler(UserStatisticService userStatisticService) {
        this.userStatisticService = userStatisticService;
    }

    /**
     * Handles the event of a user being created.
     * <p>
     * This method listens to the "user.created" topic and calls the {@link UserStatisticService#createUserStatistic()} method
     * to create statistics for the new user.
     * </p>
     */
    @Override
    @KafkaListener(topics = "user.created")
    public void create() {
        userStatisticService.createUserStatistic();
    }
}
