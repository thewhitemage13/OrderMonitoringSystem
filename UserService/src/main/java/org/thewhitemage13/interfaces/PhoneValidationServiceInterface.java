package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;

/**
 * Service interface for phone number validation.
 * <p>
 * Provides methods to validate and normalize phone numbers in the system,
 * ensuring compliance with international formats and uniqueness requirements.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates phone numbers for updates.</li>
 *     <li>Checks for uniqueness of phone numbers during user creation.</li>
 *     <li>Supports international phone number validation with region codes.</li>
 * </ul>
 *
 * @see com.google.i18n.phonenumbers.NumberParseException
 * @see org.thewhitemage13.exception.PhoneNumberAlreadyTakenException
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PhoneValidationServiceInterface {

    /**
     * Validates a phone number before updating user information.
     * <p>
     * Ensures that the phone number format is correct and optionally checks
     * if it is already in use.
     * </p>
     *
     * @param phoneNum the phone number to validate
     * @param region the region code for the phone number
     * @return a normalized phone number if valid
     * @throws NumberParseException if the phone number format is invalid
     */
    String validatePhoneNumberForUpdate(String phoneNum, String region) throws NumberParseException;

    /**
     * Validates the uniqueness of a phone number during user creation.
     *
     * @param phoneNum the phone number to validate
     * @param region the region code for the phone number
     * @return a normalized phone number if valid
     * @throws NumberParseException if the phone number format is invalid
     */
    String validateUniquePhoneNumber(String phoneNum, String region) throws NumberParseException;
}
