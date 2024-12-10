package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.CommentEvent;
import org.thewhitemage13.exception.LikeNotFoundException;
import org.thewhitemage13.service.LikeServiceImpl;

/**
 * Event handler for processing Kafka messages related to deleted comments.
 * <p>
 * This component listens for messages on the {@code comment.deleted} topic and processes
 * events related to comment deletions. When a comment is deleted, all associated likes
 * for that comment are removed from the system.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Listens to the Kafka topic {@code comment.deleted} for incoming events.</li>
 *     <li>Handles events with the {@link #postDeleted(CommentEvent)} method, which triggers
 *     the deletion of all likes associated with a comment.</li>
 *     <li>Integrates with {@link LikeServiceImpl} to perform the deletion of likes.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // This handler listens for the "comment.deleted" Kafka topic and deletes likes
 * // associated with a comment when the event is received.
 * }</pre>
 *
 * @see KafkaListener
 * @see LikeServiceImpl
 * @see CommentEvent
 * @see LikeNotFoundException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "comment.deleted")
public class CommentDeleteEventHandler {
    private final LikeServiceImpl likeServiceImpl;

    /**
     * Constructs a new {@code CommentDeleteEventHandler} with the specified {@link LikeServiceImpl}.
     *
     * @param likeServiceImpl the {@code LikeServiceImpl} used for deleting likes associated with comments
     */
    @Autowired
    public CommentDeleteEventHandler(LikeServiceImpl likeServiceImpl) {
        this.likeServiceImpl = likeServiceImpl;
    }

    /**
     * Handles the event of a comment being deleted.
     * <p>
     * This method listens for incoming {@link CommentEvent} messages and, upon receiving a
     * {@code comment.deleted} event, it triggers the deletion of all likes associated with the
     * specified comment ID.
     * </p>
     *
     * @param commentEvent the {@link CommentEvent} containing the details of the deleted comment
     * @throws LikeNotFoundException if no likes are found for the specified comment ID
     */
    @KafkaHandler
    public void postDeleted(CommentEvent commentEvent) throws LikeNotFoundException {
        likeServiceImpl.deleteAllByCommentId(commentEvent.getCommentId());
    }
}
