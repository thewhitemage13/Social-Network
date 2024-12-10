package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.interfaces.UserDeletedEventHandlerInterface;
import org.thewhitemage13.service.UserStatisticServiceImpl;

/**
 * Handles events related to the deletion of users from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "user.deleted" and processes
 * incoming events by delegating the logic to {@link UserStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "user.deleted" topic for user deletion events.</li>
 *     <li>Handle the event by invoking the {@link UserStatisticServiceImpl#remoteUserStatistic(UserEvent)} method.</li>
 * </ul>
 *
 * <h3>Kafka Configuration:</h3>
 * <p>
 * The class is annotated with {@code @KafkaListener} and {@code @KafkaHandler}
 * to enable integration with a Kafka topic and processing of messages.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "user.deleted")
public class UserDeleteEventHandlerImpl implements UserDeletedEventHandlerInterface {

    /**
     * Service to manage statistics related to user deletion.
     * <p>
     * This service contains the business logic for removing user statistics
     * when a user deletion event is processed.
     * </p>
     */
    private final UserStatisticServiceImpl userStatisticServiceImpl;

    /**
     * Constructs a new {@code UserDeleteEventHandlerImpl} with the specified
     * {@link UserStatisticServiceImpl}.
     *
     * @param userStatisticServiceImpl the service for managing statistics related to user deletion
     */
    @Autowired
    public UserDeleteEventHandlerImpl(UserStatisticServiceImpl userStatisticServiceImpl) {
        this.userStatisticServiceImpl = userStatisticServiceImpl;
    }

    /**
     * Processes a user deletion event.
     * <p>
     * This method is triggered when a message is received from the "user.deleted" Kafka topic.
     * It delegates the removal of user statistics to the {@link UserStatisticServiceImpl}.
     * </p>
     *
     * @param userEvent the event containing data about the deleted user
     */
    @Override
    @KafkaHandler
    public void userDeleted(UserEvent userEvent) {
        userStatisticServiceImpl.remoteUserStatistic(userEvent);
    }

}
