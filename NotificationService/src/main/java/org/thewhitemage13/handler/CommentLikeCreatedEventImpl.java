package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.LikeEvent;
import org.thewhitemage13.clients.CommentClient;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.LikeCommentCreateHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to the creation of likes on comments.
 * <p>
 * This class listens to the {@code comment.like.created} Kafka topic and processes
 * incoming like events on comments. Upon receiving a like event, it retrieves the
 * associated user for the comment and creates a notification to inform the user about
 * the like on their comment.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code comment.like.created} events from the Kafka topic.</li>
 *     <li>Fetches the user associated with the comment via the {@link CommentClient}.</li>
 *     <li>Creates a notification for the user via the {@link NotificationService}.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "comment.like.created")
public class CommentLikeCreatedEventImpl implements LikeCommentCreateHandlerInterface {
    private final NotificationService notificationService;
    private final CommentClient commentClient;

    /**
     * Constructs a new {@code CommentLikeCreatedEventImpl} with the specified services.
     *
     * @param notificationService the service used to create notifications
     * @param commentClient       the client used to retrieve comment-related data
     */
    @Autowired
    public CommentLikeCreatedEventImpl(NotificationService notificationService, CommentClient commentClient) {
        this.notificationService = notificationService;
        this.commentClient = commentClient;
    }

    /**
     * Handles the {@link LikeEvent} when a like is created on a comment.
     * <p>
     * This method fetches the user ID associated with the comment from the {@link CommentClient}
     * and creates a notification for that user, informing them about the like on their comment.
     * </p>
     *
     * @param likeEvent the event representing the newly created like on a comment
     */
    @Override
    @KafkaHandler
    public void likeCommentCreated(LikeEvent likeEvent) {
        Long user = commentClient.getCommentUserIdByCommentId(likeEvent.getCommentId()).getBody();

        notificationService.createNotification
                (new CreateNotificationDTO(user ,"SMS", "User with id = %s like your comment with id = %s"
                        .formatted(likeEvent.getUserId(), likeEvent.getCommentId())));
    }
}
