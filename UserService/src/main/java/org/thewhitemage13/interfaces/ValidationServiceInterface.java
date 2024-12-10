package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;

import java.util.List;

/**
 * Interface for validation services across various user-related operations.
 * <p>
 * This interface defines the validation methods for checking various aspects of a user's
 * account, such as username, phone number, password, email, media, followers, and more.
 * It provides methods for validating data when creating or updating users, as well as performing
 * checks related to user activity, posts, and subscriptions.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #validateUsername(String)} validates the username for proper format and availability.</li>
 *     <li>{@link #validateUpdateUser(CreateUserDTO, User)} validates user data for updating an existing profile.</li>
 *     <li>{@link #validatePost(Long)} checks if the user has valid posts in the system.</li>
 *     <li>{@link #validateUpdatePhoneNumber(String, String)} validates a phone number update.</li>
 *     <li>{@link #validatePhoneNumber(String, String)} validates the phone number for a new user or update.</li>
 *     <li>{@link #validatePassword(String)} ensures that the password meets security requirements.</li>
 *     <li>{@link #validatePicture(CreateUserDTO)} ensures that the provided profile picture URL is valid.</li>
 *     <li>{@link #validateMedia(Long)} checks the media associated with a user’s posts.</li>
 *     <li>{@link #validateFollowing(Long)} validates the count of users the user is following.</li>
 *     <li>{@link #validateFollowers(Long)} validates the count of followers a user has.</li>
 *     <li>{@link #validateEmail(String)} validates a user's email format and availability.</li>
 *     <li>{@link #validateUpdateEmail(String)} validates an email update for correctness and availability.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface ValidationServiceInterface {

    /**
     * Validates the username to ensure it is correctly formatted and not already taken.
     * <p>
     * This method checks if the username adheres to the required format (e.g., no special characters)
     * and if the username is available for use (not already taken by another user).
     * </p>
     *
     * @param username the username to validate
     */
    void validateUsername(String username);

    /**
     * Validates the user data when updating an existing user profile.
     * <p>
     * This method checks the validity of the data provided for updating an existing user profile.
     * It checks for conflicts with existing data and ensures the data is formatted correctly.
     * </p>
     *
     * @param createUserDTO the new user data provided for the update
     * @param updateUser the existing user entity that is being updated
     * @return a string indicating the result of the validation process
     * @throws NumberParseException if there is an issue with the phone number format
     */
    String validateUpdateUser(CreateUserDTO createUserDTO, User updateUser) throws NumberParseException;

    /**
     * Validates the number of posts associated with a user.
     * <p>
     * This method checks if the number of posts for a given user is valid.
     * </p>
     *
     * @param userId the ID of the user whose posts are to be validated
     * @return the number of posts associated with the user
     */
    Long validatePost(Long userId);

    /**
     * Validates a phone number when updating it.
     * <p>
     * This method validates the format and correctness of a phone number when it is being updated.
     * </p>
     *
     * @param phoneNum the phone number to validate
     * @param region the region code for phone number validation
     * @return a string indicating the result of the validation process
     * @throws NumberParseException if the phone number format is invalid
     */
    String validateUpdatePhoneNumber(String phoneNum, String region) throws NumberParseException;

    /**
     * Validates the phone number for a new user or an update.
     * <p>
     * This method ensures the phone number is valid according to the specified region.
     * </p>
     *
     * @param phoneNumber the phone number to validate
     * @param region the region code for phone number validation
     * @return a string indicating the result of the validation process
     * @throws NumberParseException if the phone number format is invalid
     */
    String validatePhoneNumber(String phoneNumber, String region) throws NumberParseException;

    /**
     * Validates a password to ensure it meets the required security standards.
     * <p>
     * This method checks if the password adheres to predefined security rules, such as length and complexity.
     * </p>
     *
     * @param password the password to validate
     */
    void validatePassword(String password);

    /**
     * Validates the profile picture URL provided by the user.
     * <p>
     * This method checks if the provided profile picture URL is valid and accessible.
     * </p>
     *
     * @param createUserDTO the user data containing the profile picture URL
     */
    void validatePicture(CreateUserDTO createUserDTO);

    /**
     * Validates the media URLs associated with a user's posts.
     * <p>
     * This method checks if the media URLs associated with a user’s posts are valid and accessible.
     * </p>
     *
     * @param userId the ID of the user whose media URLs are to be validated
     * @return a list of valid media URLs associated with the user
     */
    List<String> validateMedia(Long userId);

    /**
     * Validates the count of users that the user is following.
     * <p>
     * This method ensures that the following count of a user is valid and within acceptable limits.
     * </p>
     *
     * @param userId the ID of the user whose following count is to be validated
     * @return the number of users the given user is following
     */
    Long validateFollowing(Long userId);

    /**
     * Validates the count of followers a user has.
     * <p>
     * This method ensures that the follower count of a user is valid and within acceptable limits.
     * </p>
     *
     * @param userId the ID of the user whose follower count is to be validated
     * @return the number of followers of the given user
     */
    Long validateFollowers(Long userId);

    /**
     * Validates the email address to ensure it is in the correct format and available for use.
     * <p>
     * This method checks the format of the email address and ensures that it is not already in use.
     * </p>
     *
     * @param email the email address to validate
     */
    void validateEmail(String email);

    /**
     * Validates an email update to ensure the new email address is correctly formatted and available for use.
     * <p>
     * This method checks the format of the new email address and ensures it is not already in use by another user.
     * </p>
     *
     * @param email the new email address to validate
     */
    void validateUpdateEmail(String email);
}
