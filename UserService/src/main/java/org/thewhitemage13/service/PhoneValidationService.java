package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Service;
import org.thewhitemage13.exception.IncorrectPhoneNumberException;
import org.thewhitemage13.interfaces.PhoneValidationServiceInterface;
import org.thewhitemage13.repository.UserRepository;

/**
 * Service for validating and formatting phone numbers.
 * <p>
 * This service provides phone number validation and formatting functionalities. It uses
 * the {@link PhoneNumberUtil} class from the Google libphonenumber library to validate
 * phone numbers and ensure that they are in the correct format. It also checks whether
 * the phone number is already in use in the system.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates the phone number format for a given region.</li>
 *     <li>Formats the phone number into an international format.</li>
 *     <li>Checks whether a phone number is already in use in the system.</li>
 *     <li>Throws custom exceptions for invalid or already taken phone numbers.</li>
 * </ul>
 *
 * @see com.google.i18n.phonenumbers.PhoneNumberUtil
 * @see com.google.i18n.phonenumbers.Phonenumber
 * @see org.thewhitemage13.exception.IncorrectPhoneNumberException
 * @see org.thewhitemage13.repository.UserRepository
 * @see org.thewhitemage13.interfaces.PhoneValidationServiceInterface
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class PhoneValidationService implements PhoneValidationServiceInterface {
    private final UserRepository userRepository;

    /**
     * Constructs a new instance of {@link PhoneValidationService}.
     * <p>
     * This constructor initializes the service with the given {@link UserRepository}
     * to check if the phone number is already in use.
     * </p>
     *
     * @param userRepository the repository to check for existing phone numbers
     */
    public PhoneValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validates and formats the phone number for an update.
     * <p>
     * This method formats the phone number into an international format and checks whether
     * the phone number is valid for the given region. If the number is not valid, it throws
     * an {@link IncorrectPhoneNumberException}.
     * </p>
     *
     * @param phoneNum the phone number to be validated and formatted
     * @param region the region code (e.g., "US", "RU") for phone number validation
     * @return the formatted phone number in international format
     * @throws NumberParseException if the phone number cannot be parsed
     * @throws IncorrectPhoneNumberException if the phone number is invalid
     */
    @Override
    public String validatePhoneNumberForUpdate(String phoneNum, String region) throws NumberParseException {
        return formatAndValidatePhoneNumber(phoneNum, region);
    }

    /**
     * Validates the phone number format and checks for its uniqueness.
     * <p>
     * This method checks if the phone number is already in use in the system. If the phone number
     * is already taken, it throws an {@link IncorrectPhoneNumberException}. If the number is valid,
     * it formats it into the international format and returns the result.
     * </p>
     *
     * @param phoneNum the phone number to be validated and checked for uniqueness
     * @param region the region code (e.g., "US", "RU") for phone number validation
     * @return the formatted phone number in international format
     * @throws NumberParseException if the phone number cannot be parsed
     * @throws IncorrectPhoneNumberException if the phone number is invalid or already in use
     */
    @Override
    public String validateUniquePhoneNumber(String phoneNum, String region) throws NumberParseException {
        if (userRepository.existsByPhone(phoneNum)){
            throw new IncorrectPhoneNumberException("Phone number is already in use");
        }else {
            return formatAndValidatePhoneNumber(phoneNum, region);
        }
    }

    /**
     * Formats and validates the phone number.
     * <p>
     * This private helper method formats the phone number into an international format and checks if
     * the phone number is valid. If the phone number is invalid, it throws an {@link IncorrectPhoneNumberException}.
     * </p>
     *
     * @param phoneNum the phone number to be validated and formatted
     * @param region the region code for validation
     * @return the formatted phone number in international format
     * @throws NumberParseException if the phone number cannot be parsed
     * @throws IncorrectPhoneNumberException if the phone number is invalid
     */
    private static String formatAndValidatePhoneNumber(String phoneNum, String region) throws NumberParseException {
        String formattedNumber;
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phoneNum, region);
        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (!isValid) {
            throw new IncorrectPhoneNumberException("Incorrect phone number");
        }else {
            formattedNumber = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        }
        return formattedNumber;
    }
}
