package org.thewhitemage13.interfaces;

import org.thewhitemage13.UserEvent;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.dto.OpenUserDTO;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;

import java.util.List;

/**
 * Interface for user processor services.
 * <p>
 * This interface defines methods for handling user-related operations such as creating,
 * fetching, and converting user data to various DTO (Data Transfer Object) formats.
 * It also helps in constructing user events for any necessary notifications or updates.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #userCreateProcessor(CreateUserDTO, User, String)} processes user creation by
 *         validating and saving user data.</li>
 *     <li>{@link #getGetUserDTO(User)} converts a User entity to a GetUserDTO for data retrieval.</li>
 *     <li>{@link #getUserEvent(User)} maps a User entity to a UserEvent for event-driven processing.</li>
 *     <li>{@link #getOpenUserDTO(String, String, List, Long, Long, Long)} maps user profile data
 *         into a more publicly viewable DTO format.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserProcessorInterface {

    /**
     * Processes the creation of a new user by validating and saving the user data.
     * <p>
     * This method is called when a new user is being created, processing the provided
     * user data and phone number, and storing it in the user database after validation.
     * </p>
     *
     * @param createUserDTO the DTO containing user data for creation
     * @param user the user entity to be persisted
     * @param phoneNumber the phone number associated with the user
     */
    void userCreateProcessor(CreateUserDTO createUserDTO, User user, String phoneNumber);


    /**
     * Converts a User entity into a GetUserDTO for easier data retrieval.
     * <p>
     * This method is used to map user entity data into a GetUserDTO, which is a
     * simpler object for returning user details.
     * </p>
     *
     * @param user the user entity to be converted
     * @return the corresponding GetUserDTO object
     */
    GetUserDTO getGetUserDTO(User user);


    /**
     * Converts a User entity into a UserEvent, typically for event-driven processing or messaging.
     * <p>
     * This method is used to convert a User entity into an event format for notifications,
     * logging, or other asynchronous processing.
     * </p>
     *
     * @param user the user entity to be converted
     * @return the corresponding UserEvent object
     */
    UserEvent getUserEvent(User user);


    /**
     * Creates an OpenUserDTO based on publicly available user profile data.
     * <p>
     * This method constructs an OpenUserDTO, which includes publicly accessible details such as
     * the username, profile picture URL, media post URLs, and follower/post counts.
     * </p>
     *
     * @param userName the username of the user
     * @param profilePictureUrl the URL of the user's profile picture
     * @param mediaPostsUrl a list of URLs to the user's media posts
     * @param countFollowing the count of users this user is following
     * @param countFollowers the count of users following this user
     * @param countPosts the total number of posts made by the user
     * @return the OpenUserDTO containing the user's public profile information
     */
    OpenUserDTO getOpenUserDTO(String userName, String profilePictureUrl, List<String> mediaPostsUrl, Long countFollowing, Long countFollowers, Long countPosts);
}
