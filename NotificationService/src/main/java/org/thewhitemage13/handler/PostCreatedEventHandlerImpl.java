package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.PostCreateEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to the creation of posts.
 * <p>
 * This class listens to the {@code post.created} Kafka topic and processes
 * incoming post creation events. Upon receiving a post creation event, it creates
 * a notification to inform the user that their post has been successfully created.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code post.created} events from the Kafka topic.</li>
 *     <li>Creates a notification for the user about the successful creation of their post.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "post.created")
public class PostCreatedEventHandlerImpl implements PostCreateEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code PostCreatedEventHandlerImpl} with the specified service.
     *
     * @param notificationService the service used to create notifications
     */
    @Autowired
    public PostCreatedEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link PostEvent} when a new post is created.
     * <p>
     * This method creates a notification informing the user about the successful creation
     * of their post, using the provided post ID from the event.
     * </p>
     *
     * @param postEvent the event representing the created post
     */
    @Override
    @KafkaHandler
    public void postCreate(PostEvent postEvent) {
        notificationService.createNotification(new CreateNotificationDTO(postEvent.getUserId(), "SMS", "Your post with id = %s is up".formatted(postEvent.getPostId())));
    }
}
