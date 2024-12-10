package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.interfaces.UserDeletedEventHandlerInterface;
import org.thewhitemage13.service.SubscriptionServiceImpl;

/**
 * Handles the "user.deleted" Kafka topic events.
 * <p>
 * This component listens for events on the "user.deleted" topic, processes the incoming {@code UserEvent},
 * and triggers the deletion of associated subscriptions. The implementation relies on the
 * {@link SubscriptionServiceImpl} to remove all followers and followings for the user identified
 * in the event.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen for user deletion events via the Kafka topic "user.deleted".</li>
 *     <li>Invoke services to delete associated follower and following subscriptions for the user.</li>
 * </ul>
 *
 * <h2>Annotations Used:</h2>
 * <ul>
 *     <li>{@link Component}: Marks this class as a Spring-managed component.</li>
 *     <li>{@link KafkaListener}: Registers this handler to the "user.deleted" topic.</li>
 *     <li>{@link KafkaHandler}: Designates the method responsible for handling the event.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "user.deleted")
public class UserDeletedEventHandlerImpl implements UserDeletedEventHandlerInterface {
    private final SubscriptionServiceImpl subscriptionServiceImpl;

    /**
     * Constructs a new {@code UserDeletedEventHandlerImpl} with the specified subscription service.
     *
     * @param subscriptionServiceImpl the subscription service implementation for handling follower and following deletions
     */
    @Autowired
    public UserDeletedEventHandlerImpl(SubscriptionServiceImpl subscriptionServiceImpl) {
        this.subscriptionServiceImpl = subscriptionServiceImpl;
    }

    /**
     * Handles a {@code UserEvent} when a user is deleted.
     * <p>
     * Deletes all followers and followings for the user identified in the event.
     * </p>
     *
     * @param userEvent the event containing details of the user to be deleted
     */
    @Override
    @KafkaHandler
    public void userDeleted(UserEvent userEvent) {
        subscriptionServiceImpl.deleteFollowersById(userEvent.getUserId());
        subscriptionServiceImpl.deleteFollowingById(userEvent.getUserId());
    }
}
