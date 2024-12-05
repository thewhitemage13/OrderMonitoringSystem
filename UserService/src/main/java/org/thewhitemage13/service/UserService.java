package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.PhoneNumberAlreadyTakenException;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.interfaces.UserServiceInterface;
import org.thewhitemage13.processor.UserProcessor;
import org.thewhitemage13.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for handling user-related operations.
 * <p>
 * This service provides various methods to manage users, including:
 * <ul>
 *     <li>Creating new users while validating email, password, and phone number.</li>
 *     <li>Retrieving user data by ID or fetching all users from the system.</li>
 *     <li>Updating user information, including email, password, and phone number.</li>
 *     <li>Deleting users from the system.</li>
 * </ul>
 * The class leverages caching with Spring's @Cacheable, @CacheEvict, and @CachePut annotations for optimizing repeated queries and updates.
 * It also integrates with Kafka for publishing events when a user is created.
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Support for creating, updating, retrieving, and deleting users.</li>
 *     <li>Email and phone number validation and uniqueness checks.</li>
 *     <li>Integration with Kafka for user creation events.</li>
 *     <li>Caching for optimized performance in fetching user data.</li>
 * </ul>
 *
 * @see UserRepository
 * @see UserProcessor
 * @see ValidationService
 * @see KafkaTemplate
 * @see CreateUserDTO
 * @see GetUserDTO
 * @see org.thewhitemage13.exception.UserNotFoundException
 * @see org.thewhitemage13.exception.EmailBusyException
 * @see org.thewhitemage13.exception.PhoneNumberAlreadyTakenException
 * @see org.springframework.cache.annotation.Cacheable
 * @see org.springframework.cache.annotation.CacheEvict
 * @see org.springframework.cache.annotation.CachePut
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Transactional
@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final KafkaTemplate<Long, Object> kafkaTemplate;
    private final ValidationService validationService;
    private final UserProcessor userProcessor;

    /**
     * Constructs a new instance of {@link UserService}.
     * <p>
     * Initializes the service with the required dependencies, including
     * {@link UserRepository} for database operations, {@link KafkaTemplate}
     * for Kafka message publishing, {@link ValidationService} for input validation,
     * and {@link UserProcessor} for processing user data.
     * </p>
     *
     * @param userRepository the user repository for database operations
     * @param kafkaTemplate the Kafka template for sending events
     * @param validationService the validation service for input validation
     * @param userProcessor the processor for user data transformation
     */
    @Autowired
    public UserService(UserRepository userRepository, KafkaTemplate<Long, Object> kafkaTemplate, ValidationService validationService, UserProcessor userProcessor) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.validationService = validationService;
        this.userProcessor = userProcessor;
    }

    /**
     * Checks whether a user exists by their ID.
     * <p>
     * This method queries the repository to check if a user with the specified ID exists in the database.
     * </p>
     *
     * @param userId the ID of the user to check
     * @return {@code true} if the user exists, {@code false} otherwise
     */
    @Override
    public boolean checkUserId(Long userId) {
        return userRepository.existsById(userId);
    }

    /**
     * Creates a new user in the system.
     * <p>
     * This method performs several steps to create a user:
     * <ul>
     *     <li>Validates the user's email, password, and phone number.</li>
     *     <li>Uses the {@link UserProcessor} to transform the input data into a user entity.</li>
     *     <li>Saves the user entity to the database and sends a Kafka event indicating user creation.</li>
     * </ul>
     * If the phone number is already taken, a {@link PhoneNumberAlreadyTakenException} is thrown.
     *
     * @param createUserDao the user data transfer object containing user details
     * @throws EmailBusyException if the email is already taken
     * @throws PhoneNumberAlreadyTakenException if the phone number is already in use
     * @throws NumberParseException if the phone number cannot be parsed
     */
    @CacheEvict(cacheNames = "users", key = "#createUserDao.email", beforeInvocation = true)
    @Override
    public void createUser(CreateUserDTO createUserDao) throws EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException {
        try {
            validationService.validateEmail(createUserDao.getEmail());
            validationService.validatePassword(createUserDao.getPassword());
            String phoneNumber = validationService.validateNewPhone(createUserDao.getPhone(), createUserDao.getRegion());
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

    /**
     * Deletes a user from the system by their ID.
     * <p>
     * This method checks if the user exists, and if so, deletes the user from the database.
     * If the user does not exist, a {@link UserNotFoundException} is thrown.
     * </p>
     *
     * @param userId the ID of the user to delete
     * @throws UserNotFoundException if the user with the specified ID is not found
     */
    @CacheEvict(cacheNames = "users", key = "#userId")
    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        User deleteUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        userRepository.delete(deleteUser);
    }

    /**
     * Retrieves a user by their ID.
     * <p>
     * This method queries the repository to find a user with the specified ID. If the user does not exist,
     * a {@link UserNotFoundException} is thrown.
     * </p>
     *
     * @param userId the ID of the user to retrieve
     * @return a {@link GetUserDTO} containing user information
     * @throws UserNotFoundException if the user with the specified ID is not found
     */
    @Cacheable(cacheNames = "users", key = "#userId")
    @Override
    public GetUserDTO getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        GetUserDTO getUserDao = new GetUserDTO();
        userProcessor.getProcessor(getUserDao, user);
        return getUserDao;
    }

    /**
     * Retrieves all users from the system.
     * <p>
     * This method queries the repository to find all users in the database. If no users are found,
     * an empty list is returned.
     * </p>
     *
     * @return a list of {@link GetUserDTO} objects containing user information
     */
    @Cacheable(cacheNames = "allUsers", unless = "#result.isEmpty()")
    @Override
    public List<GetUserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<GetUserDTO> getUserDaos = new ArrayList<>();
        for(User user : users) {
            GetUserDTO getUserDao = new GetUserDTO();
            userProcessor.getProcessor(getUserDao, user);
            getUserDaos.add(getUserDao);
        }
        return getUserDaos;
    }

    /**
     * Updates an existing user's information.
     * <p>
     * This method allows updating a user's details, including email, password, and phone number.
     * It validates the input, processes the updated data, and saves the changes to the database.
     * </p>
     *
     * @param userId the ID of the user to update
     * @param createUserDao the new user data
     * @throws UserNotFoundException if the user with the specified ID is not found
     * @throws EmailBusyException if the email is already taken
     * @throws PhoneNumberAlreadyTakenException if the phone number is already in use
     * @throws NumberParseException if the phone number cannot be parsed
     */
    @CachePut(cacheNames = "users", key = "#userId")
    @Override
    public void updateUser(Long userId, CreateUserDTO createUserDao) throws UserNotFoundException, EmailBusyException, PhoneNumberAlreadyTakenException, NumberParseException {
        User updateUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(userId)));
        validationService.validatePassword(createUserDao.getPassword());
        String phoneNum = validationService.validateUserForUpdate(updateUser, createUserDao);
        userProcessor.createProcessor(createUserDao, updateUser, phoneNum);
        userRepository.save(updateUser);
    }
}
