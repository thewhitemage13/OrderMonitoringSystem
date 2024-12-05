package org.thewhitemage13;

/**
 * Represents an event triggered when a new user is created.
 * <p>
 * This class holds essential user information such as their name, contact details, and region.
 * It is used in event-driven systems to communicate the creation of a new user, enabling other services or
 * systems to respond accordingly (e.g., sending a welcome email, creating user-related records in other services).
 * </p>
 *
 * <h2>Key Fields:</h2>
 * <ul>
 *     <li><b>id</b>: The unique identifier for the user.</li>
 *     <li><b>firstName</b>: The first name of the user.</li>
 *     <li><b>surname</b>: The surname of the user.</li>
 *     <li><b>lastname</b>: The last name of the user.</li>
 *     <li><b>email</b>: The email address of the user.</li>
 *     <li><b>phone</b>: The phone number of the user.</li>
 *     <li><b>region</b>: The region or location where the user resides.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This class is used to encapsulate the details of a user when they are created. The event that it represents
 * can be used to trigger actions such as user registration, data synchronization, and notifications.
 * </p>
 *
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class UserCreatedEvent {
    private Long id;
    private String firstName;
    private String surname;
    private String lastname;
    private String email;
    private String phone;
    private String region;

    public UserCreatedEvent(Long id, String firstName, String surname, String lastname, String email, String phone, String region) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public UserCreatedEvent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
