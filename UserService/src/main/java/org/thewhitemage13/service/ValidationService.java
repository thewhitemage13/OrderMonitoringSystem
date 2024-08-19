package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.stereotype.Service;
import org.thewhitemage13.dao.CreateUserDao;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.interfaces.ValidationServiceInterface;

@Service
public class ValidationService implements ValidationServiceInterface {
    private final EmailValidationService emailValidationService;
    private final PasswordValidationService passwordValidationService;
    private final PhoneValidationService phoneValidationService;
    private final UserValidationService userValidationService;

    public ValidationService(EmailValidationService emailValidationService, PasswordValidationService passwordValidationService, PhoneValidationService phoneValidationService, UserValidationService userValidationService) {
        this.emailValidationService = emailValidationService;
        this.passwordValidationService = passwordValidationService;
        this.phoneValidationService = phoneValidationService;
        this.userValidationService = userValidationService;
    }

    @Override
    public void validateEmail(String email) throws EmailBusyException {
        emailValidationService.validateEmail(email);
    }

    @Override
    public void validateUpdateEmail(String email) throws EmailBusyException {
        emailValidationService.validateUpdateEmail(email);
    }

    @Override
    public String validateUpdateUser(User updateUser, CreateUserDao createUserDa) throws NumberParseException, EmailBusyException {
        return userValidationService.validateUpdateUser(updateUser, createUserDa);
    }

    @Override
    public void validatePassword(String password) {
        passwordValidationService.validatePassword(password);
    }

    @Override
    public String validatePhone(String phone, String region) throws NumberParseException {
        phoneValidationService.validatePhoneNumber(phone, region);
        return phone;
    }

    @Override
    public String validateUpdatePhone(String phone, String region) throws NumberParseException {
        phoneValidationService.validateUpdatePhoneNumber(phone, region);
        return phone;
    }

}
