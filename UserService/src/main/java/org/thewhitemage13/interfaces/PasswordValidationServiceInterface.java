package org.thewhitemage13.interfaces;

/**
 * Service interface for password validation.
 * <p>
 * Provides methods to validate the format and complexity of user passwords
 * to ensure security standards are met.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates the format of user passwords during account creation or updates.</li>
 *     <li>Ensures compliance with system password complexity requirements.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PasswordValidationServiceInterface {

    /**
     * Validates the format of the given password.
     * <p>
     * Checks if the password complies with the system's complexity rules, such as
     * minimum length, character variety, and other security requirements.
     * </p>
     *
     * @param password the password to validate
     */
    void validatePasswordFormat(String password);
}
