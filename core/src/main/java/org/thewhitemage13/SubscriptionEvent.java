package org.thewhitemage13;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an event related to user subscriptions in the system.
 * <p>
 * This class encapsulates details about a subscription event, which involves a follower
 * subscribing to follow another user. It includes the subscription ID, follower and following
 * user IDs, and the timestamp for when the subscription was created.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks the unique identifier for the subscription.</li>
 *     <li>Associates the subscription with the follower and the user being followed.</li>
 *     <li>Records the timestamp of when the subscription was created.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class SubscriptionEvent implements Serializable {
    private Long subscriptionId;
    private Long followerId;
    private Long followingId;
    private LocalDateTime createdAt;

    /**
     * Default constructor for SubscriptionEvent.
     */
    public SubscriptionEvent() {
    }


    /**
     * Constructs a new {@code SubscriptionEvent} with the specified parameters.
     *
     * @param subscriptionId the unique identifier for the subscription
     * @param followerId the ID of the follower
     * @param followingId the ID of the user being followed
     * @param createdAt the timestamp when the subscription was created
     */
    public SubscriptionEvent(Long subscriptionId, Long followerId, Long followingId, LocalDateTime createdAt) {
        this.subscriptionId = subscriptionId;
        this.followerId = followerId;
        this.followingId = followingId;
        this.createdAt = createdAt;
    }

    /**
     * Gets the unique identifier for the subscription.
     *
     * @return the subscription ID
     */
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Sets the unique identifier for the subscription.
     *
     * @param subscriptionId the subscription ID to set
     */
    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    /**
     * Gets the ID of the follower in the subscription.
     *
     * @return the follower ID
     */
    public Long getFollowerId() {
        return followerId;
    }

    /**
     * Sets the ID of the follower in the subscription.
     *
     * @param followerId the follower ID to set
     */
    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }

    /**
     * Gets the ID of the user being followed in the subscription.
     *
     * @return the following ID
     */
    public Long getFollowingId() {
        return followingId;
    }

    /**
     * Sets the ID of the user being followed in the subscription.
     *
     * @param followingId the following ID to set
     */
    public void setFollowingId(Long followingId) {
        this.followingId = followingId;
    }

    /**
     * Gets the timestamp when the subscription was created.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    /**
     * Sets the timestamp when the subscription was created.
     *
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns a string representation of the SubscriptionEvent.
     * <p>
     * The string includes the subscription ID, follower ID, following ID, and the creation timestamp.
     * </p>
     *
     * @return a string representation of the subscription event
     */
    @Override
    public String toString() {
        return "SubscriptionEvent{" +
                "subscriptionId=" + subscriptionId +
                ", followerId=" + followerId +
                ", followingId=" + followingId +
                ", createdAt=" + createdAt +
                '}';
    }
}
