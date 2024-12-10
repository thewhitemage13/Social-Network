package org.thewhitemage13.interfaces;

/**
 * Interface for user validation services.
 * <p>
 * This interface defines the method for validating a user based on their unique identifier.
 * Implementing classes are responsible for performing the logic necessary to validate the user.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates a user based on their ID.</li>
 *     <li>Ensures that the provided user ID is legitimate and exists within the system.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface ValidationServiceInterface {

    /**
     * Validates a user by their unique identifier.
     * <p>
     * This method checks the legitimacy of a user by their ID, ensuring that the user exists
     * and is valid in the system. If the user is not found or is invalid, an exception may be thrown.
     * </p>
     *
     * @param userId the unique identifier of the user to be validated
     */
    void validateUser(Long userId);
}
