package org.thewhitemage13.interfaces;

/**
 * Interface defining methods for validating follower-related data.
 * <p>
 * This interface provides methods to validate the number of followers and following
 * for a given user. It ensures that the follower and following counts are correctly handled
 * in various user-related operations.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>{@link #countFollowersValidation(Long)} validates the follower count for a user.</li>
 *     <li>{@link #countFollowingValidation(Long)} validates the following count for a user.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface FollowerValidationServiceInterface {

    /**
     * Validates the follower count for a user.
     * <p>
     * This method ensures that the user has the correct number of followers based on
     * their profile data or actions performed by other users. It returns the total count
     * of followers for the user.
     * </p>
     *
     * @param userId the unique identifier of the user whose followers count is to be validated
     * @return the number of followers for the specified user
     */
    Long countFollowersValidation(Long userId);

    /**
     * Validates the following count for a user.
     * <p>
     * This method ensures that the user has the correct number of users they are following.
     * It returns the total count of users that the given user is following.
     * </p>
     *
     * @param userId the unique identifier of the user whose following count is to be validated
     * @return the number of users the specified user is following
     */
    Long countFollowingValidation(Long userId);
}
