package org.thewhitemage13.service;
import com.google.i18n.phonenumbers.NumberParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.interfaces.ValidationServiceInterface;

import java.util.List;

/**
 * Service implementation that coordinates various validation tasks related to user information.
 * <p>
 * This service acts as a central hub for validating different aspects of a user's profile, such as username, email, phone number,
 * password, media (profile picture), followers, and posts. It delegates validation tasks to specific services for each aspect.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates username, email, phone number, password, media (profile picture), posts, followers, and following.</li>
 *     <li>Handles user updates by validating the provided details and ensuring they don't conflict with existing records.</li>
 *     <li>Supports validation of both new user data and updates to existing user profiles.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link EmailValidationServiceImpl} - Service for validating email addresses.</li>
 *     <li>{@link FollowerValidationServiceImpl} - Service for validating follower counts.</li>
 *     <li>{@link MediaValidationServiceImpl} - Service for validating media URLs, such as profile pictures.</li>
 *     <li>{@link PasswordValidationServiceImpl} - Service for validating passwords according to defined rules.</li>
 *     <li>{@link PhoneValidationServiceImpl} - Service for validating phone numbers.</li>
 *     <li>{@link PostValidationServiceImpl} - Service for validating the number of posts a user has.</li>
 *     <li>{@link UserActivityValidationServiceImpl} - Service for validating user activity, such as email, phone number, and username.</li>
 * </ul>
 *
 * <h2>Exception Handling:</h2>
 * <ul>
 *     <li>{@link NumberParseException} - Thrown if a phone number is incorrectly formatted.</li>
 *     <li>Various other exceptions (e.g., email-related, username-related) are handled by delegated services.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class ValidationServiceImpl implements ValidationServiceInterface {
    private final EmailValidationServiceImpl emailValidationServiceImpl;
    private final FollowerValidationServiceImpl followerValidationServiceImpl;
    private final MediaValidationServiceImpl mediaValidationServiceImpl;
    private final PasswordValidationServiceImpl passwordValidationServiceImpl;
    private final PhoneValidationServiceImpl phoneValidationServiceImpl;
    private final PostValidationServiceImpl postValidationService;
    private final UserActivityValidationServiceImpl userActivityValidationServiceImpl;

    /**
     * Constructor for dependency injection.
     *
     * @param emailValidationServiceImpl service for validating email addresses
     * @param followerValidationServiceImpl service for validating follower counts
     * @param mediaValidationServiceImpl service for validating media URLs (e.g., profile picture)
     * @param passwordValidationServiceImpl service for validating passwords
     * @param phoneValidationServiceImpl service for validating phone numbers
     * @param postValidationService service for validating post counts
     * @param userActivityValidationServiceImpl service for validating user activity (email, username, phone number)
     */
    @Autowired
    public ValidationServiceImpl(EmailValidationServiceImpl emailValidationServiceImpl, FollowerValidationServiceImpl followerValidationServiceImpl, MediaValidationServiceImpl mediaValidationServiceImpl, PasswordValidationServiceImpl passwordValidationServiceImpl, PhoneValidationServiceImpl phoneValidationServiceImpl, PostValidationServiceImpl postValidationService, UserActivityValidationServiceImpl userActivityValidationServiceImpl) {
        this.emailValidationServiceImpl = emailValidationServiceImpl;
        this.followerValidationServiceImpl = followerValidationServiceImpl;
        this.mediaValidationServiceImpl = mediaValidationServiceImpl;
        this.passwordValidationServiceImpl = passwordValidationServiceImpl;
        this.phoneValidationServiceImpl = phoneValidationServiceImpl;
        this.postValidationService = postValidationService;
        this.userActivityValidationServiceImpl = userActivityValidationServiceImpl;
    }

    /**
     * Validates the username to ensure it is available for use.
     *
     * @param username the username to validate
     */
    @Override
    public void validateUsername(String username) {
        userActivityValidationServiceImpl.validateUsername(username);
    }

    /**
     * Validates the user details during an update.
     * <p>
     * This method checks if the provided email, phone number, and username conflicts with existing records.
     * </p>
     *
     * @param createUserDTO the DTO containing the updated user details
     * @param updateUser the existing user entity being updated
     * @return the formatted phone number if valid
     * @throws NumberParseException if the phone number is invalid
     */
    @Override
    public String validateUpdateUser(CreateUserDTO createUserDTO, User updateUser) throws NumberParseException {
        return userActivityValidationServiceImpl.updateUserValidationProcessor(createUserDTO, updateUser);
    }

    /**
     * Validates the number of posts by a user.
     *
     * @param userId the user ID whose posts count will be validated
     * @return the count of posts for the user
     */
    @Override
    public Long validatePost(Long userId) {
        return postValidationService.countPostValidation(userId);
    }

    /**
     * Validates the phone number update.
     *
     * @param phoneNum the phone number to validate
     * @param region the region code for the phone number
     * @return the formatted phone number if valid
     * @throws NumberParseException if the phone number is invalid
     */
    @Override
    public String validateUpdatePhoneNumber(String phoneNum, String region) throws NumberParseException {
        return phoneValidationServiceImpl.validateUpdatePhoneNumber(phoneNum, region);
    }

    /**
     * Validates a new phone number.
     *
     * @param phoneNumber the phone number to validate
     * @param region the region code for the phone number
     * @return the formatted phone number if valid
     * @throws NumberParseException if the phone number is invalid
     */
    @Override
    public String validatePhoneNumber(String phoneNumber, String region) throws NumberParseException {
        return phoneValidationServiceImpl.validatePhoneNumber(phoneNumber, region);
    }

    /**
     * Validates the password to ensure it follows required format rules.
     *
     * @param password the password to validate
     */
    @Override
    public void validatePassword(String password) {
        passwordValidationServiceImpl.validatePassword(password);
    }

    /**
     * Validates the profile picture URL during user creation or update.
     *
     * @param createUserDTO the DTO containing the user details, including the profile picture URL
     */
    @Override
    public void validatePicture(CreateUserDTO createUserDTO) {
        mediaValidationServiceImpl.validatePicture(createUserDTO);
    }

    /**
     * Validates the media (e.g., posts' media URLs) for the user.
     *
     * @param userId the user ID whose media URLs will be validated
     * @return a list of media URLs associated with the user
     */
    @Override
    public List<String> validateMedia(Long userId) {
        return mediaValidationServiceImpl.validateMedia(userId);
    }

    /**
     * Validates the number of people the user is following.
     *
     * @param userId the user ID whose following count will be validated
     * @return the number of users the user is following
     */
    @Override
    public Long validateFollowing(Long userId) {
        return followerValidationServiceImpl.countFollowingValidation(userId);
    }

    /**
     * Validates the number of followers a user has.
     *
     * @param userId the user ID whose followers count will be validated
     * @return the number of followers the user has
     */
    @Override
    public Long validateFollowers(Long userId) {
        return followerValidationServiceImpl.countFollowersValidation(userId);
    }

    /**
     * Validates the email during user creation or update.
     *
     * @param email the email to validate
     */
    @Override
    public void validateEmail(String email) {
        emailValidationServiceImpl.validateEmail(email);
    }

    /**
     * Validates the email during a user update to ensure it's not already in use.
     *
     * @param email the email to validate
     */
    @Override
    public void validateUpdateEmail(String email) {
        emailValidationServiceImpl.validateUpdateEmail(email);
    }
}
