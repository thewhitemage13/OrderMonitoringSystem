package org.thewhitemage13.service;

import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.IncorrectEmailFormatException;
import org.thewhitemage13.interfaces.EmailValidationServiceInterface;
import org.thewhitemage13.repository.UserRepository;

/**
 * Service for validating email addresses.
 * <p>
 * This service handles email validation operations, including checking whether
 * an email is correctly formatted and whether the email is already in use by another user.
 * It uses {@link EmailValidator} to validate the email format and the {@link UserRepository}
 * to check the uniqueness of the email.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates email format using Apache Commons Validator.</li>
 *     <li>Checks if an email is already taken in the system.</li>
 *     <li>Throws custom exceptions if the email is invalid or already in use.</li>
 * </ul>
 *
 * @see org.apache.commons.validator.EmailValidator
 * @see org.thewhitemage13.exception.EmailBusyException
 * @see org.thewhitemage13.exception.IncorrectEmailFormatException
 * @see org.thewhitemage13.repository.UserRepository
 * @see org.thewhitemage13.interfaces.EmailValidationServiceInterface
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class EmailValidationService implements EmailValidationServiceInterface {
    private final UserRepository userRepository;
    private final EmailValidator validator = EmailValidator.getInstance();

    /**
     * Constructs a new instance of {@link EmailValidationService}.
     * <p>
     * This constructor initializes the service with the given {@link UserRepository}
     * to check for email uniqueness in the database.
     * </p>
     *
     * @param userRepository the repository to check for existing emails
     */
    @Autowired
    public EmailValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validates the email format before updating.
     * <p>
     * This method checks if the email is valid according to the {@link EmailValidator}.
     * If the format is incorrect, it throws an {@link IncorrectEmailFormatException}.
     * </p>
     *
     * @param checkEmail the email to be validated
     * @throws EmailBusyException if the email is already in use (this exception is not used in this method)
     * @throws IncorrectEmailFormatException if the email format is invalid
     */
    @Override
    public void validateEmailBeforeUpdate(String checkEmail) throws EmailBusyException {
        validator.isValid(checkEmail);
        if(!validator.isValid(checkEmail)) {
            throw new IncorrectEmailFormatException("Incorrect email format");
        }
    }

    /**
     * Validates that the email is both correctly formatted and unique.
     * <p>
     * This method first checks if the email already exists in the system. If the email is taken,
     * it throws an {@link EmailBusyException}. If the email format is invalid, it throws an
     * {@link IncorrectEmailFormatException}.
     * </p>
     *
     * @param checkEmail the email to be validated
     * @throws EmailBusyException if the email is already taken
     * @throws IncorrectEmailFormatException if the email format is invalid
     */
    @Override
    public void validateUniqueEmail(String checkEmail) throws EmailBusyException {
        if(userRepository.existsByEmail(checkEmail)) {
            throw new EmailBusyException("Email = %s is already taken".formatted(checkEmail));
        }
        validator.isValid(checkEmail);
        if(!validator.isValid(checkEmail)) {
            throw new IncorrectEmailFormatException("Incorrect email format");
        }
    }
}
