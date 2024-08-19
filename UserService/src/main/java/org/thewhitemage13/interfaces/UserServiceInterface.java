package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dao.CreateUserDao;
import org.thewhitemage13.dao.GetUserDao;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.PhoneNumberAlreadyTakenException;
import org.thewhitemage13.exception.UserNotFoundException;

import java.util.List;

public interface UserServiceInterface {
    boolean checkUserId(Long userId);
    void createUser(CreateUserDao createUserDao) throws EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException;
    void deleteUser(Long userId) throws UserNotFoundException;
    GetUserDao getUserById(Long userId) throws UserNotFoundException;
    List<GetUserDao> getAllUsers();
    void updateUser(Long userId, CreateUserDao createUserDao) throws UserNotFoundException, EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException ;
}
