package org.thewhitemage13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a User entity in the system.
 * <p>
 * This class is a JPA entity that maps to the "users" table in the database.
 * It contains key information about a user, including personal details,
 * account information, and timestamps for creation and updates.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identification for each user using {@code userId}.</li>
 *     <li>Stores essential user attributes such as username, phone number, and email.</li>
 *     <li>Tracks timestamps for when the user record is created and last updated.</li>
 * </ul>
 *
 * <p>
 * This entity is annotated with JPA and uses Lombok annotations to minimize boilerplate code
 * for getters, setters, and constructors.
 * </p>
 *
 * @author Mukhammed Lolo
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
     * Unique identifier for the user.
     * <p>
     * This field serves as the primary key in the "users" table.
     * It is auto-generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;


    /**
     * Unique username chosen by the user.
     * <p>
     * This field is mandatory and must be unique within the system.
     * </p>
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * The phone number of the user.
     * <p>
     * This field is mandatory and stores the contact number of the user.
     * </p>
     */
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    /**
     * The region or location associated with the user.
     */
    private String region;

    /**
     * The email address of the user.
     * <p>
     * This field is mandatory and must be unique within the system.
     * </p>
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * The password for the user's account.
     * <p>
     * This field is mandatory and must be securely stored.
     * </p>
     */
    @Column(nullable = false)
    private String password;

    /**
     * The first name of the user.
     */
    @Column(nullable = false, name = "first_name")
    private String firstName;

    /**
     * The surname of the user.
     */
    @Column(nullable = false)
    private String surname;

    /**
     * The last name of the user.
     */
    @Column(nullable = false, name = "last_name")
    private String lastName;

    /**
     * The URL to the user's profile picture.
     */
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    /**
     * The timestamp when the user record was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the user record was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Provides a string representation of the User entity.
     * <p>
     * Useful for debugging and logging purposes.
     * </p>
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", region='" + region + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
