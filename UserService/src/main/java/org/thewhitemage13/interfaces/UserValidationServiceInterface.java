package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dao.CreateUserDao;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;

public interface UserValidationServiceInterface {
    String validateUpdateUser(User updateUser, CreateUserDao createUserDa) throws EmailBusyException, NumberParseException;
}
