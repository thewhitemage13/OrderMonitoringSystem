package org.thewhitemage13.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.service.UserStatisticService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCreatedEventHandlerTest {
    @Mock
    private UserStatisticService userStatisticService;
    @InjectMocks
    private UserCreatedEventHandler userCreatedEventHandler;

    @Test
    void shouldCallCreateUserStatisticWhenUserCreatedEventReceived() {
        userCreatedEventHandler.create();

        verify(userStatisticService, times(1)).createUserStatistic();
    }

    @Test
    void shouldThrowExceptionWhenUserStatisticServiceFails() {
        doThrow(new RuntimeException("Service failure")).when(userStatisticService).createUserStatistic();

        assertThrows(RuntimeException.class, () -> userCreatedEventHandler.create());
    }
}