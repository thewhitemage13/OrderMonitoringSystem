package org.thewhitemage13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a User in the system.
 * <p>
 * This class maps to the "users" table in the database and defines the structure of user-related data.
 * It is used for database operations involving user information.
 * </p>
 *
 * <h2>Fields:</h2>
 * <ul>
 *     <li><b>id</b>: The unique identifier for the user, automatically generated.</li>
 *     <li><b>firstName</b>: The first name of the user, required.</li>
 *     <li><b>surname</b>: The surname of the user, required.</li>
 *     <li><b>lastname</b>: The last name of the user, required.</li>
 *     <li><b>email</b>: The unique email address of the user, required.</li>
 *     <li><b>phone</b>: The unique phone number of the user, required.</li>
 *     <li><b>password</b>: The password of the user, stored in an encoded format, required.</li>
 *     <li><b>region</b>: The region or location of the user, required.</li>
 * </ul>
 *
 * <h2>Database Table:</h2>
 * <p>
 * Mapped to the "users" table in the database. Fields correspond to the columns in the table.
 * </p>
 *
 * <h2>Annotations:</h2>
 * <ul>
 *     <li>{@link Entity}: Marks this class as a JPA entity.</li>
 *     <li>{@link Table}: Specifies the table name in the database ("users").</li>
 *     <li>{@link Id}: Marks the primary key field.</li>
 *     <li>{@link GeneratedValue}: Specifies the generation strategy for the primary key.</li>
 *     <li>{@link Column}: Defines column attributes such as name, nullability, and uniqueness.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * The `User` entity is primarily used for CRUD operations on user data through JPA repositories or similar ORM frameworks.
 * </p>
 *
 * @see jakarta.persistence.Entity
 * @see jakarta.persistence.Table
 * @see jakarta.persistence.Column
 * @see jakarta.persistence.GeneratedValue
 * @see lombok.Getter
 * @see lombok.Setter
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 *
 * @author Mukhammed
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {

    /**
     * The unique identifier for the user, generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The first name of the user.
     * <p>
     * This field is required and mapped to the "first_name" column in the database.
     * </p>
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The surname of the user.
     * <p>
     * This field is required and mapped to the "surname" column in the database.
     * </p>
     */
    @Column(name = "surname", nullable = false)
    private String surname;

    /**
     * The last name of the user.
     * <p>
     * This field is required and mapped to the "last_name" column in the database.
     * </p>
     */
    @Column(name = "last_name", nullable = false)
    private String lastname;

    /**
     * The unique email address of the user.
     * <p>
     * This field is required and must be unique across the "users" table.
     * </p>
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The unique phone number of the user.
     * <p>
     * This field is required and must be unique across the "users" table.
     * </p>
     */
    @Column(nullable = false, unique = true)
    private String phone;

    /**
     * The password of the user.
     * <p>
     * This field is required and stored in an encoded format for security.
     * </p>
     */
    @Column(nullable = false)
    private String password;

    /**
     * The region or location of the user.
     * <p>
     * This field is required and stored as a plain string.
     * </p>
     */
    @Column(nullable = false)
    private String region;

    /**
     * Returns a string representation of the user entity.
     *
     * @return a string containing all field values of this user entity
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
