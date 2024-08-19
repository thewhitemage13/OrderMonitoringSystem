package org.thewhitemage13.processor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.thewhitemage13.dao.CreateUserDao;
import org.thewhitemage13.dao.GetUserDao;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.interfaces.UserProcessorInterface;

@Component
public class UserProcessor implements UserProcessorInterface {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void createProcessor(CreateUserDao createUserDao, User updateUser, String phoneNumber) {
        updateUser.setFirstName(createUserDao.getFirstName());
        updateUser.setLastname(createUserDao.getLastname());
        updateUser.setSurname(createUserDao.getSurname());
        updateUser.setPassword(passwordEncoder.encode(createUserDao.getPassword()));
        updateUser.setEmail(createUserDao.getEmail());
        updateUser.setPhone(phoneNumber);
        updateUser.setRegion(createUserDao.getRegion());
    }

    @Override
    public void getProcessor(GetUserDao getUserDao, User user) {
        getUserDao.setEmail(user.getEmail());
        getUserDao.setPhone(user.getPhone());
        getUserDao.setFirstName(user.getFirstName());
        getUserDao.setLastname(user.getLastname());
        getUserDao.setSurname(user.getSurname());
        getUserDao.setRegion(user.getRegion());
    }
}
