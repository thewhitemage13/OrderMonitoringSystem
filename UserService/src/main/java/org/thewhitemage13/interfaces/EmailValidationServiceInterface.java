package org.thewhitemage13.interfaces;

import org.thewhitemage13.exception.EmailBusyException;

/**
 * Service interface for email validation.
 * <p>
 * This interface provides methods for validating email addresses in the system.
 * It ensures that email addresses comply with uniqueness requirements and other constraints
 * before updates or creation.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates an email address for uniqueness before updating.</li>
 *     <li>Checks if an email is already in use during user creation or update.</li>
 *     <li>Throws specific exceptions for email conflicts.</li>
 * </ul>
 *
 * @see org.thewhitemage13.exception.EmailBusyException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface EmailValidationServiceInterface {

    /**
     * Validates the given email address before updating a user's email.
     * <p>
     * Ensures that the provided email is not already associated with another user in the system.
     * Throws an exception if the email is in use.
     * </p>
     *
     * @param checkEmail the email address to validate
     * @throws EmailBusyException if the email address is already in use
     */
    void validateEmailBeforeUpdate(String checkEmail) throws EmailBusyException;

    /**
     * Validates that the given email address is unique.
     * <p>
     * This method is used to verify that the provided email address is not associated
     * with any existing user in the system.
     * </p>
     *
     * @param checkEmail the email address to validate
     * @throws EmailBusyException if the email address is already in use
     */
    void validateUniqueEmail(String checkEmail) throws EmailBusyException;
}
