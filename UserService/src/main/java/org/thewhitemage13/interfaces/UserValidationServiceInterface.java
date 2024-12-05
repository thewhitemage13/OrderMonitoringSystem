package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;

/**
 * Service interface for validating user-related data.
 * <p>
 * Provides methods for validating user information during updates and ensuring
 * compliance with business rules for email and phone number validation.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates user information during updates.</li>
 *     <li>Ensures consistency and correctness of user data.</li>
 *     <li>Handles exceptions for email and phone number conflicts.</li>
 * </ul>
 *
 * @see org.thewhitemage13.dto.CreateUserDTO
 * @see org.thewhitemage13.entity.User
 * @see org.thewhitemage13.exception.EmailBusyException
 * @see com.google.i18n.phonenumbers.NumberParseException
 * @see org.thewhitemage13.exception.PhoneNumberAlreadyTakenException
 * @see org.thewhitemage13.exception.UserNotFoundException
 * @see com.google.i18n.phonenumbers.NumberParseException
 * @see java.lang.String
 * @see java.util.List
 * @see java.lang.Long
 * @see org.thewhitemage13.dto.GetUserDTO
 * @see org.thewhitemage13.exception.UserNotFoundException
 * @see com.google.i18n.phonenumbers.NumberParseException
 * @author Mukhammed Lolo
 * @version
 * 1.0.
 */
public interface UserValidationServiceInterface {

    /**
     * Validates user data for updates.
     * <p>
     * Ensures that the provided user data complies with system rules during an update operation.
     * This includes checks for email and phone number conflicts, as well as data format consistency.
     * </p>
     *
     * @param updateUser the existing user entity
     * @param createUserDa the DTO containing updated user data
     * @return a message indicating the validation result
     * @throws EmailBusyException if the email is already in use
     * @throws NumberParseException if the phone number format is invalid
     */
    String validateUserForUpdate(User updateUser, CreateUserDTO createUserDa) throws EmailBusyException, NumberParseException;
}
