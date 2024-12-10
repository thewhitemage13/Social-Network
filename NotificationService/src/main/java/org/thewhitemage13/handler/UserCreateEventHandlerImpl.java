package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.UserCreatedEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to user creation.
 * <p>
 * This class listens for events from the {@code user.created} Kafka topic and processes them.
 * Upon receiving a user creation event, it generates a notification informing the system
 * that a new user has been created.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Listens for user creation events from the {@code user.created} Kafka topic.</li>
 *     <li>Creates a notification with a basic message stating that a user has been created.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "user.created")
public class UserCreateEventHandlerImpl implements UserCreatedEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code UserCreateEventHandlerImpl} with the specified notification service.
     *
     * @param notificationService the service used to create notifications
     */
    @Autowired
    public UserCreateEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link UserEvent} when a new user is created.
     * <p>
     * This method creates a notification that indicates that a new user has been created,
     * using the user’s ID from the event.
     * </p>
     *
     * @param userEvent the event representing the user creation
     */
    @Override
    @KafkaHandler
    public void userCreated(UserEvent userEvent) {
        notificationService.createNotification(new CreateNotificationDTO(userEvent.getUserId(), "SMS", "User created"));
    }
}
