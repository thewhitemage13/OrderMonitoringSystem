package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;

public interface ValidationServiceInterface {
    void validateEmail(String email) throws EmailBusyException;
    void validateUpdateEmail(String email) throws EmailBusyException;
    String validateUpdateUser(User updateUser, CreateUserDTO createUserDa) throws NumberParseException, EmailBusyException;
    void validatePassword(String password);
    String validatePhone(String phone, String region) throws NumberParseException;
    String validateUpdatePhone(String phone, String region) throws NumberParseException;
}
