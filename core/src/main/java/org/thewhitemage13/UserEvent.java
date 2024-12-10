package org.thewhitemage13;

import java.io.Serializable;

/**
 * Represents an event related to a user in the system.
 * <p>
 * This class encapsulates details about a user, including personal information such as
 * username, phone number, region, email, name details, and profile picture URL. This event
 * is used to track user data changes or user creation events.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks the unique identifier for the user.</li>
 *     <li>Stores various user information such as username, contact details, and name.</li>
 *     <li>Includes the URL of the user's profile picture.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class UserEvent implements Serializable {
    private Long userId;
    private String username;
    private String phoneNumber;
    private String region;
    private String email;
    private String firstName;
    private String surname;
    private String lastName;
    private String profilePictureUrl;

    /**
     * Default constructor for UserEvent.
     */
    public UserEvent() {
    }

    /**
     * Constructs a new {@code UserEvent} with the specified parameters.
     *
     * @param userId the unique identifier for the user
     * @param username the username of the user
     * @param phoneNumber the phone number of the user
     * @param region the region where the user resides
     * @param email the email address of the user
     * @param firstName the user's first name
     * @param surname the user's surname
     * @param lastName the user's last name
     * @param profilePictureUrl the URL of the user's profile picture
     */
    public UserEvent(Long userId, String username, String phoneNumber, String region, String email, String firstName, String surname, String lastName, String profilePictureUrl) {
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.region = region;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.lastName = lastName;
        this.profilePictureUrl = profilePictureUrl;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the region where the user resides.
     *
     * @return the region
     */
    public String getRegion() {
        return region;
    }


    /**
     * Sets the region where the user resides.
     *
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the surname of the user.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the user.
     *
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the URL of the user's profile picture.
     *
     * @return the profile picture URL
     */
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    /**
     * Sets the URL of the user's profile picture.
     *
     * @param profilePictureUrl the profile picture URL to set
     */
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    /**
     * Returns a string representation of the UserEvent.
     * <p>
     * The string includes the user ID, username, phone number, email, and profile picture URL.
     * </p>
     *
     * @return a string representation of the user event
     */
    @Override
    public String toString() {
        return "UserEvent{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", region='" + region + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }
}
