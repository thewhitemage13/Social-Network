package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.UserUpdatedEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to user updates.
 * <p>
 * This class listens for events from the {@code user.updated} Kafka topic and processes them.
 * Upon receiving a user update event, it triggers the creation of a notification
 * informing that the user's details have been updated.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Listens for user update events from the {@code user.updated} Kafka topic.</li>
 *     <li>Creates a notification for the user whose details have been updated.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "user.updated")
public class UserUpdateEventHandlerImpl implements UserUpdatedEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code UserUpdateEventHandlerImpl} with the specified notification service.
     *
     * @param notificationService the service used to create notifications
     */
    @Autowired
    public UserUpdateEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link UserEvent} when a user is updated.
     * <p>
     * This method creates a notification informing the user that their details have been updated.
     * </p>
     *
     * @param userEvent the event representing the user update
     */
    @Override
    @KafkaHandler
    public void userUpdated(UserEvent userEvent) {
        notificationService.createNotification(new CreateNotificationDTO(userEvent.getUserId(), "SMS", "User updated"));
    }
}
