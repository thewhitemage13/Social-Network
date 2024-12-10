package org.thewhitemage13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) for retrieving user details.
 * <p>
 * This class is used to encapsulate user information when fetching user data
 * from the system. It includes key fields such as username, contact information,
 * region, and profile details. This DTO is intended to be used in response models
 * and API endpoints where user details are required.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDTO implements Serializable {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The phone number of the user.
     */
    private String phoneNumber;

    /**
     * The region associated with the user.
     */
    private String region;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The surname of the user.
     * <p>
     * Optional field.
     * </p>
     */
    private String surname;

    /**
     * The last name of the user.
     * <p>
     * Optional field.
     * </p>
     */
    private String lastName;

    /**
     * The URL of the user's profile picture.
     * <p>
     * This field is expected to contain a valid HTTP or HTTPS URL.
     * </p>
     */
    private String profilePictureUrl;

    /**
     * Provides a string representation of the GetUserDTO object.
     * <p>
     * This representation includes key user details for debugging and logging purposes.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "GetUserDTO{" +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", region='" + region + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }
}
