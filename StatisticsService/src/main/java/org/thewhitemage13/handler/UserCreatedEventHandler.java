package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.interfaces.UserCreatedEventHandlerInterface;
import org.thewhitemage13.service.UserStatisticService;

@Component
public class UserCreatedEventHandler implements UserCreatedEventHandlerInterface {
    private final UserStatisticService userStatisticService;

    @Autowired
    public UserCreatedEventHandler(UserStatisticService userStatisticService) {
        this.userStatisticService = userStatisticService;
    }

    @Override
    @KafkaListener(topics = "user.created")
    public void create() {
        userStatisticService.createUserStatistic();
    }
}
