package org.thewhitemage13.service;

import org.passay.*;
import org.springframework.stereotype.Service;
import org.thewhitemage13.exception.IncorrectPasswordFormatException;
import org.thewhitemage13.interfaces.PasswordValidationServiceInterface;

/**
 * Service for validating password format.
 * <p>
 * This service handles password format validation by applying several rules to ensure
 * that the password meets certain security criteria. It uses the {@link PasswordValidator}
 * class from the Passay library to validate the password according to rules such as:
 * <ul>
 *     <li>At least one uppercase letter</li>
 *     <li>At least one digit</li>
 *     <li>At least one special character</li>
 *     <li>No whitespace characters</li>
 * </ul>
 * If the password does not meet these criteria, an {@link IncorrectPasswordFormatException}
 * is thrown.
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates password for complexity, including upper case letters, digits, and special characters.</li>
 *     <li>Ensures that the password does not contain any whitespace characters.</li>
 *     <li>Throws a custom exception when the password does not meet the required format.</li>
 * </ul>
 *
 * @see org.passay.PasswordValidator
 * @see org.passay.CharacterRule
 * @see org.passay.EnglishCharacterData
 * @see org.passay.WhitespaceRule
 * @see org.thewhitemage13.exception.IncorrectPasswordFormatException
 * @see org.thewhitemage13.interfaces.PasswordValidationServiceInterface
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class PasswordValidationService implements PasswordValidationServiceInterface {

    /**
     * Validates the format of the given password.
     * <p>
     * This method checks the password against several security rules to ensure it meets the required
     * format. The rules include:
     * <ul>
     *     <li>At least one uppercase letter</li>
     *     <li>At least one digit</li>
     *     <li>At least one special character</li>
     *     <li>No whitespace characters</li>
     * </ul>
     * If the password does not satisfy these rules, an {@link IncorrectPasswordFormatException}
     * is thrown.
     *
     * @param password the password to be validated
     * @throws IncorrectPasswordFormatException if the password does not meet the required format
     */
    @Override
    public void validatePasswordFormat(String password) {
        PasswordValidator validator = new PasswordValidator(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        );
        RuleResult result = validator.validate(new PasswordData(password));
        if (!result.isValid()) {
            throw new IncorrectPasswordFormatException("Incorrect password format");
        }
    }
}
