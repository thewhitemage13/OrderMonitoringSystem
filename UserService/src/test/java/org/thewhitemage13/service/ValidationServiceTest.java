package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {
    @Mock
    private EmailValidationService emailValidationService;
    @Mock
    private PasswordValidationService passwordValidationService;
    @Mock
    private PhoneValidationService phoneValidationService;
    @Mock
    private UserValidationService userValidationService;
    @InjectMocks
    private ValidationService validationService;

    @Test
    void validateEmail_CallsEmailValidationService() throws EmailBusyException {
        // Given
        String email = "test@example.com";

        Mockito.doNothing().when(emailValidationService).validateUniqueEmail(email);

        // When
        validationService.validateEmail(email);

        // Then
        Mockito.verify(emailValidationService).validateUniqueEmail(email);
    }

    @Test
    void validateUpdateEmail_CallsEmailValidationBeforeUpdate() throws EmailBusyException {
        // Given
        String email = "update@example.com";

        Mockito.doNothing().when(emailValidationService).validateEmailBeforeUpdate(email);

        // When
        validationService.validateUpdateEmail(email);

        // Then
        Mockito.verify(emailValidationService).validateEmailBeforeUpdate(email);
    }

    @Test
    void validatePassword_CallsPasswordValidationService() {
        // Given
        String password = "StrongP@ssw0rd";

        Mockito.doNothing().when(passwordValidationService).validatePasswordFormat(password);

        // When
        validationService.validatePassword(password);

        // Then
        Mockito.verify(passwordValidationService).validatePasswordFormat(password);
    }

    @Test
    void validateNewPhone_CallsPhoneValidationService() throws NumberParseException {
        // Given
        String phone = "1234567890";
        String region = "US";
        String formattedPhone = "1234567890";

        Mockito.when(phoneValidationService.validateUniquePhoneNumber(phone, region)).thenReturn(formattedPhone);

        // When
        String result = validationService.validateNewPhone(phone, region);

        // Then
        assertEquals(formattedPhone, result);
        Mockito.verify(phoneValidationService).validateUniquePhoneNumber(phone, region);
    }

    @Test
    void validatePhoneForUpdate_CallsPhoneValidationForUpdate() throws NumberParseException {
        // Given
        String phone = "1234567890";
        String region = "US";
        String formattedPhone = "1234567890";

        Mockito.when(phoneValidationService.validatePhoneNumberForUpdate(phone, region)).thenReturn(formattedPhone);

        // When
        String result = validationService.validatePhoneForUpdate(phone, region);

        // Then
        assertEquals(formattedPhone, result);
        Mockito.verify(phoneValidationService).validatePhoneNumberForUpdate(phone, region);
    }

    @Test
    void validateUserForUpdate_CallsUserValidationService() throws EmailBusyException, NumberParseException {
        // Given
        User existingUser = new User();
        existingUser.setEmail("existing@example.com");
        existingUser.setPhone("1234567890");

        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("new@example.com");
        createUserDTO.setPhone("0987654321");
        createUserDTO.setRegion("US");

        String expectedPhone = "+1 098-765-4321";

        Mockito.when(userValidationService.validateUserForUpdate(existingUser, createUserDTO))
                .thenReturn(expectedPhone);

        // When
        String resultPhone = validationService.validateUserForUpdate(existingUser, createUserDTO);

        // Then
        assertEquals(expectedPhone, resultPhone);
        Mockito.verify(userValidationService).validateUserForUpdate(existingUser, createUserDTO);
    }

    @Test
    void validateNewPhone_ThrowsNumberParseException() throws NumberParseException {
        // Given
        String phone = "invalid-phone";
        String region = "US";

        Mockito.doThrow(new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "Invalid phone"))
                .when(phoneValidationService).validateUniquePhoneNumber(phone, region);

        // When / Then
        NumberParseException exception = assertThrows(NumberParseException.class,
                () -> validationService.validateNewPhone(phone, region));
        assertEquals("Invalid phone", exception.getMessage());

        Mockito.verify(phoneValidationService).validateUniquePhoneNumber(phone, region);
    }

    @Test
    void validateUpdateEmail_ThrowsEmailBusyException() throws EmailBusyException {
        // Given
        String email = "taken@example.com";

        Mockito.doThrow(new EmailBusyException("Email is already in use"))
                .when(emailValidationService).validateEmailBeforeUpdate(email);

        // When / Then
        EmailBusyException exception = assertThrows(EmailBusyException.class,
                () -> validationService.validateUpdateEmail(email));
        assertEquals("Email is already in use", exception.getMessage());

        Mockito.verify(emailValidationService).validateEmailBeforeUpdate(email);
    }
}