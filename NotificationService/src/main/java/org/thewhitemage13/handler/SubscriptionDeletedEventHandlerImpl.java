package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.SubscriptionEvent;
import org.thewhitemage13.interfaces.SubscriptionDeletedEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to the deletion of subscriptions (unsubscriptions).
 * <p>
 * This class listens to the {@code subscription.deleted} Kafka topic and processes
 * incoming subscription deletion events. Upon receiving a subscription deletion event,
 * it creates a notification informing the user that someone has unsubscribed from them.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code subscription.deleted} events from the Kafka topic.</li>
 *     <li>Creates a notification informing the user that they lost a follower.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "subscription.deleted")
public class SubscriptionDeletedEventHandlerImpl implements SubscriptionDeletedEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code SubscriptionDeletedEventHandlerImpl} with the specified notification service.
     *
     * @param notificationService the service used to create notifications
     */
    @Autowired
    public SubscriptionDeletedEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link SubscriptionEvent} when a subscription (follower) is deleted (unsubscribed).
     * <p>
     * This method creates a notification for the user informing them that they lost a follower,
     * using the follower's ID and the following user's ID from the event.
     * </p>
     *
     * @param subscriptionEvent the event representing the subscription deletion
     */
    @Override
    @KafkaHandler
    public void subscriptionCreated(SubscriptionEvent subscriptionEvent) {
        notificationService.createNotification(new CreateNotificationDTO(subscriptionEvent.getFollowingId(), "SMS", "user = %s has unsubscribed ".formatted(subscriptionEvent.getFollowerId())));
    }
}
