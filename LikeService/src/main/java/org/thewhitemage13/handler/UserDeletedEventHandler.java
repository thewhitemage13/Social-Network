package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.exceptions.LikeNotFoundException;
import org.thewhitemage13.exceptions.MediaNotFoundException;
import org.thewhitemage13.service.LikeServiceImpl;

/**
 * Event handler for processing Kafka messages related to deleted users.
 * <p>
 * This component listens for messages on the {@code user.deleted} topic and processes
 * events related to user deletions. When a user is deleted, all associated likes
 * created by that user are removed from the system.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Listens to the Kafka topic {@code user.deleted} for incoming events.</li>
 *     <li>Handles events with the {@link #userDeleted(UserEvent)} method, which triggers
 *     the deletion of all likes associated with the specified user.</li>
 *     <li>Integrates with {@link LikeServiceImpl} to perform the deletion of likes.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * // This handler listens for the "user.deleted" Kafka topic and deletes likes
 * // associated with a user when the event is received.
 * }</pre>
 *
 * @see KafkaListener
 * @see LikeServiceImpl
 * @see UserEvent
 * @see LikeNotFoundException
 * @see MediaNotFoundException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "user.deleted")
public class UserDeletedEventHandler {
    private final LikeServiceImpl likeServiceImpl;

    /**
     * Constructs a new {@code UserDeletedEventHandler} with the specified {@link LikeServiceImpl}.
     *
     * @param likeServiceImpl the {@code LikeServiceImpl} used for deleting likes associated with users
     */
    @Autowired
    public UserDeletedEventHandler(LikeServiceImpl likeServiceImpl) {
        this.likeServiceImpl = likeServiceImpl;
    }

    /**
     * Handles the event of a user being deleted.
     * <p>
     * This method listens for incoming {@link UserEvent} messages and, upon receiving a
     * {@code user.deleted} event, it triggers the deletion of all likes created by the
     * specified user ID.
     * </p>
     *
     * @param userEvent the {@link UserEvent} containing the details of the deleted user
     * @throws MediaNotFoundException if any media associated with the user is not found
     * @throws LikeNotFoundException if no likes are found for the specified user ID
     */
    @KafkaHandler
    public void userDeleted(UserEvent userEvent) throws MediaNotFoundException, LikeNotFoundException {
        likeServiceImpl.deleteAllByUserId(userEvent.getUserId());
    }
}
