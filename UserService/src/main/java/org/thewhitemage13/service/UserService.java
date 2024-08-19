package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.dao.CreateUserDao;
import org.thewhitemage13.dao.GetUserDao;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.PhoneNumberAlreadyTakenException;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.interfaces.UserServiceInterface;
import org.thewhitemage13.processor.UserProcessor;
import org.thewhitemage13.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final KafkaTemplate<Long, Object> kafkaTemplate;
    private final ValidationService validationService;
    private final UserProcessor userProcessor;

    @Autowired
    public UserService(UserRepository userRepository, KafkaTemplate<Long, Object> kafkaTemplate, ValidationService validationService, UserProcessor userProcessor) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.validationService = validationService;
        this.userProcessor = userProcessor;
    }

    @Override
    public boolean checkUserId(Long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void createUser(CreateUserDao createUserDao) throws EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException {
        try {
            validationService.validateEmail(createUserDao.getEmail());
            validationService.validatePassword(createUserDao.getPassword());
            String phoneNumber = validationService.validatePhone(createUserDao.getPhone(), createUserDao.getRegion());
            User registerUser = new User();
            userProcessor.createProcessor(createUserDao, registerUser, phoneNumber);
            userRepository.save(registerUser);
            kafkaTemplate.send("user.created", registerUser.getId());
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new PhoneNumberAlreadyTakenException("Phone number = %s is already taken".formatted(createUserDao.getPhone()));
            }
        }
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        User deleteUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        userRepository.delete(deleteUser);
    }

    @Override
    public GetUserDao getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        GetUserDao getUserDao = new GetUserDao();
        userProcessor.getProcessor(getUserDao, user);
        return getUserDao;
    }

    @Override
    public List<GetUserDao> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<GetUserDao> getUserDaos = new ArrayList<>();
        for(User user : users) {
            GetUserDao getUserDao = new GetUserDao();
            userProcessor.getProcessor(getUserDao, user);
            getUserDaos.add(getUserDao);
        }
        return getUserDaos;
    }

    @Override
    public void updateUser(Long userId, CreateUserDao createUserDao) throws UserNotFoundException, EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException {
        User updateUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        validationService.validatePassword(createUserDao.getPassword());
        String phoneNum = validationService.validateUpdateUser(updateUser, createUserDao);
        userProcessor.createProcessor(createUserDao, updateUser, phoneNum);
        userRepository.save(updateUser);
    }
}
