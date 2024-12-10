package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.LikeEvent;
import org.thewhitemage13.interfaces.LikePostDeleteEventHandlerInterface;
import org.thewhitemage13.service.LikeStatisticServiceImpl;

/**
 * Handles events related to the deletion of likes on posts from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "post.like.deleted" and processes
 * incoming events by delegating the logic to {@link LikeStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "post.like.deleted" topic for like deletion events on posts.</li>
 *     <li>Handle the event by invoking the {@link LikeStatisticServiceImpl#deleteLikePostStatistic()} method.</li>
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
@KafkaListener(topics = "post.like.deleted")
public class LikePostDeleteEventHandlerImpl implements LikePostDeleteEventHandlerInterface {

    /**
     * Service to manage like statistics for posts.
     * <p>
     * This service contains the business logic for deleting like statistics
     * when a like deletion event for a post is processed.
     * </p>
     */
    private final LikeStatisticServiceImpl likeStatisticServiceImpl;

    /**
     * Constructs a new {@code LikePostDeleteEventHandlerImpl} with the specified
     * {@link LikeStatisticServiceImpl}.
     *
     * @param likeStatisticServiceImpl the service for managing like statistics for posts
     */
    @Autowired
    public LikePostDeleteEventHandlerImpl(LikeStatisticServiceImpl likeStatisticServiceImpl) {
        this.likeStatisticServiceImpl = likeStatisticServiceImpl;
    }

    /**
     * Processes a like deletion event for a post.
     * <p>
     * This method is triggered when a message is received from the "post.like.deleted" Kafka topic.
     * It delegates the deletion of like statistics for the post to the {@link LikeStatisticServiceImpl}.
     * </p>
     *
     * @param likeEvent the event containing data about the deleted like
     */
    @Override
    @KafkaHandler
    public void likePostDeleted(LikeEvent likeEvent) {
        likeStatisticServiceImpl.deleteLikePostStatistic();
    }
}
