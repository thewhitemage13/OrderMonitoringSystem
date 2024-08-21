package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.PhoneNumberAlreadyTakenException;
import org.thewhitemage13.exception.UserNotFoundException;

import java.util.List;

public interface UserServiceInterface {
    boolean checkUserId(Long userId);
    void createUser(CreateUserDTO createUserDao) throws EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException;
    void deleteUser(Long userId) throws UserNotFoundException;
    GetUserDTO getUserById(Long userId) throws UserNotFoundException;
    List<GetUserDTO> getAllUsers();
    void updateUser(Long userId, CreateUserDTO createUserDao) throws UserNotFoundException, EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException ;
}
