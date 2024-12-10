package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.SubscriptionEvent;
import org.thewhitemage13.interfaces.SubscriptionCreateEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to the creation of new subscriptions (followers).
 * <p>
 * This class listens to the {@code subscription.created} Kafka topic and processes
 * incoming subscription creation events. Upon receiving a new subscription event,
 * it creates a notification for the user who gained a new follower.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code subscription.created} events from the Kafka topic.</li>
 *     <li>Creates a notification informing the user about their new follower.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "subscription.created")
public class SubscriptionCreateEventHandlerImpl implements SubscriptionCreateEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code SubscriptionCreateEventHandlerImpl} with the specified notification service.
     *
     * @param notificationService the service used to create notifications
     */
    @Autowired
    public SubscriptionCreateEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link SubscriptionEvent} when a new subscription (follower) is created.
     * <p>
     * This method creates a notification for the user informing them that they have gained a new follower,
     * using the follower's ID and the following user's ID from the event.
     * </p>
     *
     * @param subscriptionEvent the event representing the subscription creation
     */
    @Override
    @KafkaHandler
    public void subscriptionCreated(SubscriptionEvent subscriptionEvent) {
        notificationService.createNotification(new CreateNotificationDTO(subscriptionEvent.getFollowingId(), "SMS", "New follower = %s".formatted(subscriptionEvent.getFollowerId())));
    }
}
