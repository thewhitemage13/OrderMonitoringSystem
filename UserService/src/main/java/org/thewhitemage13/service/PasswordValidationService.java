package org.thewhitemage13.service;

import org.passay.*;
import org.springframework.stereotype.Service;
import org.thewhitemage13.exception.IncorrectPasswordFormatException;
import org.thewhitemage13.interfaces.PasswordValidationServiceInterface;

@Service
public class PasswordValidationService implements PasswordValidationServiceInterface {

    @Override
    public void validatePassword(String password) {
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
