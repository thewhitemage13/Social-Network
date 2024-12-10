package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.exceptions.CommentNotFoundException;
import org.thewhitemage13.service.CommentServiceImpl;

/**
 * Handles Kafka events related to post deletions.
 * <p>
 * This component listens to messages on the "post.deleted" topic and triggers
 * the deletion of all comments associated with the deleted post. It ensures that
 * orphaned comments are properly removed from the system when a post is deleted.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Subscribes to the "post.deleted" Kafka topic.</li>
 *     <li>Processes {@link PostEvent} messages to identify deleted posts.</li>
 *     <li>Delegates comment deletion to {@link CommentServiceImpl}.</li>
 *     <li>Handles potential exceptions, such as {@link CommentNotFoundException}.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This handler is automatically managed by Spring's dependency injection and is
 * triggered whenever a relevant message is published to the "post.deleted" topic.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "post.deleted")
public class PostDeleteEventHandler {

    /**
     * Service for managing comments, injected via constructor.
     */
    private final CommentServiceImpl commentServiceImpl;

    /**
     * Constructs a new {@code PostDeleteEventHandler} with the specified comment service.
     *
     * @param commentServiceImpl the comment service used to handle comment deletions
     */
    @Autowired
    public PostDeleteEventHandler(CommentServiceImpl commentServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
    }

    /**
     * Handles events indicating that a post has been deleted.
     * <p>
     * This method processes {@link PostEvent} messages to retrieve the post ID
     * and delete all associated comments. If no comments are found for the given
     * post ID, a {@link CommentNotFoundException} may be thrown.
     * </p>
     *
     * @param postEvent the event containing the ID of the deleted post
     * @throws CommentNotFoundException if no comments are found for the given post ID
     */
    @KafkaHandler
    public void postDeleted(PostEvent postEvent) throws CommentNotFoundException {
        commentServiceImpl.deleteAllByPostId(postEvent.getPostId());
    }
}