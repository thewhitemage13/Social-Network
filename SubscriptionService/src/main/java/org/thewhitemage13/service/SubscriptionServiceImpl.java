package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dao.SubscriptionDAO;
import org.thewhitemage13.dto.UserSubscriptionDTO;
import org.thewhitemage13.entity.Subscription;
import org.thewhitemage13.exceotion.SubscriptionNotFoundException;
import org.thewhitemage13.SubscriptionEvent;
import org.thewhitemage13.interfaces.SubscriptionInterface;
import org.thewhitemage13.processor.SubscriptionProcessorImpl;
import org.thewhitemage13.repository.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing user subscriptions.
 * <p>
 * This class provides methods to manage subscriptions between users, such as
 * creating, deleting, and verifying subscriptions, as well as retrieving subscription
 * data. It integrates with a Kafka message broker for event processing and communicates
 * with external services using a REST client.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>CRUD operations for user subscriptions.</li>
 *     <li>Integration with external systems via Kafka and REST client.</li>
 *     <li>Validation of subscription operations.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionInterface {

    /**
     * Processor for transforming subscription entities.
     */
    private final SubscriptionProcessorImpl subscriptionProcessorImpl;

    /**
     * Repository for interacting with subscription data in the database.
     */
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Service for validating subscription and user operations.
     */
    private final ValidationServiceImpl validationServiceImpl;

    /**
     * Kafka template for sending subscription events.
     */
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    /**
     * REST client for retrieving user data.
     */
    private final UserClient userClient;

    /**
     * Constructs a new {@code SubscriptionServiceImpl} with the required dependencies.
     *
     * @param subscriptionRepository the subscription repository
     * @param validationServiceImpl  the validation service
     * @param kafkaTemplate          the Kafka template
     * @param userClient             the user client
     * @param subscriptionProcessorImpl the subscription processor
     */
    @Autowired
    public SubscriptionServiceImpl
            (
                    SubscriptionRepository subscriptionRepository,
                    ValidationServiceImpl validationServiceImpl,
                    KafkaTemplate<Long, Object> kafkaTemplate,
                    UserClient userClient,
                    SubscriptionProcessorImpl subscriptionProcessorImpl
            ) {
        this.subscriptionRepository = subscriptionRepository;
        this.validationServiceImpl = validationServiceImpl;
        this.kafkaTemplate = kafkaTemplate;
        this.userClient = userClient;
        this.subscriptionProcessorImpl = subscriptionProcessorImpl;
    }

    /**
     * Retrieves all subscriptions where the given user is the follower.
     *
     * @param followerId the ID of the follower
     * @return a list of {@link SubscriptionDAO} representing the subscriptions
     */
    @Override
    public List<SubscriptionDAO> getAllFollowingByFollowerId(Long followerId) {
        List<Subscription> get = subscriptionRepository.findAllByFollowerId(followerId);
        return subscriptionProcessorImpl.getSubscriptionDAOS(get);
    }

    /**
     * Retrieves all subscriptions where the given user is being followed.
     *
     * @param followingId the ID of the followed user
     * @return a list of {@link SubscriptionDAO} representing the subscriptions
     */
    @Override
    public List<SubscriptionDAO> getAllFollowingByFollowingId(Long followingId) {
        List<Subscription> get = subscriptionRepository.findAllByFollowingId(followingId);
        return subscriptionProcessorImpl.getSubscriptionDAOS(get);
    }

    /**
     * Verifies whether a subscription exists between the given follower and following IDs.
     *
     * @param followerId the ID of the follower
     * @param followingId the ID of the followed user
     * @return {@code true} if the subscription exists, {@code false} otherwise
     */
    @Override
    public boolean followingVerification(Long followerId, Long followingId) {
        return subscriptionRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    }

    /**
     * Counts the number of followers for a given user.
     *
     * @param followingId the ID of the followed user
     * @return the number of followers
     */
    @Override
    public Long countFollowersByFollowingId(Long followingId) {
        return subscriptionRepository.countByFollowingId(followingId);
    }

    /**
     * Counts the number of users a given user is following.
     *
     * @param followerId the ID of the follower
     * @return the number of users being followed
     */
    @Override
    public Long countFollowingByFollower(Long followerId) {
        return subscriptionRepository.countByFollowerId(followerId);
    }

    /**
     * Deletes all subscriptions where the given user is the follower.
     *
     * @param followerId the ID of the follower
     */
    @Override
    public void deleteFollowersById(Long followerId) {
        subscriptionRepository.deleteAllByFollowerId(followerId);
    }

    /**
     * Deletes all subscriptions where the given user is being followed.
     *
     * @param followingId the ID of the followed user
     */
    @Override
    public void deleteFollowingById(Long followingId) {
        subscriptionRepository.deleteAllByFollowingId(followingId);
    }

    /**
     * Retrieves a list of followers for a given user.
     *
     * @param userId the ID of the user
     * @return a list of {@link UserSubscriptionDTO} containing follower details
     */
    @Override
    public List<UserSubscriptionDTO> getFollowers(Long userId) {
        List<Long> followerIds = getUserIdsFromSubscriptions(userId, false);
        return userClient.getUsersByIds(followerIds);
    }

    /**
     * Retrieves a list of users being followed by a given user.
     *
     * @param userId the ID of the user
     * @return a list of {@link UserSubscriptionDTO} containing followed user details
     */
    @Override
    public List<UserSubscriptionDTO> getFollowing(Long userId) {
        List<Long> followingIds = getUserIdsFromSubscriptions(userId, true);
        return userClient.getUsersByIds(followingIds);
    }

    /**
     * Creates a new subscription between two users.
     *
     * @param followerId the ID of the follower
     * @param followingId the ID of the followed user
     */
    @Override
    public void createSubscription(Long followerId, Long followingId) {
        validationServiceImpl.validateSubscriptionVerification(followerId, followingId);
        validationServiceImpl.validateUser(followerId);
        validationServiceImpl.validateUser(followingId);
        Subscription subscription = new Subscription();
        subscription.setCreatedAt(LocalDateTime.now());
        subscription.setFollowingId(followingId);
        subscription.setFollowerId(followerId);
        subscriptionRepository.save(subscription);
        SubscriptionEvent subscriptionEvent = subscriptionProcessorImpl.getSubscriptionEvent(subscription);

//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("subscription.created", subscription.getSubscriptionId(), subscriptionEvent);
//            return null;
//        });

        kafkaTemplate.send("subscription.created", subscription.getSubscriptionId(), subscriptionEvent);
    }

    /**
     * Deletes an existing subscription between two users.
     *
     * @param followerId the ID of the follower
     * @param followingId the ID of the followed user
     * @throws SubscriptionNotFoundException if the subscription is not found
     */
    @Override
    public void deleteSubscription(Long followerId, Long followingId) {
        Subscription delete = subscriptionRepository
                .findByFollowerIdAndFollowingId(followerId, followingId)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription with followerId = %s and followingId = %s not found"
                        .formatted(followerId, followingId)));
        subscriptionRepository.delete(delete);
        SubscriptionEvent subscriptionEvent = subscriptionProcessorImpl.getSubscriptionEvent(delete);

//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("subscription.deleted", delete.getSubscriptionId(), subscriptionEvent);
//            return null;
//        });

        kafkaTemplate.send("subscription.deleted", delete.getSubscriptionId(), subscriptionEvent);
    }

    /**
     * Retrieves a list of user IDs from subscriptions based on the specified user and role.
     *
     * @param userId the ID of the user
     * @param isFollower {@code true} to retrieve followed user IDs, {@code false} to retrieve follower IDs
     * @return a list of user IDs
     */
    private List<Long> getUserIdsFromSubscriptions(Long userId, boolean isFollower) {
        return (isFollower
                ? subscriptionRepository.findAllByFollowerId(userId)
                : subscriptionRepository.findAllByFollowingId(userId))
                .stream()
                .map(isFollower ? Subscription::getFollowingId : Subscription::getFollowerId)
                .collect(Collectors.toList());
    }
}
