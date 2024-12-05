package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.PhoneNumberAlreadyTakenException;
import org.thewhitemage13.exception.UserNotFoundException;

import java.util.List;

/**
 * Service interface for user management operations.
 * <p>
 * Provides methods to create, retrieve, update, and delete users in the system.
 * Supports validation of user data and handles exceptions related to business rules.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>User creation with validation for email and phone number uniqueness.</li>
 *     <li>Retrieval of user data by ID and in bulk.</li>
 *     <li>Updating user data with comprehensive validation.</li>
 *     <li>Deleting users by ID.</li>
 * </ul>
 *
 * @see org.thewhitemage13.dto.CreateUserDTO
 * @see org.thewhitemage13.dto.GetUserDTO
 * @see org.thewhitemage13.entity.User
 * @see org.thewhitemage13.exception.UserNotFoundException
 * @see org.thewhitemage13.exception.EmailBusyException
 * @see org.thewhitemage13.exception.PhoneNumberAlreadyTakenException
 * @see com.google.i18n.phonenumbers.NumberParseException
 * @see java.util.List
 * @see java.lang.Long
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserServiceInterface {

    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the ID of the user to check
     * @return {@code true} if the user exists, {@code false} otherwise
     */
    boolean checkUserId(Long userId);

    /**
     * Creates a new user in the system.
     *
     * @param createUserDao the DTO containing user creation data
     * @throws EmailBusyException if the email is already in use
     * @throws PhoneNumberAlreadyTakenException if the phone number is already in use
     * @throws NumberParseException if the phone number format is invalid
     */
    void createUser(CreateUserDTO createUserDao) throws EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException;

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     * @throws UserNotFoundException if no user exists with the given ID
     */
    void deleteUser(Long userId) throws UserNotFoundException;

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return a DTO containing user information
     * @throws UserNotFoundException if no user exists with the given ID
     */
    GetUserDTO getUserById(Long userId) throws UserNotFoundException;

    /**
     * Retrieves all users in the system.
     *
     * @return a list of DTOs containing user information
     */
    List<GetUserDTO> getAllUsers();

    /**
     * Updates a user's information by their ID.
     *
     * @param userId the ID of the user to update
     * @param createUserDao the DTO containing updated user data
     * @throws UserNotFoundException if no user exists with the given ID
     * @throws EmailBusyException if the new email is already in use
     * @throws PhoneNumberAlreadyTakenException if the new phone number is already in use
     * @throws NumberParseException if the phone number format is invalid
     */
    void updateUser(Long userId, CreateUserDTO createUserDao) throws UserNotFoundException, EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException ;
}
