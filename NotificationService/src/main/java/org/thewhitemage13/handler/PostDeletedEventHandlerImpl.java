package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.PostDeleteEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to the deletion of posts.
 * <p>
 * This class listens to the {@code post.deleted} Kafka topic and processes
 * incoming post deletion events. Upon receiving a post deletion event, it creates
 * a notification to inform the user that their post has been deleted.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code post.deleted} events from the Kafka topic.</li>
 *     <li>Creates a notification for the user about the deletion of their post.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "post.deleted")
public class PostDeletedEventHandlerImpl implements PostDeleteEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code PostDeletedEventHandlerImpl} with the specified service.
     *
     * @param notificationService the service used to create notifications
     */
    @Autowired
    public PostDeletedEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link PostEvent} when a post is deleted.
     * <p>
     * This method creates a notification informing the user about the deletion
     * of their post, using the provided post ID from the event.
     * </p>
     *
     * @param postEvent the event representing the deleted post
     */
    @Override
    @KafkaHandler
    public void postDelete(PostEvent postEvent) {
        notificationService.createNotification(new CreateNotificationDTO(postEvent.getUserId(), "SMS", "Your post with id = %s was deleted".formatted(postEvent.getPostId())));
    }
}
