package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreateUserDTO;
import java.util.List;

/**
 * Interface for validating media-related operations for users.
 * <p>
 * This interface defines methods for validating the media associated with a user,
 * including validating media URLs and profile pictures during user creation or update.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>{@link #validateMedia(Long)} validates all media related to a user, such as images or videos.</li>
 *     <li>{@link #validatePicture(CreateUserDTO)} validates the profile picture URL in a user's creation data.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface MediaValidationServiceInterface {

    /**
     * Validates the media URLs associated with a user.
     * <p>
     * This method checks all media URLs associated with the user, ensuring they are valid and accessible.
     * </p>
     *
     * @param userId the unique identifier of the user whose media URLs are to be validated
     * @return a list of valid media URLs for the specified user
     */
    List<String> validateMedia(Long userId);

    /**
     * Validates the profile picture URL in the user creation data.
     * <p>
     * This method ensures that the provided profile picture URL in the user creation data is valid.
     * </p>
     *
     * @param createUserDTO the data transfer object containing the user's profile information
     */
    void validatePicture(CreateUserDTO createUserDTO);
}
