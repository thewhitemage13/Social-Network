package org.thewhitemage13.interfaces;

/**
 * Interface for validating post-related operations.
 * <p>
 * This interface defines methods to validate post counts associated with a user.
 * It ensures that users' posts are tracked accurately and provides the functionality
 * to count posts for a specific user.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>{@link #countPostValidation(Long)} validates the number of posts for a given user.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PostValidationServiceInterface {

    /**
     * Validates the number of posts for a specified user.
     * <p>
     * This method checks whether the number of posts for the given user is valid and returns
     * the count of posts associated with the user.
     * </p>
     *
     * @param userId the unique identifier of the user whose posts are to be counted
     * @return the total count of posts for the specified user
     */
    Long countPostValidation(Long userId);
}
