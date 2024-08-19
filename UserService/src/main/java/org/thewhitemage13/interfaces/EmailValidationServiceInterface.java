package org.thewhitemage13.interfaces;

import org.thewhitemage13.exception.EmailBusyException;

public interface EmailValidationServiceInterface {
    void validateUpdateEmail(String checkEmail) throws EmailBusyException;
    void validateEmail(String checkEmail) throws EmailBusyException;
}
