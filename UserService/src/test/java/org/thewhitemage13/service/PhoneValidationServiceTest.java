package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.exception.IncorrectPhoneNumberException;
import org.thewhitemage13.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PhoneValidationServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PhoneValidationService phoneValidationService;

    @Test
    void validatePhoneNumberForUpdate_WithValidPhoneNumber_ReturnsFormattedNumber() throws NumberParseException {
        // Given
        String phoneNum = "0688689615";
        String region = "UA";

        // When
        String formattedNumber = phoneValidationService.validatePhoneNumberForUpdate(phoneNum, region);

        // Then
        assertNotNull(formattedNumber);
        assertTrue(formattedNumber.startsWith("+3"));
    }

    @Test
    void validatePhoneNumberForUpdate_WithInvalidPhoneNumber_ThrowsIncorrectPhoneNumberException() {
        // Given
        String phoneNum = "0688689615";
        String region = "US";

        // When / Then
        Exception exception = assertThrows(IncorrectPhoneNumberException.class,
                () -> phoneValidationService.validatePhoneNumberForUpdate(phoneNum, region));

        assertEquals("Incorrect phone number", exception.getMessage());
    }

    @Test
    void validateUniquePhoneNumber_WithExistingPhoneNumber_ThrowsIncorrectPhoneNumberException() throws NumberParseException {
        // Given
        String phoneNum = "1234567890";
        String region = "US";
        Mockito.when(userRepository.existsByPhone(phoneNum)).thenReturn(true);

        // When / Then
        Exception exception = assertThrows(IncorrectPhoneNumberException.class,
                () -> phoneValidationService.validateUniquePhoneNumber(phoneNum, region));

        assertEquals("Phone number is already in use", exception.getMessage());
    }

    @Test
    void validateUniquePhoneNumber_WithUniquePhoneNumber_ReturnsFormattedNumber() throws NumberParseException {
        // Given
        String phoneNum = "0688689615";
        String region = "UA";
        Mockito.when(userRepository.existsByPhone(phoneNum)).thenReturn(false);

        // When
        String formattedNumber = phoneValidationService.validateUniquePhoneNumber(phoneNum, region);

        // Then
        assertNotNull(formattedNumber);
        assertTrue(formattedNumber.startsWith("+3"));
    }

    @Test
    void validatePhoneNumberForUpdate_WithInvalidRegion_ThrowsNumberParseException() {
        // Given
        String phoneNum = "1234567890";
        String region = "INVALID_REGION";

        // When / Then
        assertThrows(NumberParseException.class,
                () -> phoneValidationService.validatePhoneNumberForUpdate(phoneNum, region));
    }
}