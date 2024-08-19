package org.thewhitemage13.interfaces;

import org.thewhitemage13.dao.CreateUserDao;
import org.thewhitemage13.dao.GetUserDao;
import org.thewhitemage13.entity.User;

public interface UserProcessorInterface {
    void createProcessor(CreateUserDao createUserDao, User updateUser, String phoneNumber);
    void getProcessor(GetUserDao getUserDao, User user);
}
