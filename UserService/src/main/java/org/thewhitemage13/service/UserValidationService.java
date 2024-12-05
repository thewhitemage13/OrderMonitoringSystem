package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.stereotype.Service;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.interfaces.UserValidationServiceInterface;

/**
 * Service class responsible for validating user data during updates.
 * <p>
 * This service validates the user's email and phone number when updating user information.
 * If the email or phone number is changed, it checks whether the new values are unique and valid.
 * If the email or phone number is not changed, it validates that the current values are in the correct format.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates the email format and ensures it is unique if updated.</li>
 *     <li>Validates the phone number format and ensures it is unique if updated.</li>
 *     <li>Ensures that the existing values are not wrongly modified.</li>
 * </ul>
 *
 * @see EmailValidationService
 * @see PhoneValidationService
 * @see User
 * @see CreateUserDTO
 * @see org.thewhitemage13.exception.EmailBusyException
 * @see org.thewhitemage13.exception.IncorrectPhoneNumberException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class UserValidationService implements UserValidationServiceInterface {
    private final EmailValidationService emailValidationService;
    private final PhoneValidationService phoneValidationService;

    /**
     * Constructs a new instance of {@link UserValidationService}.
     * <p>
     * Initializes the service with the required dependencies: {@link EmailValidationService}
     * for email validation and {@link PhoneValidationService} for phone validation.
     * </p>
     *
     * @param emailValidationService the service for validating emails
     * @param phoneValidationService the service for validating phone numbers
     */
    public UserValidationService(EmailValidationService emailValidationService, PhoneValidationService phoneValidationService) {
        this.emailValidationService = emailValidationService;
        this.phoneValidationService = phoneValidationService;
    }

    /**
     * Validates user data for updates, including email and phone number.
     * <p>
     * If the email is being changed, it validates the uniqueness and format of the new email.
     * If the phone number is being changed, it validates the uniqueness and format of the new phone number.
     * </p>
     *
     * @param updateUser the current user being updated
     * @param createUserDa the new user data
     * @return the validated phone number
     * @throws EmailBusyException if the new email is already in use
     * @throws NumberParseException if the phone number is invalid
     */
    @Override
    public String validateUserForUpdate(User updateUser, CreateUserDTO createUserDa) throws EmailBusyException, NumberParseException {
        if (!updateUser.getEmail().equals(createUserDa.getEmail())) {
            emailValidationService.validateUniqueEmail(createUserDa.getEmail());
        } else {
            emailValidationService.validateEmailBeforeUpdate(createUserDa.getEmail());
        }
        String phoneNum;
        if (!updateUser.getPhone().equals(createUserDa.getPhone())) {
            phoneNum = phoneValidationService.validateUniquePhoneNumber(createUserDa.getPhone(), createUserDa.getRegion());
        } else {
            phoneNum = phoneValidationService.validatePhoneNumberForUpdate(createUserDa.getPhone(), createUserDa.getRegion());
        }
        return phoneNum;
    }
}
