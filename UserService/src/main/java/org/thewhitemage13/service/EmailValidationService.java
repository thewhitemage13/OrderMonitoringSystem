package org.thewhitemage13.service;

import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.IncorrectEmailFormatException;
import org.thewhitemage13.interfaces.EmailValidationServiceInterface;
import org.thewhitemage13.repository.UserRepository;

@Service
public class EmailValidationService implements EmailValidationServiceInterface {
    private final UserRepository userRepository;
    private final EmailValidator validator = EmailValidator.getInstance();

    @Autowired
    public EmailValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validateUpdateEmail(String checkEmail) throws EmailBusyException {
        validator.isValid(checkEmail);
        if(!validator.isValid(checkEmail)) {
            throw new IncorrectEmailFormatException("Incorrect email format");
        }
    }

    @Override
    public void validateEmail(String checkEmail) throws EmailBusyException {
        if(userRepository.existsByEmail(checkEmail)) {
            throw new EmailBusyException("Email = %s is already taken".formatted(checkEmail));
        }
        validator.isValid(checkEmail);
        if(!validator.isValid(checkEmail)) {
            throw new IncorrectEmailFormatException("Incorrect email format");
        }
    }
}
