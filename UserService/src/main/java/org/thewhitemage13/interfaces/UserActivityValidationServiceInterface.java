package org.thewhitemage13.interfaces;

import com.google.i18n.phonenumbers.NumberParseException;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.EmailBusyException;

/**
 * Interface for user activity validation services.
 * <p>
 * This interface defines methods to validate and process user activity, including
 * handling user updates and validating user information such as usernames and emails.
 * It ensures that changes to user details are checked for conflicts and adhere to defined business rules.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>{@link #updateUserValidationProcessor(CreateUserDTO, User)} processes user updates and validates the provided data.</li>
 *     <li>{@link #validateUsername(String)} checks the availability of a username.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserActivityValidationServiceInterface {

    /**
     * Processes user updates and performs validation on the provided user data.
     * <p>
     * This method is used to validate the email and phone number during user updates.
     * It checks if the provided email or phone number is already in use or if any other
     * validation errors exist.
     * </p>
     *
     * @param createUserDTO the DTO containing the user data to be updated
     * @param updateUser the existing user entity to be updated
     * @return a confirmation message indicating the success of the validation and processing
     * @throws EmailBusyException if the email is already taken by another user
     * @throws NumberParseException if the phone number format is invalid
     */
    String updateUserValidationProcessor(CreateUserDTO createUserDTO, User updateUser) throws EmailBusyException, NumberParseException;

    /**
     * Validates the uniqueness of the provided username.
     * <p>
     * This method checks if the specified username is already in use and throws an exception
     * if it is not available.
     * </p>
     *
     * @param username the username to be validated
     */
    void validateUsername(String username);
}
