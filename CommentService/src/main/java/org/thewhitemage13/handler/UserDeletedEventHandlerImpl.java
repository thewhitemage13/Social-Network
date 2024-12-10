package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.exception.CommentNotFoundException;
import org.thewhitemage13.exception.MediaNotFoundException;
import org.thewhitemage13.interfaces.UserDeletedEventHandlerInterface;
import org.thewhitemage13.service.CommentServiceImpl;

/**
 * Handles Kafka events related to user deletions.
 * <p>
 * This component listens to messages on the "user.deleted" topic and processes
 * them to remove all comments associated with the deleted user. It implements
 * the {@link UserDeletedEventHandlerInterface} to ensure adherence to the defined contract
 * for user deletion event handling.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Subscribes to the "user.deleted" Kafka topic.</li>
 *     <li>Processes {@link UserEvent} messages to identify deleted users.</li>
 *     <li>Deletes all comments made by the deleted user via {@link CommentServiceImpl}.</li>
 *     <li>Handles potential exceptions, such as {@link CommentNotFoundException} and
 *         {@link MediaNotFoundException}.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This handler is automatically managed by Spring's dependency injection and is
 * triggered whenever a relevant message is published to the "user.deleted" topic.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "user.deleted")
public class UserDeletedEventHandlerImpl implements UserDeletedEventHandlerInterface {

    /**
     * Service for managing comments, injected via constructor.
     */
    private final CommentServiceImpl commentServiceImpl;

    /**
     * Constructs a new {@code UserDeletedEventHandlerImpl} with the specified comment service.
     *
     * @param commentServiceImpl the comment service used to handle comment deletions
     */
    @Autowired
    public UserDeletedEventHandlerImpl(CommentServiceImpl commentServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
    }

    /**
     * Handles events indicating that a user has been deleted.
     * <p>
     * This method processes {@link UserEvent} messages to retrieve the user ID
     * and delete all associated comments. If no comments or related media are
     * found for the given user ID, the appropriate exceptions may be thrown.
     * </p>
     *
     * @param userEvent the event containing the ID of the deleted user
     * @throws CommentNotFoundException if no comments are found for the given user ID
     * @throws MediaNotFoundException   if no media is found for the given user ID
     */
    @Override
    @KafkaHandler
    public void userDeleted(UserEvent userEvent) throws MediaNotFoundException, CommentNotFoundException {
        commentServiceImpl.deleteAllByUserId(userEvent.getUserId());
    }
}
