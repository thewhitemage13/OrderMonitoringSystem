package org.thewhitemage13.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDao {
    private String firstName;
    private String surname;
    private String lastname;
    private String email;
    private String phone;
    private String region;

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
