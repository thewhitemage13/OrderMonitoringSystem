package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.entity.User;

public interface UserProcessorInterface {
    void createProcessor(CreateUserDTO createUserDao, User updateUser, String phoneNumber);
    void getProcessor(GetUserDTO getUserDao, User user);
}
