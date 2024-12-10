package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.interfaces.ValidationServiceInterface;
import org.thewhitemage13.repository.SubscriptionRepository;

/**
 * Provides validation services for user and subscription-related operations.
 * <p>
 * This service ensures that user IDs are valid and verifies the integrity
 * of subscription requests. It interacts with external clients and repositories
 * to fetch necessary data and enforce constraints.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Validate the existence of a user in the system.</li>
 *     <li>Ensure subscription requests adhere to business rules.</li>
 * </ul>
 *
 * @see org.thewhitemage13.clients.UserClient
 * @see org.thewhitemage13.repository.SubscriptionRepository
 * @see org.thewhitemage13.exception.UserNotFoundException
 * @see org.thewhitemage13.interfaces.ValidationServiceInterface
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class ValidationServiceImpl implements ValidationServiceInterface {
    private final UserClient userClient;
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Constructs a new {@code ValidationServiceImpl} with the specified dependencies.
     *
     * @param userClient the client used to interact with the user service
     * @param subscriptionRepository the repository for managing subscriptions
     */
    @Autowired
    public ValidationServiceImpl(UserClient userClient, SubscriptionRepository subscriptionRepository) {
        this.userClient = userClient;
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Validates that a user with the given ID exists in the system.
     * <p>
     * This method makes a call to the user client to verify the user's existence.
     * If the user is not found, a {@link UserNotFoundException} is thrown.
     * </p>
     *
     * @param userId the ID of the user to validate
     * @throws UserNotFoundException if the user does not exist
     */
    @Override
    public void validateUser(Long userId) {
        Boolean isCreate;
        ResponseEntity<Boolean> userIsCreate = userClient.verifyUserExistence(userId);
        isCreate = userIsCreate.getBody();
        if (Boolean.FALSE.equals(isCreate)) {
            throw new UserNotFoundException("User with id = %s not found".formatted(userId));
        }
    }

    /**
     * Validates a subscription request between two users.
     * <p>
     * Ensures that the follower and following IDs are different and that a subscription
     * does not already exist between them. Throws a {@link RuntimeException} for invalid requests.
     * </p>
     *
     * @param followerId the ID of the user initiating the subscription
     * @param followingId the ID of the user being subscribed to
     * @throws RuntimeException if the IDs match or the subscription already exists
     */
    @Override
    public void validateSubscriptionVerification(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new RuntimeException("The id's don't have to match");
        }
        if (subscriptionRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new RuntimeException("You are already subscribed to this user");
        }
    }
}
