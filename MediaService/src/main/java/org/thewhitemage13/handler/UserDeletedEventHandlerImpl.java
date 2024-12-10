package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.exceptions.MediaNotFoundException;
import org.thewhitemage13.interfaces.UserDeletedEventHandlerInterface;
import org.thewhitemage13.service.MediaServiceImpl;

/**
 * Handles events related to user deletions in the system.
 * <p>
 * This class listens to the Kafka topic {@code "user.deleted"} and processes user deletion events.
 * Upon receiving a user deletion event, it calls the {@link MediaServiceImpl} service to delete
 * all media associated with the deleted user.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Listens to the Kafka topic {@code "user.deleted"} for user deletion events.</li>
 *     <li>Invokes the {@link MediaServiceImpl#deleteAllByUserId(Long)} method to delete all media
 *         associated with the deleted user.</li>
 *     <li>Handles {@link MediaNotFoundException} if an issue arises during media deletion.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "user.deleted")
public class UserDeletedEventHandlerImpl implements UserDeletedEventHandlerInterface {
    private final MediaServiceImpl mediaServiceImpl;

    /**
     * Constructs a new {@code UserDeletedEventHandlerImpl} with the specified {@link MediaServiceImpl}.
     * <p>
     * The constructor injects the {@code MediaServiceImpl} service, which is responsible for managing
     * media-related operations such as deleting media for a specific user.
     * </p>
     *
     * @param mediaServiceImpl the service used to manage media operations
     */
    @Autowired
    public UserDeletedEventHandlerImpl(MediaServiceImpl mediaServiceImpl) {
        this.mediaServiceImpl = mediaServiceImpl;
    }

    /**
     * Handles a user deletion event and deletes all media associated with the deleted user.
     * <p>
     * When a {@link UserEvent} for a deleted user is received, this method calls the
     * {@link MediaServiceImpl#deleteAllByUserId(Long)} method to remove all media related to that user.
     * If no media are found or an error occurs during the process, a {@link MediaNotFoundException}
     * is thrown.
     * </p>
     *
     * @param userEvent the event containing the deleted user's information
     * @throws MediaNotFoundException if an error occurs while deleting media associated with the user
     */
    @Override
    @KafkaHandler
    public void userDeleted(UserEvent userEvent) throws MediaNotFoundException {
        mediaServiceImpl.deleteAllByUserId(userEvent.getUserId());
    }
}
