package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.dto.OpenUserDTO;
import org.thewhitemage13.dto.UserSubscriptionDTO;
import org.thewhitemage13.exception.EmailAlreadyTakenException;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exceptions.UserNotFoundException;
import org.thewhitemage13.exception.UsernameIsBusyException;

import java.util.List;

/**
 * Interface for user service operations.
 * <p>
 * This interface defines a variety of methods to manage users, including registering new users,
 * updating profiles, fetching user details, and verifying user status. It handles operations
 * such as adding users, deleting users, and managing user subscriptions.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>{@link #addUsers(List)} adds a list of new users, ensuring all data is validated.</li>
 *     <li>{@link #getUsernameById(Long)} retrieves a user's username by their ID.</li>
 *     <li>{@link #openUser(Long)} retrieves a user’s public profile details.</li>
 *     <li>{@link #getUserById(Long)} fetches detailed user information based on their ID.</li>
 *     <li>{@link #getUsersByIds(List)} retrieves a list of users by their IDs.</li>
 *     <li>{@link #userVerification(Long)} checks if a user exists or is active.</li>
 *     <li>{@link #registerNewUser(CreateUserDTO)} registers a new user, validating and storing their details.</li>
 *     <li>{@link #updateUserProfile(Long, CreateUserDTO)} updates an existing user's profile details.</li>
 *     <li>{@link #getInformationAboutUser(String)} retrieves user information by their username.</li>
 *     <li>{@link #deleteUser(Long)} deletes a user account by their ID.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserServiceInterface {

    /**
     * Adds a list of new users after validating the provided user data.
     * <p>
     * This method processes a list of users, validating their details (e.g., phone number format)
     * and adds them to the system after validation. If any user’s data is invalid, an exception will be thrown.
     * </p>
     *
     * @param users a list of CreateUserDTO objects containing the new users' data
     * @throws NumberParseException if a phone number is invalid or cannot be parsed
     */
    void addUsers(List<CreateUserDTO> users) throws NumberParseException;

    /**
     * Retrieves the username associated with a given user ID.
     * <p>
     * This method returns the username of the user associated with the provided ID.
     * </p>
     *
     * @param userId the user ID for which to fetch the username
     * @return the username of the user with the specified ID
     */
    String getUsernameById(Long userId);

    /**
     * Retrieves the public profile information of a user by their ID.
     * <p>
     * This method returns an OpenUserDTO containing publicly available details about the user
     * such as the username, profile picture URL, post count, follower count, and media URLs.
     * </p>
     *
     * @param userId the ID of the user whose public profile is to be retrieved
     * @return an OpenUserDTO containing the user's public profile data
     */
    OpenUserDTO openUser(Long userId);

    /**
     * Retrieves detailed user information by their ID.
     * <p>
     * This method returns a GetUserDTO containing all available details about the user,
     * such as their email, phone number, and profile information.
     * </p>
     *
     * @param userId the ID of the user whose details are to be retrieved
     * @return a GetUserDTO containing the user’s information
     */
    GetUserDTO getUserById(Long userId);

    /**
     * Retrieves detailed information for a list of users by their IDs.
     * <p>
     * This method returns a list of UserSubscriptionDTO objects for each user in the provided list of IDs.
     * </p>
     *
     * @param userIds a list of user IDs to retrieve information for
     * @return a list of UserSubscriptionDTO objects containing the information of the requested users
     */
    List<UserSubscriptionDTO> getUsersByIds(List<Long> userIds);

    /**
     * Verifies if a user exists or is active.
     * <p>
     * This method checks if the user with the given ID exists in the system and is active.
     * </p>
     *
     * @param userId the ID of the user to verify
     * @return true if the user exists and is active, otherwise false
     */
    boolean userVerification(Long userId);

    /**
     * Registers a new user by validating and saving their data.
     * <p>
     * This method processes the provided user data, ensuring that there are no conflicts with
     * existing usernames or email addresses before registering the new user.
     * </p>
     *
     * @param createUserDTO the DTO containing the data of the user to be registered
     * @throws UsernameIsBusyException if the username is already taken
     * @throws EmailAlreadyTakenException if the email is already registered
     * @throws EmailBusyException if the email is in use for another account
     * @throws NumberParseException if the phone number is invalid or cannot be parsed
     */
    void registerNewUser(CreateUserDTO createUserDTO) throws UsernameIsBusyException, EmailAlreadyTakenException, EmailBusyException, NumberParseException;

    /**
     * Updates an existing user’s profile data.
     * <p>
     * This method updates the user’s profile, including username, email, and other details.
     * It will check if the provided details are valid and if there are any conflicts with other users.
     * </p>
     *
     * @param userId the ID of the user whose profile is to be updated
     * @param createUserDTO the new user profile data
     * @throws UsernameIsBusyException if the username is already taken by another user
     * @throws UserNotFoundException if the user with the given ID is not found
     * @throws EmailAlreadyTakenException if the email is already taken by another user
     * @throws EmailBusyException if the email is currently in use
     * @throws NumberParseException if the phone number is invalid or cannot be parsed
     */
    void updateUserProfile(Long userId, CreateUserDTO createUserDTO) throws UsernameIsBusyException, UserNotFoundException, EmailAlreadyTakenException, EmailBusyException, NumberParseException;

    /**
     * Retrieves user information based on their username.
     * <p>
     * This method fetches a user’s information (such as their email, profile, etc.) by their username.
     * </p>
     *
     * @param username the username of the user to retrieve
     * @return a GetUserDTO containing the user's information
     * @throws UserNotFoundException if the user is not found with the given username
     */
    GetUserDTO getInformationAboutUser(String username) throws UserNotFoundException;

    /**
     * Deletes a user based on their ID.
     * <p>
     * This method deletes the user account corresponding to the provided ID, including all associated data.
     * </p>
     *
     * @param userId the ID of the user to be deleted
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    void deleteUser(Long userId) throws UserNotFoundException;
}
