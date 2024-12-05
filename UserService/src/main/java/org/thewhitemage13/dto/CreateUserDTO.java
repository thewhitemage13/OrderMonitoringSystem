package org.thewhitemage13.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a user.
 * <p>
 * This class serves as a container for user data during the user creation process.
 * It includes validation annotations to ensure data integrity and proper formatting.
 * </p>
 *
 * <h2>Fields:</h2>
 * <ul>
 *     <li><b>firstName</b>: The user's first name. Must be between 2 and 50 characters.</li>
 *     <li><b>surname</b>: The user's surname. Must be between 2 and 50 characters.</li>
 *     <li><b>lastname</b>: The user's last name. Must be between 2 and 50 characters.</li>
 *     <li><b>email</b>: The user's email address. Must be a valid email format.</li>
 *     <li><b>phone</b>: The user's phone number. Must not be blank.</li>
 *     <li><b>password</b>: The user's password. Must be between 6 and 100 characters.</li>
 *     <li><b>region</b>: The user's region. Must be between 2 and 100 characters.</li>
 * </ul>
 *
 * <h2>Validation:</h2>
 * <p>
 * The class uses Jakarta Bean Validation annotations to enforce constraints on the fields:
 * </p>
 * <ul>
 *     <li>{@link NotBlank}: Ensures that the field is not null or empty.</li>
 *     <li>{@link Size}: Specifies the allowed length range for string fields.</li>
 *     <li>{@link Email}: Ensures that the email field contains a valid email format.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This DTO is intended to be used in API endpoints that handle user creation. It provides
 * validated data for services and ensures that only properly formatted data reaches the application logic.
 * </p>
 *
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 * @see jakarta.validation.constraints.NotBlank
 * @see jakarta.validation.constraints.Size
 * @see jakarta.validation.constraints.Email
 *
 * @author Mukhammed
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    /**
     * The user's first name.
     * <p>
     * Must be between 2 and 50 characters.
     * </p>
     */
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    /**
     * The user's surname.
     * <p>
     * Must be between 2 and 50 characters.
     * </p>
     */
    @NotBlank(message = "Surname is required")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    /**
     * The user's last name.
     * <p>
     * Must be between 2 and 50 characters.
     * </p>
     */
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastname;

    /**
     * The user's email address.
     * <p>
     * Must be in a valid email format.
     * </p>
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    /**
     * The user's phone number.
     * <p>
     * Must not be blank.
     * </p>
     */
    @NotBlank(message = "Phone number is required")
    private String phone;

    /**
     * The user's password.
     * <p>
     * Must be between 6 and 100 characters.
     * </p>
     */
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    /**
     * The user's region.
     * <p>
     * Must be between 2 and 100 characters.
     * </p>
     */
    @NotBlank(message = "Region is required")
    @Size(min = 2, max = 100, message = "Region must be between 2 and 100 characters")
    private String region;

    /**
     * Returns a string representation of the object.
     *
     * @return a string containing the field values of this DTO
     */
    @Override
    public String toString() {
        return "CreateUserDao{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
