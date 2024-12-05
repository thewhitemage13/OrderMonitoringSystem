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
class UserValidationServiceTest {
    @Mock
    private EmailValidationService emailValidationService;
    @Mock
    private PhoneValidationService phoneValidationService;
    @InjectMocks
    private UserValidationService userValidationService;

    @Test
    void validateUserForUpdate_EmailAndPhoneChanged_ValidatesBoth() throws EmailBusyException, NumberParseException {
        // Given
        User existingUser = new User();
        existingUser.setEmail("old@example.com");
        existingUser.setPhone("1234567890");

        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("new@example.com");
        createUserDTO.setPhone("0987654321");
        createUserDTO.setRegion("US");

        Mockito.doNothing().when(emailValidationService).validateUniqueEmail(createUserDTO.getEmail());
        Mockito.when(phoneValidationService.validateUniquePhoneNumber(createUserDTO.getPhone(), createUserDTO.getRegion()))
                .thenReturn("+1 098-765-4321");

        // When
        String resultPhone = userValidationService.validateUserForUpdate(existingUser, createUserDTO);

        // Then
        assertNotNull(resultPhone);
        assertEquals("+1 098-765-4321", resultPhone);

        Mockito.verify(emailValidationService).validateUniqueEmail(createUserDTO.getEmail());
        Mockito.verify(phoneValidationService).validateUniquePhoneNumber(createUserDTO.getPhone(), createUserDTO.getRegion());
    }

    @Test
    void validateUserForUpdate_OnlyEmailChanged_ValidatesEmail() throws EmailBusyException, NumberParseException {
        // Given
        User existingUser = new User();
        existingUser.setEmail("old@example.com");
        existingUser.setPhone("1234567890");

        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("new@example.com");
        createUserDTO.setPhone("1234567890");
        createUserDTO.setRegion("US");

        Mockito.doNothing().when(emailValidationService).validateUniqueEmail(createUserDTO.getEmail());
        Mockito.when(phoneValidationService.validatePhoneNumberForUpdate(createUserDTO.getPhone(), createUserDTO.getRegion()))
                .thenReturn("+1 123-456-7890");

        // When
        String resultPhone = userValidationService.validateUserForUpdate(existingUser, createUserDTO);

        // Then
        assertNotNull(resultPhone);
        assertEquals("+1 123-456-7890", resultPhone);

        Mockito.verify(emailValidationService).validateUniqueEmail(createUserDTO.getEmail());
        Mockito.verify(phoneValidationService).validatePhoneNumberForUpdate(createUserDTO.getPhone(), createUserDTO.getRegion());
    }

    @Test
    void validateUserForUpdate_OnlyPhoneChanged_ValidatesPhone() throws EmailBusyException, NumberParseException {
        // Given
        User existingUser = new User();
        existingUser.setEmail("old@example.com");
        existingUser.setPhone("1234567890");

        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("old@example.com");
        createUserDTO.setPhone("0987654321");
        createUserDTO.setRegion("US");

        Mockito.doNothing().when(emailValidationService).validateEmailBeforeUpdate(createUserDTO.getEmail());
        Mockito.when(phoneValidationService.validateUniquePhoneNumber(createUserDTO.getPhone(), createUserDTO.getRegion()))
                .thenReturn("+1 098-765-4321");

        // When
        String resultPhone = userValidationService.validateUserForUpdate(existingUser, createUserDTO);

        // Then
        assertNotNull(resultPhone);
        assertEquals("+1 098-765-4321", resultPhone);

        Mockito.verify(emailValidationService).validateEmailBeforeUpdate(createUserDTO.getEmail());
        Mockito.verify(phoneValidationService).validateUniquePhoneNumber(createUserDTO.getPhone(), createUserDTO.getRegion());
    }

    @Test
    void validateUserForUpdate_InvalidEmail_ThrowsException() throws Exception {
        // Given
        User existingUser = new User();
        existingUser.setEmail("old@example.com");
        existingUser.setPhone("1234567890");

        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("new@example.com");
        createUserDTO.setPhone("1234567890");
        createUserDTO.setRegion("US");

        Mockito.doThrow(new EmailBusyException("Email is already in use"))
                .when(emailValidationService).validateUniqueEmail(createUserDTO.getEmail());

        // When / Then
        EmailBusyException exception = assertThrows(EmailBusyException.class,
                () -> userValidationService.validateUserForUpdate(existingUser, createUserDTO));
        assertEquals("Email is already in use", exception.getMessage());

        Mockito.verify(emailValidationService).validateUniqueEmail(createUserDTO.getEmail());
        Mockito.verifyNoInteractions(phoneValidationService);
    }

    @Test
    void validateUserForUpdate_InvalidPhoneNumber_ThrowsException() throws EmailBusyException, NumberParseException {
        // Given
        User existingUser = new User();
        existingUser.setEmail("old@example.com");
        existingUser.setPhone("1234567890");

        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("old@example.com");
        createUserDTO.setPhone("invalid-phone");
        createUserDTO.setRegion("US");

        Mockito.doNothing().when(emailValidationService).validateEmailBeforeUpdate(createUserDTO.getEmail());
        Mockito.doThrow(new NumberParseException(NumberParseException.ErrorType.NOT_A_NUMBER, "Invalid phone number"))
                .when(phoneValidationService).validateUniquePhoneNumber(createUserDTO.getPhone(), createUserDTO.getRegion());

        // When / Then
        NumberParseException exception = assertThrows(NumberParseException.class,
                () -> userValidationService.validateUserForUpdate(existingUser, createUserDTO));
        assertEquals("Invalid phone number", exception.getMessage());

        Mockito.verify(emailValidationService).validateEmailBeforeUpdate(createUserDTO.getEmail());
        Mockito.verify(phoneValidationService).validateUniquePhoneNumber(createUserDTO.getPhone(), createUserDTO.getRegion());
    }
}