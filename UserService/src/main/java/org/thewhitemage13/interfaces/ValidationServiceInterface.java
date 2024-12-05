package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;

/**
 * Comprehensive validation service interface for user-related operations.
 * <p>
 * Provides methods for validating email addresses, phone numbers, and passwords,
 * as well as ensuring the consistency and correctness of user data during updates or creation.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Email validation for uniqueness and updates.</li>
 *     <li>Password format validation.</li>
 *     <li>Phone number validation with support for international formats.</li>
 *     <li>Validation of user data integrity during updates.</li>
 * </ul>
 *
 * @see org.thewhitemage13.exception.EmailBusyException
 * @see com.google.i18n.phonenumbers.NumberParseException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface ValidationServiceInterface {

    /**
     * Validates the uniqueness of an email address during user creation.
     *
     * @param email the email address to validate
     * @throws EmailBusyException if the email is already in use
     */
    void validateEmail(String email) throws EmailBusyException;

    /**
     * Validates an email address before updating user information.
     *
     * @param email the email address to validate
     * @throws EmailBusyException if the email is already in use
     */
    void validateUpdateEmail(String email) throws EmailBusyException;

    /**
     * Validates the user data and updates consistency.
     *
     * @param updateUser the existing user entity
     * @param createUserDa the DTO containing updated user data
     * @return a validation message or status
     * @throws NumberParseException if phone number format is invalid
     * @throws EmailBusyException if the email is already in use
     */
    String validateUserForUpdate (User updateUser, CreateUserDTO createUserDa) throws NumberParseException, EmailBusyException;

    /**
     * Validates the password format for compliance with security rules.
     *
     * @param password the password to validate
     */
    void validatePassword(String password);

    /**
     * Validates a new phone number for user creation.
     *
     * @param phone the phone number to validate
     * @param region the region code for phone validation
     * @return a normalized phone number if valid
     * @throws NumberParseException if the phone number format is invalid
     */
    String validateNewPhone(String phone, String region) throws NumberParseException;

    /**
     * Validates a phone number for user update.
     *
     * @param phone the phone number to validate
     * @param region the region code for phone validation
     * @return a normalized phone number if valid
     * @throws NumberParseException if the phone number format is invalid
     */
    String validatePhoneForUpdate(String phone, String region) throws NumberParseException;
}
