package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.SubscriptionClient;
import org.thewhitemage13.interfaces.FollowerValidationServiceInterface;

/**
 * Service implementation for validating follower and following counts.
 * <p>
 * This service communicates with a subscription client to fetch the number of followers
 * and followings for a specific user. If the client call fails, it defaults the count to zero.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Retrieve the count of followers for a given user.</li>
 *     <li>Retrieve the count of followings for a given user.</li>
 *     <li>Handle exceptions gracefully by defaulting counts to zero in case of errors.</li>
 * </ul>
 *
 * <h2>Dependencies:</h2>
 * <ul>
 *     <li>{@link SubscriptionClient} - Used for communicating with the subscription service.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class FollowerValidationServiceImpl implements FollowerValidationServiceInterface {
    private final SubscriptionClient subscriptionClient;

    /**
     * Constructs an instance of {@code FollowerValidationServiceImpl}.
     *
     * @param subscriptionClient the client for interacting with the subscription service
     */
    @Autowired
    public FollowerValidationServiceImpl(SubscriptionClient subscriptionClient) {
        this.subscriptionClient = subscriptionClient;
    }

    /**
     * Retrieves the count of followers for a given user.
     * <p>
     * Fetches the follower count from the {@link SubscriptionClient}. If the request fails,
     * the method returns a default count of {@code 0}.
     * </p>
     *
     * @param userId the ID of the user whose followers are being counted
     * @return the count of followers; {@code 0} if the client call fails
     */
    @Override
    public Long countFollowersValidation(Long userId) {
        Long countFollowers;
        try {
            countFollowers = subscriptionClient.countFollowers(userId).getBody();
        } catch (Exception e) {
            countFollowers = 0L;
        }
        return countFollowers;
    }

    /**
     * Retrieves the count of users a given user is following.
     * <p>
     * Fetches the following count from the {@link SubscriptionClient}. If the request fails,
     * the method returns a default count of {@code 0}.
     * </p>
     *
     * @param userId the ID of the user whose followings are being counted
     * @return the count of followings; {@code 0} if the client call fails
     */
    @Override
    public Long countFollowingValidation(Long userId) {
        Long countFollowing;
        try {
            countFollowing = subscriptionClient.countFollowing(userId).getBody();
        } catch (Exception e) {
            countFollowing = 0L;
        }
        return countFollowing;
    }
}
