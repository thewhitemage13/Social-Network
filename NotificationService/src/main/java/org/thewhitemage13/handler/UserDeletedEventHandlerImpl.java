package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.interfaces.UserDeletedEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to user deletion.
 * <p>
 * This class listens for events from the {@code user.deleted} Kafka topic and processes them.
 * Upon receiving a user deletion event, it triggers the deletion of all notifications
 * associated with the deleted user.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Listens for user deletion events from the {@code user.deleted} Kafka topic.</li>
 *     <li>Deletes all notifications related to the user who has been deleted.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "user.deleted")
public class UserDeletedEventHandlerImpl implements UserDeletedEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code UserDeletedEventHandlerImpl} with the specified notification service.
     *
     * @param notificationService the service used to delete notifications
     */
    @Autowired
    public UserDeletedEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link UserEvent} when a user is deleted.
     * <p>
     * This method deletes all notifications associated with the deleted user.
     * </p>
     *
     * @param userEvent the event representing the user deletion
     */
    @Override
    @KafkaHandler
    public void userDeleted(UserEvent userEvent) {
        notificationService.deleteAllByUserId(userEvent.getUserId());
    }
}
