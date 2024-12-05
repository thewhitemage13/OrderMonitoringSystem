package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.entity.User;

/**
 * Service interface for processing user-related data.
 * <p>
 * Provides methods for handling user creation and retrieval operations.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Processes data for creating a new user.</li>
 *     <li>Processes data for retrieving user information.</li>
 *     <li>Ensures integration with DTOs and user entities.</li>
 * </ul>
 *
 * @see org.thewhitemage13.dto.CreateUserDTO
 * @see org.thewhitemage13.dto.GetUserDTO
 * @see org.thewhitemage13.entity.User
 * @see org.thewhitemage13.exception.UserNotFoundException
 * @see org.thewhitemage13.exception.EmailBusyException
 * @see org.thewhitemage13.exception.PhoneNumberAlreadyTakenException
 * @see org.thewhitemage13.exception.UserNotFoundException
 * @see com.google.i18n.phonenumbers.NumberParseException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserProcessorInterface {

    /**
     * Processes the creation of a new user.
     * <p>
     * Integrates user data with phone number normalization and other required validations.
     * </p>
     *
     * @param createUserDao the DTO containing user creation data
     * @param updateUser the existing user entity, if applicable
     * @param phoneNumber the phone number to associate with the user
     */
    void createProcessor(CreateUserDTO createUserDao, User updateUser, String phoneNumber);

    /**
     * Processes the retrieval of user information.
     *
     * @param getUserDao the DTO containing data for user retrieval
     * @param user the user entity to process
     */
    void getProcessor(GetUserDTO getUserDao, User user);
}
