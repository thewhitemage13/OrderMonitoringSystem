package org.thewhitemage13.processor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.interfaces.UserProcessorInterface;

/**
 * Processor for managing user-related data transformations.
 * <p>
 * This class implements the {@link UserProcessorInterface} and provides methods
 * to process the creation and retrieval of user data. It handles password encoding,
 * and populates {@link User} entities based on incoming data from DTOs.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Processes user data for creation, including encoding passwords.</li>
 *     <li>Transfers data from a {@link User} entity to a {@link GetUserDTO} for retrieval.</li>
 *     <li>Supports integration with various user-related operations in the system.</li>
 * </ul>
 *
 * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 * @see org.springframework.security.crypto.password.PasswordEncoder
 * @see org.thewhitemage13.dto.CreateUserDTO
 * @see org.thewhitemage13.dto.GetUserDTO
 * @see org.thewhitemage13.entity.User
 * @see org.thewhitemage13.interfaces.UserProcessorInterface
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
public class UserProcessor implements UserProcessorInterface {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Processes user data for creation, including encoding the password.
     * <p>
     * This method takes the user creation data from {@link CreateUserDTO}, encrypts the password,
     * and updates the provided {@link User} entity with the new values.
     * </p>
     *
     * @param createUserDao the DTO containing the new user data
     * @param updateUser the existing {@link User} entity to update
     * @param phoneNumber the user's phone number
     */
    @Override
    public void createProcessor(CreateUserDTO createUserDao, User updateUser, String phoneNumber) {
        updateUser.setFirstName(createUserDao.getFirstName());
        updateUser.setLastname(createUserDao.getLastname());
        updateUser.setSurname(createUserDao.getSurname());
        updateUser.setPassword(passwordEncoder.encode(createUserDao.getPassword()));
        updateUser.setEmail(createUserDao.getEmail());
        updateUser.setPhone(phoneNumber);
        updateUser.setRegion(createUserDao.getRegion());
    }

    /**
     * Processes user data for retrieval.
     * <p>
     * This method populates a {@link GetUserDTO} with data from the provided {@link User} entity.
     * </p>
     *
     * @param getUserDao the DTO to populate with user data
     * @param user the {@link User} entity containing the user data
     */
    @Override
    public void getProcessor(GetUserDTO getUserDao, User user) {
        getUserDao.setEmail(user.getEmail());
        getUserDao.setPhone(user.getPhone());
        getUserDao.setFirstName(user.getFirstName());
        getUserDao.setLastname(user.getLastname());
        getUserDao.setSurname(user.getSurname());
        getUserDao.setRegion(user.getRegion());
    }
}
