package org.thewhitemage13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for retrieving user information.
 * <p>
 * This class is used for transferring user data from the service or database to the client or controller layer.
 * It provides a simple representation of the user, excluding sensitive details such as passwords.
 * </p>
 *
 * <h2>Fields:</h2>
 * <ul>
 *     <li><b>firstName</b>: The user's first name.</li>
 *     <li><b>surname</b>: The user's surname.</li>
 *     <li><b>lastname</b>: The user's last name.</li>
 *     <li><b>email</b>: The user's email address.</li>
 *     <li><b>phone</b>: The user's phone number.</li>
 *     <li><b>region</b>: The user's region.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This DTO is typically returned in API responses for user-related queries, such as retrieving a user profile.
 * </p>
 *
 * <h2>Serialization:</h2>
 * <p>
 * Implements {@link Serializable} to support serialization and deserialization processes, enabling the transfer of data
 * across different layers or systems.
 * </p>
 *
 * <h2>Equality and Hashing:</h2>
 * <p>
 * The class overrides {@code equals()} and {@code hashCode()} methods to allow comparison and storage in hash-based
 * collections such as {@link java.util.HashSet} or {@link java.util.HashMap}.
 * </p>
 *
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 * @see java.io.Serializable
 *
 * @author Mukhammed
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDTO implements Serializable {

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's surname.
     */
    private String surname;

    /**
     * The user's last name.
     */
    private String lastname;

    /**
     * The user's email address.
     */
    private String email;

    /**
     * The user's phone number.
     */
    private String phone;

    /**
     * The user's region.
     */
    private String region;

    /**
     * Compares this object to another object for equality.
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetUserDTO that = (GetUserDTO) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(surname, that.surname) && Objects.equals(lastname, that.lastname) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(region, that.region);
    }

    /**
     * Returns a hash code for this object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, surname, lastname, email, phone, region);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string containing the field values of this DTO
     */
    @Override
    public String toString() {
        return "GetUserDao{" +
                "firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
