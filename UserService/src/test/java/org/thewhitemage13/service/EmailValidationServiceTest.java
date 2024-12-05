package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.IncorrectEmailFormatException;
import org.thewhitemage13.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailValidationServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EmailValidationService emailValidationService;

    @Test
    void validateEmailBeforeUpdate_WithValidEmail_DoesNotThrowException() {
        // given
        String validEmail = "test@example.com";

        // when / then
        assertDoesNotThrow(() -> emailValidationService.validateEmailBeforeUpdate(validEmail));
    }

    @Test
    void validateEmailBeforeUpdate_WithInvalidEmail_ThrowsIncorrectEmailFormatException() {
        // given
        String invalidEmail = "invalid-email";

        // when / then
        Exception exception = assertThrows(IncorrectEmailFormatException.class,
                () -> emailValidationService.validateEmailBeforeUpdate(invalidEmail));

        assertEquals("Incorrect email format", exception.getMessage());
    }

    @Test
    void validateUniqueEmail_WithUniqueValidEmail_DoesNotThrowException() {
        // given
        String validEmail = "unique@example.com";
        Mockito.when(userRepository.existsByEmail(validEmail)).thenReturn(false);

        // when / then
        assertDoesNotThrow(() -> emailValidationService.validateUniqueEmail(validEmail));
    }

    @Test
    void validateUniqueEmail_WithExistingEmail_ThrowsEmailBusyException() {
        // given
        String existingEmail = "taken@example.com";
        Mockito.when(userRepository.existsByEmail(existingEmail)).thenReturn(true);

        // when / then
        Exception exception = assertThrows(EmailBusyException.class,
                () -> emailValidationService.validateUniqueEmail(existingEmail));

        assertEquals("Email = taken@example.com is already taken", exception.getMessage());
    }

    @Test
    void validateUniqueEmail_WithInvalidEmail_ThrowsIncorrectEmailFormatException() {
        // given
        String invalidEmail = "not-an-email";
        Mockito.when(userRepository.existsByEmail(invalidEmail)).thenReturn(false);

        // when / then
        Exception exception = assertThrows(IncorrectEmailFormatException.class,
                () -> emailValidationService.validateUniqueEmail(invalidEmail));

        assertEquals("Incorrect email format", exception.getMessage());
    }
}