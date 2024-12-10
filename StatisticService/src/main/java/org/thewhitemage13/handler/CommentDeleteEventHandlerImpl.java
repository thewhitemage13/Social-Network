package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.CommentEvent;
import org.thewhitemage13.interfaces.CommentDeleteEventHandlerInterface;
import org.thewhitemage13.service.CommentStatisticServiceImpl;

/**
 * Handles events related to comment deletion from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "comment.deleted" and processes
 * incoming events by delegating the logic to {@link CommentStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "comment.deleted" topic for comment deletion events.</li>
 *     <li>Handle the event by invoking the {@link CommentStatisticServiceImpl#deleteCommentStatistic()} method.</li>
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
@KafkaListener(topics = "comment.deleted")
public class CommentDeleteEventHandlerImpl implements CommentDeleteEventHandlerInterface {

    /**
     * Service to manage comment statistics.
     * <p>
     * This service contains the business logic for deleting comment statistics
     * when a comment deletion event is processed.
     * </p>
     */
    private final CommentStatisticServiceImpl commentStatisticServiceImpl;

    /**
     * Constructs a new {@code CommentDeleteEventHandlerImpl} with the specified
     * {@link CommentStatisticServiceImpl}.
     *
     * @param commentStatisticServiceImpl the service for managing comment statistics
     */
    @Autowired
    public CommentDeleteEventHandlerImpl(CommentStatisticServiceImpl commentStatisticServiceImpl) {
        this.commentStatisticServiceImpl = commentStatisticServiceImpl;
    }

    /**
     * Processes a comment deletion event.
     * <p>
     * This method is triggered when a message is received from the "comment.deleted" Kafka topic.
     * It delegates the deletion of comment statistics to the {@link CommentStatisticServiceImpl}.
     * </p>
     *
     * @param commentEvent the event containing data about the deleted comment
     */
    @Override
    @KafkaHandler
    public void commentDeleted(CommentEvent commentEvent) {
        commentStatisticServiceImpl.deleteCommentStatistic();
    }
}
