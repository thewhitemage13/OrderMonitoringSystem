package org.thewhitemage13;

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
