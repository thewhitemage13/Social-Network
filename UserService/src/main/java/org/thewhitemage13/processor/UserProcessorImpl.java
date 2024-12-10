package org.thewhitemage13.processor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.dto.OpenUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.interfaces.UserProcessorInterface;

import java.util.List;

/**
 * Implementation of {@link UserProcessorInterface} for handling user-related processing logic.
 * <p>
 * This class provides methods to process user creation, convert user entities to various DTOs,
 * and encode sensitive data such as passwords using {@link BCryptPasswordEncoder}.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Processes user creation and populates user entity fields.</li>
 *     <li>Converts user entities to various data transfer objects (DTOs).</li>
 *     <li>Encodes passwords for security purposes.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link PasswordEncoder} for encoding user passwords securely.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
public class UserProcessorImpl implements UserProcessorInterface {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Populates a {@link User} entity with data from {@link CreateUserDTO}.
     * <p>
     * This method encodes the user's password and maps various fields from the DTO to the user entity.
     * </p>
     *
     * @param createUserDTO the DTO containing user creation data
     * @param user the {@link User} entity to be populated
     * @param phoneNumber the user's phone number to be set
     */
    @Override
    public void userCreateProcessor(CreateUserDTO createUserDTO, User user, String phoneNumber) {
        user.setUsername(createUserDTO.getUsername());
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setSurname(createUserDTO.getSurname());
        user.setProfilePictureUrl(createUserDTO.getProfilePictureUrl());
        user.setRegion(createUserDTO.getRegion());
        user.setPhoneNumber(phoneNumber);
    }

    /**
     * Converts a {@link User} entity to a {@link GetUserDTO}.
     *
     * @param user the {@link User} entity to be converted
     * @return a {@link GetUserDTO} containing user information
     */
    @Override
    public GetUserDTO getGetUserDTO(User user) {
        return new GetUserDTO
                (
                        user.getUsername(),
                        user.getPhoneNumber(),
                        user.getRegion(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getSurname(),
                        user.getLastName(),
                        user.getProfilePictureUrl()
                );
    }


    /**
     * Converts a {@link User} entity to a {@link UserEvent}.
     *
     * @param user the {@link User} entity to be converted
     * @return a {@link UserEvent} containing user-related event data
     */
    @Override
    public UserEvent getUserEvent(User user) {
        return new UserEvent
                (
                        user.getUserId(),
                        user.getUsername(),
                        user.getPhoneNumber(),
                        user.getRegion(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getSurname(),
                        user.getLastName(),
                        user.getProfilePictureUrl()
                );
    }

    /**
     * Creates an {@link OpenUserDTO} with detailed user information.
     *
     * @param userName the username of the user
     * @param profilePictureUrl the URL of the user's profile picture
     * @param mediaPostsUrl a list of URLs pointing to the user's media posts
     * @param countFollowing the count of users this user is following
     * @param countFollowers the count of followers the user has
     * @param countPosts the count of posts the user has created
     * @return an {@link OpenUserDTO} containing the user's public information
     */
    @Override
    public OpenUserDTO getOpenUserDTO(String userName, String profilePictureUrl, List<String> mediaPostsUrl, Long countFollowing, Long countFollowers, Long countPosts) {
        OpenUserDTO openUserDTO = new OpenUserDTO();
        openUserDTO.setUsername(userName);
        openUserDTO.setProfilePictureUrl(profilePictureUrl);
        openUserDTO.setMediaPostsUrl(mediaPostsUrl);
        openUserDTO.setCountFollowing(countFollowing);
        openUserDTO.setCountFollowers(countFollowers);
        openUserDTO.setCountPosts(countPosts);
        return openUserDTO;
    }

}
