package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.CommentEvent;
import org.thewhitemage13.clients.PostClient;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.CommentCreateEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;


/**
 * Handles events related to comment creation in the system.
 * <p>
 * This class listens to the {@code comment.created} Kafka topic and processes
 * incoming comment creation events. Upon receiving a new comment event, it
 * retrieves the associated user from the post and creates a notification for
 * the user, informing them about the comment.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code comment.created} events from the Kafka topic.</li>
 *     <li>Fetches the user associated with the post via the {@link PostClient}.</li>
 *     <li>Creates a notification for the user via the {@link NotificationService}.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "comment.created")
public class CommentCreateEventHandlerImpl implements CommentCreateEventHandlerInterface {
    private final NotificationService notificationService;
    private final PostClient postClient;

    /**
     * Constructs a new {@code CommentCreateEventHandlerImpl} with the specified services.
     *
     * @param notificationService the service used to create notifications
     * @param postClient          the client used to retrieve post-related data
     */
    @Autowired
    public CommentCreateEventHandlerImpl(NotificationService notificationService, PostClient postClient) {
        this.notificationService = notificationService;
        this.postClient = postClient;
    }

    /**
     * Handles the {@link CommentEvent} when a new comment is created.
     * <p>
     * This method fetches the user ID associated with the post from the {@link PostClient}
     * and creates a notification for that user, informing them about the new comment.
     * </p>
     *
     * @param commentEvent the event representing the newly created comment
     */
    @Override
    @KafkaHandler
    public void commentCreated(CommentEvent commentEvent) {
        Long user = postClient.getUserIdByPostId(commentEvent.getPostId()).getBody();

        notificationService
                .createNotification
                        (new CreateNotificationDTO
                                (user, "SMS", "User with id = %s left a comment under your post with id = %s"
                                        .formatted(commentEvent.getUserId(), commentEvent.getPostId())));
    }
}
