package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.User;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and
 * custom queries for the {@link User} entity. It is annotated with {@link Repository}
 * to indicate that it is a Spring Data repository component.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Provides basic CRUD operations for {@link User} entity.</li>
 *     <li>Custom query methods to check for existing users by email or phone number.</li>
 * </ul>
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see org.springframework.stereotype.Repository
 * @see org.thewhitemage13.entity.User
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks if a {@link User} exists with the specified email.
     * <p>
     * This method checks the database to determine if there is already a user
     * with the given email address.
     * </p>
     *
     * @param email the email to check for existence
     * @return true if a user with the provided email exists, otherwise false
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a {@link User} exists with the specified phone number.
     * <p>
     * This method checks the database to determine if there is already a user
     * with the given phone number.
     * </p>
     *
     * @param phoneNumber the phone number to check for existence
     * @return true if a user with the provided phone number exists, otherwise false
     */
    boolean existsByPhone(String phoneNumber);
}
