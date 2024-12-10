package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.exceptions.LikeNotFoundException;
import org.thewhitemage13.service.LikeServiceImpl;

/**
 * Event handler for processing Kafka messages related to deleted posts.
 * <p>
 * This component listens for messages on the {@code post.deleted} topic and processes
 * events related to post deletions. When a post is deleted, all associated likes
 * for that post are removed from the system.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Listens to the Kafka topic {@code post.deleted} for incoming events.</li>
 *     <li>Handles events with the {@link #postDeleted(PostEvent)} method, which triggers
 *     the deletion of all likes associated with a post.</li>
 *     <li>Integrates with {@link LikeServiceImpl} to perform the deletion of likes.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // This handler listens for the "post.deleted" Kafka topic and deletes likes
 * // associated with a post when the event is received.
 * }</pre>
 *
 * @see KafkaListener
 * @see LikeServiceImpl
 * @see PostEvent
 * @see LikeNotFoundException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "post.deleted")
public class PostDeleteEventHandler {
    private final LikeServiceImpl likeServiceImpl;

    /**
     * Constructs a new {@code PostDeleteEventHandler} with the specified {@link LikeServiceImpl}.
     *
     * @param likeServiceImpl the {@code LikeServiceImpl} used for deleting likes associated with posts
     */
    @Autowired
    public PostDeleteEventHandler(LikeServiceImpl likeServiceImpl) {
        this.likeServiceImpl = likeServiceImpl;
    }

    /**
     * Handles the event of a post being deleted.
     * <p>
     * This method listens for incoming {@link PostEvent} messages and, upon receiving a
     * {@code post.deleted} event, it triggers the deletion of all likes associated with the
     * specified post ID.
     * </p>
     *
     * @param postEvent the {@link PostEvent} containing the details of the deleted post
     * @throws LikeNotFoundException if no likes are found for the specified post ID
     */
    @KafkaHandler
    public void postDeleted(PostEvent postEvent) throws LikeNotFoundException {
        likeServiceImpl.deleteAllByPostId(postEvent.getPostId());
    }
}
