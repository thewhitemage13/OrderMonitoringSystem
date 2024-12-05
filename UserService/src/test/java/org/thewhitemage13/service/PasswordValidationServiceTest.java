package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.thewhitemage13.exception.IncorrectPasswordFormatException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidationServiceTest {
    private final PasswordValidationService passwordValidationService = new PasswordValidationService();

    @Test
    void validatePasswordFormat_WithValidPassword_DoesNotThrowException() {
        // Given
        String validPassword = "Valid123!";

        // When / Then
        assertDoesNotThrow(() -> passwordValidationService.validatePasswordFormat(validPassword));
    }

    @Test
    void validatePasswordFormat_WithNoUpperCase_ThrowsIncorrectPasswordFormatException() {
        // Given
        String invalidPassword = "valid123!";

        // When / Then
        Exception exception = assertThrows(IncorrectPasswordFormatException.class,
                () -> passwordValidationService.validatePasswordFormat(invalidPassword));

        assertEquals("Incorrect password format", exception.getMessage());
    }

    @Test
    void validatePasswordFormat_WithNoDigit_ThrowsIncorrectPasswordFormatException() {
        // Given
        String invalidPassword = "InvalidPassword!";

        // When / Then
        Exception exception = assertThrows(IncorrectPasswordFormatException.class,
                () -> passwordValidationService.validatePasswordFormat(invalidPassword));

        assertEquals("Incorrect password format", exception.getMessage());
    }

    @Test
    void validatePasswordFormat_WithNoSpecialCharacter_ThrowsIncorrectPasswordFormatException() {
        // Given
        String invalidPassword = "Invalid123";

        // When / Then
        Exception exception = assertThrows(IncorrectPasswordFormatException.class,
                () -> passwordValidationService.validatePasswordFormat(invalidPassword));

        assertEquals("Incorrect password format", exception.getMessage());
    }

    @Test
    void validatePasswordFormat_WithWhitespace_ThrowsIncorrectPasswordFormatException() {
        // Given
        String invalidPassword = "Invalid 123!";

        // When / Then
        Exception exception = assertThrows(IncorrectPasswordFormatException.class,
                () -> passwordValidationService.validatePasswordFormat(invalidPassword));

        assertEquals("Incorrect password format", exception.getMessage());
    }
}