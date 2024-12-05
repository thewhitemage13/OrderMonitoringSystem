package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.stereotype.Service;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.interfaces.ValidationServiceInterface;

/**
 * Service class responsible for validating user-related data such as email, password, and phone number.
 * <p>
 * This service combines several validation services to provide unified validation functionality:
 * <ul>
 *     <li>Validates the email format and ensures it is unique when necessary.</li>
 *     <li>Validates the password format according to the required rules.</li>
 *     <li>Validates phone numbers and checks their uniqueness.</li>
 * </ul>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates email uniqueness and format.</li>
 *     <li>Validates the format of user passwords.</li>
 *     <li>Validates and formats phone numbers, ensuring uniqueness.</li>
 *     <li>Provides methods for validating data when updating user details.</li>
 * </ul>
 *
 * @see EmailValidationService
 * @see PasswordValidationService
 * @see PhoneValidationService
 * @see UserValidationService
 * @see org.thewhitemage13.dto.CreateUserDTO
 * @see org.thewhitemage13.entity.User
 * @see org.thewhitemage13.exception.EmailBusyException
 * @see org.thewhitemage13.exception.IncorrectPhoneNumberException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class ValidationService implements ValidationServiceInterface {
    private final EmailValidationService emailValidationService;
    private final PasswordValidationService passwordValidationService;
    private final PhoneValidationService phoneValidationService;
    private final UserValidationService userValidationService;

    /**
     * Constructs a new instance of {@link ValidationService}.
     * <p>
     * Initializes the service with the required dependencies:
     * <ul>
     *     <li>{@link EmailValidationService} for email validation</li>
     *     <li>{@link PasswordValidationService} for password validation</li>
     *     <li>{@link PhoneValidationService} for phone validation</li>
     *     <li>{@link UserValidationService} for user-related validation</li>
     * </ul>
     *
     * @param emailValidationService   the service for validating emails
     * @param passwordValidationService the service for validating passwords
     * @param phoneValidationService    the service for validating phone numbers
     * @param userValidationService     the service for validating user data
     */
    public ValidationService(EmailValidationService emailValidationService, PasswordValidationService passwordValidationService, PhoneValidationService phoneValidationService, UserValidationService userValidationService) {
        this.emailValidationService = emailValidationService;
        this.passwordValidationService = passwordValidationService;
        this.phoneValidationService = phoneValidationService;
        this.userValidationService = userValidationService;
    }

    /**
     * Validates the email by ensuring it is unique.
     * <p>
     * This method delegates the validation to {@link EmailValidationService}.
     * </p>
     *
     * @param email the email to validate
     * @throws EmailBusyException if the email is already taken
     */
    @Override
    public void validateEmail(String email) throws EmailBusyException {
        emailValidationService.validateUniqueEmail(email);
    }

    /**
     * Validates the email format before updating the user.
     * <p>
     * This method delegates the validation to {@link EmailValidationService}.
     * </p>
     *
     * @param email the email to validate
     * @throws EmailBusyException if the email is already taken
     */
    @Override
    public void validateUpdateEmail(String email) throws EmailBusyException {
        emailValidationService.validateEmailBeforeUpdate(email);
    }

    /**
     * Validates user data before updating.
     * <p>
     * This method checks if the email and phone number have been changed and validates them accordingly.
     * </p>
     *
     * @param updateUser    the current user being updated
     * @param createUserDa  the new user data
     * @return the validated phone number
     * @throws EmailBusyException if the email is already taken
     * @throws NumberParseException if the phone number is invalid
     */
    @Override
    public String validateUserForUpdate(User updateUser, CreateUserDTO createUserDa) throws NumberParseException, EmailBusyException {
        return userValidationService.validateUserForUpdate(updateUser, createUserDa);
    }

    /**
     * Validates the password format.
     * <p>
     * This method delegates the validation to {@link PasswordValidationService}.
     * </p>
     *
     * @param password the password to validate
     */
    @Override
    public void validatePassword(String password) {
        passwordValidationService.validatePasswordFormat(password);
    }

    /**
     * Validates a new phone number, ensuring it is unique.
     * <p>
     * This method delegates the validation to {@link PhoneValidationService}.
     * </p>
     *
     * @param phone  the phone number to validate
     * @param region the region for the phone number
     * @return the formatted phone number
     * @throws NumberParseException if the phone number is invalid
     */
    @Override
    public String validateNewPhone(String phone, String region) throws NumberParseException {
        phoneValidationService.validateUniquePhoneNumber(phone, region);
        return phone;
    }

    /**
     * Validates a phone number during an update, ensuring its format is correct.
     * <p>
     * This method delegates the validation to {@link PhoneValidationService}.
     * </p>
     *
     * @param phone  the phone number to validate
     * @param region the region for the phone number
     * @return the formatted phone number
     * @throws NumberParseException if the phone number is invalid
     */
    @Override
    public String validatePhoneForUpdate(String phone, String region) throws NumberParseException {
        phoneValidationService.validatePhoneNumberForUpdate(phone, region);
        return phone;
    }

}
