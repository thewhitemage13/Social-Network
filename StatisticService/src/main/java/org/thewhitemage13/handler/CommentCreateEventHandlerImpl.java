package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.CommentEvent;
import org.thewhitemage13.interfaces.CommentCreateEventHandlerInterface;
import org.thewhitemage13.service.CommentStatisticServiceImpl;

/**
 * Handles events related to comment creation from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "comment.created" and processes
 * incoming events by delegating the logic to {@link CommentStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "comment.created" topic for new comment creation events.</li>
 *     <li>Handle the event by invoking the {@link CommentStatisticServiceImpl#createCommentStatistic()} method.</li>
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
@KafkaListener(topics = "comment.created")
public class CommentCreateEventHandlerImpl implements CommentCreateEventHandlerInterface {

    /**
     * Service to manage comment statistics.
     * <p>
     * This service contains the business logic for creating comment statistics
     * when a new comment creation event is processed.
     * </p>
     */
    private final CommentStatisticServiceImpl commentStatisticServiceImpl;

    /**
     * Constructs a new {@code CommentCreateEventHandlerImpl} with the specified
     * {@link CommentStatisticServiceImpl}.
     *
     * @param commentStatisticServiceImpl the service for managing comment statistics
     */
    @Autowired
    public CommentCreateEventHandlerImpl(CommentStatisticServiceImpl commentStatisticServiceImpl) {
        this.commentStatisticServiceImpl = commentStatisticServiceImpl;
    }

    /**
     * Processes a comment creation event.
     * <p>
     * This method is triggered when a message is received from the "comment.created" Kafka topic.
     * It delegates the creation of comment statistics to the {@link CommentStatisticServiceImpl}.
     * </p>
     *
     * @param commentEvent the event containing data about the created comment
     */
    @Override
    @KafkaHandler
    public void commentCreated(CommentEvent commentEvent) {
        commentStatisticServiceImpl.createCommentStatistic();
    }
}
