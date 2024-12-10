package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.LikeEvent;
import org.thewhitemage13.clients.PostClient;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.LikePostCreateEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to likes on posts.
 * <p>
 * This class listens to the {@code post.like.created} Kafka topic and processes
 * incoming like events. Upon receiving a like event on a post, it creates
 * a notification for the user whose post was liked.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code post.like.created} events from the Kafka topic.</li>
 *     <li>Creates a notification informing the post owner about the like.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "post.like.created")
public class PostLikeCreateEventHandlerImpl implements LikePostCreateEventHandlerInterface {
    private final NotificationService notificationService;
    private final PostClient postClient;

    /**
     * Constructs a new {@code PostLikeCreateEventHandlerImpl} with the specified services.
     *
     * @param notificationService the service used to create notifications
     * @param postClient the client used to fetch post data
     */
    @Autowired
    public PostLikeCreateEventHandlerImpl(NotificationService notificationService, PostClient postClient) {
        this.notificationService = notificationService;
        this.postClient = postClient;
    }

    /**
     * Handles the {@link LikeEvent} when a like is created for a post.
     * <p>
     * This method creates a notification for the post owner informing them that their
     * post was liked, using the provided post ID and user ID from the event.
     * </p>
     *
     * @param likeEvent the event representing the like on the post
     */
    @Override
    @KafkaHandler
    public void likePostCreate(LikeEvent likeEvent) {
        Long user = postClient.getUserIdByPostId(likeEvent.getPostId()).getBody();

        notificationService.createNotification
                (new CreateNotificationDTO(user ,"SMS", "User with id = %s like your post with id = %s"
                        .formatted(likeEvent.getUserId(), likeEvent.getPostId())));
    }
}
