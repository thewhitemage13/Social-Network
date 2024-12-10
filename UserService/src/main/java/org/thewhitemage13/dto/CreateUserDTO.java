package org.thewhitemage13.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) for creating a new user.
 * <p>
 * This class encapsulates the required data for creating a user in the system,
 * ensuring validation and proper formatting of the provided information.
 * The DTO follows best practices for input validation to enhance data integrity
 * and security.
 * </p>
 *
 * <h2>Validation Constraints:</h2>
 * <ul>
 *     <li><b>Username:</b> Must be between 3 and 50 characters and not blank.</li>
 *     <li><b>Email:</b> Must be in a valid email format and not blank.</li>
 *     <li><b>Phone Number:</b> Must contain 10 to 15 digits and can include an optional "+" prefix.</li>
 *     <li><b>Region:</b> Cannot be blank.</li>
 *     <li><b>Password:</b> Must be at least 8 characters long and not blank.</li>
 *     <li><b>First Name:</b> Cannot exceed 50 characters and cannot be blank.</li>
 *     <li><b>Surname:</b> Optional but cannot exceed 50 characters.</li>
 *     <li><b>Last Name:</b> Optional but cannot exceed 50 characters.</li>
 *     <li><b>Profile Picture URL:</b> Must be a valid HTTP or HTTPS URL.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO implements Serializable {

    /**
     * The username of the user.
     * <p>
     * Must be between 3 and 50 characters in length.
     * </p>
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    /**
     * The email address of the user.
     * <p>
     * Must be in a valid email format.
     * </p>
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    /**
     * The phone number of the user.
     * <p>
     * Must contain 10 to 15 digits and can include an optional "+" prefix.
     * </p>
     */
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be valid and contain 10 to 15 digits")
    private String phoneNumber;

    /**
     * The region associated with the user.
     * <p>
     * Cannot be blank.
     * </p>
     */
    @NotBlank(message = "Region is required")
    private String region;

    /**
     * The password for the user's account.
     * <p>
     * Must be at least 8 characters long.
     * </p>
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    /**
     * The first name of the user.
     * <p>
     * Cannot exceed 50 characters.
     * </p>
     */
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    /**
     * The surname of the user.
     * <p>
     * Optional field but cannot exceed 50 characters if provided.
     * </p>
     */
    @Size(max = 50, message = "Surname must not exceed 50 characters")
    private String surname;

    /**
     * The last name of the user.
     * <p>
     * Optional field but cannot exceed 50 characters if provided.
     * </p>
     */
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    /**
     * The profile picture URL of the user.
     * <p>
     * Must be a valid HTTP or HTTPS URL.
     * </p>
     */
    @Pattern(regexp = "^(http|https)://.*", message = "Profile picture URL must be a valid URL")
    private String profilePictureUrl;

    /**
     * Provides a string representation of the CreateUserDTO object.
     * <p>
     * Includes key user fields for logging and debugging purposes.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "CreateUserDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", region='" + region + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }
}
