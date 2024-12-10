package org.thewhitemage13.interfaces;

import org.thewhitemage13.dao.SubscriptionDAO;
import org.thewhitemage13.dto.UserSubscriptionDTO;

import java.util.List;

/**
 * Defines the contract for subscription-related operations in the system.
 * <p>
 * This interface encapsulates methods for creating, deleting, and retrieving
 * subscription data, as well as performing verification and counting operations.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Manage subscription relationships between users (followers and followings).</li>
 *     <li>Provide methods to verify and retrieve subscription details.</li>
 *     <li>Support counting and bulk deletion of subscriptions.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface SubscriptionInterface {

    /**
     * Creates a subscription between a follower and a user they are following.
     *
     * @param followerId  the unique identifier of the follower
     * @param followingId the unique identifier of the user being followed
     */
    void createSubscription(Long followerId, Long followingId);

    /**
     * Deletes a subscription between a follower and a user they are following.
     *
     * @param followerId  the unique identifier of the follower
     * @param followingId the unique identifier of the user being unfollowed
     */
    void deleteSubscription(Long followerId, Long followingId);

    /**
     * Retrieves all users that a specific follower is following.
     *
     * @param followerId the unique identifier of the follower
     * @return a list of {@link SubscriptionDAO} representing the following relationships
     */
    List<SubscriptionDAO> getAllFollowingByFollowerId(Long followerId);

    /**
     * Retrieves all followers of a specific user.
     *
     * @param followingId the unique identifier of the user being followed
     * @return a list of {@link SubscriptionDAO} representing the follower relationships
     */
    List<SubscriptionDAO> getAllFollowingByFollowingId(Long followingId);

    /**
     * Verifies if a follower is following a specific user.
     *
     * @param followerId  the unique identifier of the follower
     * @param followingId the unique identifier of the user being checked
     * @return {@code true} if the follower is following the user; {@code false} otherwise
     */
    boolean followingVerification(Long followerId, Long followingId);


    /**
     * Retrieves the list of followers for a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of {@link UserSubscriptionDTO} representing the followers
     */
    List<UserSubscriptionDTO> getFollowers(Long userId);

    /**
     * Retrieves the list of users that a specific user is following.
     *
     * @param userId the unique identifier of the user
     * @return a list of {@link UserSubscriptionDTO} representing the following relationships
     */
    List<UserSubscriptionDTO> getFollowing(Long userId);

    /**
     * Counts the number of followers for a specific user.
     *
     * @param followingId the unique identifier of the user being followed
     * @return the number of followers
     */
    Long countFollowersByFollowingId(Long followingId);

    /**
     * Counts the number of users that a specific user is following.
     *
     * @param followerId the unique identifier of the follower
     * @return the number of users the follower is following
     */
    Long countFollowingByFollower(Long followerId);

    /**
     * Deletes all followers for a specific user.
     *
     * @param followerId the unique identifier of the user whose followers are to be deleted
     */
    void deleteFollowersById(Long followerId);

    /**
     * Deletes all users that a specific user is following.
     *
     * @param followingId the unique identifier of the user whose following relationships are to be deleted
     */
    void deleteFollowingById(Long followingId);

}
