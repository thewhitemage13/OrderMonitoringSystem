package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.stereotype.Service;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.interfaces.UserValidationServiceInterface;

@Service
public class UserValidationService implements UserValidationServiceInterface {
    private final EmailValidationService emailValidationService;
    private final PhoneValidationService phoneValidationService;

    public UserValidationService(EmailValidationService emailValidationService, PhoneValidationService phoneValidationService) {
        this.emailValidationService = emailValidationService;
        this.phoneValidationService = phoneValidationService;
    }

    @Override
    public String validateUpdateUser(User updateUser, CreateUserDTO createUserDa) throws EmailBusyException, NumberParseException {
        if (!updateUser.getEmail().equals(createUserDa.getEmail())) {
            emailValidationService.validateEmail(createUserDa.getEmail());
        } else {
            emailValidationService.validateUpdateEmail(createUserDa.getEmail());
        }
        String phoneNum;
        if (!updateUser.getPhone().equals(createUserDa.getPhone())) {
            phoneNum = phoneValidationService.validatePhoneNumber(createUserDa.getPhone(), createUserDa.getRegion());
        } else {
            phoneNum = phoneValidationService.validateUpdatePhoneNumber(createUserDa.getPhone(), createUserDa.getRegion());
        }
        return phoneNum;
    }
}
