package org.thewhitemage13.interfaces;

/**
 * Defines the contract for validating user and subscription data.
 * <p>
 * This interface provides methods to perform validation checks on user existence
 * and subscription relationships. It ensures the integrity of input data before
 * proceeding with business logic.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Validate the existence of a user based on their unique identifier.</li>
 *     <li>Verify the validity of a subscription relationship between a follower and a following user.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface ValidationServiceInterface {

    /**
     * Validates the existence of a user in the system.
     * <p>
     * This method checks if the user identified by the provided {@code userId} exists.
     * If the user does not exist, it throws an appropriate exception.
     * </p>
     *
     * @param userId the unique identifier of the user to validate
     */
    void validateUser(Long userId);

    /**
     * Validates the subscription relationship between a follower and a following user.
     * <p>
     * This method ensures that the subscription relationship is valid and adheres to
     * the system's rules and constraints.
     * </p>
     *
     * @param followerId  the unique identifier of the follower
     * @param followingId the unique identifier of the user being followed
     */
    void validateSubscriptionVerification(Long followerId, Long followingId);
}
