package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.PostUpdatedEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to updates on posts.
 * <p>
 * This class listens to the {@code post.updated} Kafka topic and processes
 * incoming post update events. Upon receiving an update event for a post,
 * it creates a notification for the user whose post has been updated.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code post.updated} events from the Kafka topic.</li>
 *     <li>Creates a notification informing the post owner about the update.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "post.updated")
public class PostUpdatedEventHandlerImpl implements PostUpdatedEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code PostUpdatedEventHandlerImpl} with the specified notification service.
     *
     * @param notificationService the service used to create notifications
     */
    @Autowired
    public PostUpdatedEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link PostEvent} when a post is updated.
     * <p>
     * This method creates a notification for the post owner informing them that their
     * post has been updated, using the provided post ID and user ID from the event.
     * </p>
     *
     * @param postEvent the event representing the post update
     */
    @Override
    @KafkaHandler
    public void updatedPost(PostEvent postEvent) {
        notificationService.createNotification(new CreateNotificationDTO(postEvent.getUserId(), "SMS", "Your post with id = %s is updated".formatted(postEvent.getPostId())));
    }

}
