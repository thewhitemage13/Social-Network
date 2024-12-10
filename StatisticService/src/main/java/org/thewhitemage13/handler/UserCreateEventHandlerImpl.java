package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.interfaces.UserCreatedEventHandlerInterface;
import org.thewhitemage13.service.UserStatisticServiceImpl;

/**
 * Handles events related to the creation of users from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "user.created" and processes
 * incoming events by delegating the logic to {@link UserStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "user.created" topic for user creation events.</li>
 *     <li>Handle the event by invoking the {@link UserStatisticServiceImpl#createUserStatistic(UserEvent)} method.</li>
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
@KafkaListener(topics = "user.created")
public class UserCreateEventHandlerImpl implements UserCreatedEventHandlerInterface {

    /**
     * Service to manage statistics related to user creation.
     * <p>
     * This service contains the business logic for creating user statistics
     * when a user creation event is processed.
     * </p>
     */
    private final UserStatisticServiceImpl userStatisticServiceImpl;

    /**
     * Constructs a new {@code UserCreateEventHandlerImpl} with the specified
     * {@link UserStatisticServiceImpl}.
     *
     * @param userStatisticServiceImpl the service for managing statistics related to user creation
     */
    @Autowired
    public UserCreateEventHandlerImpl(UserStatisticServiceImpl userStatisticServiceImpl) {
        this.userStatisticServiceImpl = userStatisticServiceImpl;
    }

    /**
     * Processes a user creation event.
     * <p>
     * This method is triggered when a message is received from the "user.created" Kafka topic.
     * It delegates the creation of user statistics to the {@link UserStatisticServiceImpl}.
     * </p>
     *
     * @param userEvent the event containing data about the created user
     */
    @Override
    @KafkaHandler
    public void userCreated(UserEvent userEvent) {
        userStatisticServiceImpl.createUserStatistic(userEvent);
    }
}
