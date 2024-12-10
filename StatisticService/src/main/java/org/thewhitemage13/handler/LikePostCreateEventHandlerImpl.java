package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.LikeEvent;
import org.thewhitemage13.interfaces.LikePostCreateEventHandlerInterface;
import org.thewhitemage13.service.LikeStatisticServiceImpl;

/**
 * Handles events related to the creation of likes on posts from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "post.like.created" and processes
 * incoming events by delegating the logic to {@link LikeStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "post.like.created" topic for new like creation events on posts.</li>
 *     <li>Handle the event by invoking the {@link LikeStatisticServiceImpl#createLikePostStatistic()} method.</li>
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
@KafkaListener(topics = "post.like.created")
public class LikePostCreateEventHandlerImpl implements LikePostCreateEventHandlerInterface {

    /**
     * Service to manage like statistics for posts.
     * <p>
     * This service contains the business logic for creating like statistics
     * when a new like event for a post is processed.
     * </p>
     */
    private final LikeStatisticServiceImpl likeStatisticServiceImpl;

    /**
     * Constructs a new {@code LikePostCreateEventHandlerImpl} with the specified
     * {@link LikeStatisticServiceImpl}.
     *
     * @param likeStatisticServiceImpl the service for managing like statistics for posts
     */
    @Autowired
    public LikePostCreateEventHandlerImpl(LikeStatisticServiceImpl likeStatisticServiceImpl) {
        this.likeStatisticServiceImpl = likeStatisticServiceImpl;
    }

    /**
     * Processes a like creation event for a post.
     * <p>
     * This method is triggered when a message is received from the "post.like.created" Kafka topic.
     * It delegates the creation of like statistics for the post to the {@link LikeStatisticServiceImpl}.
     * </p>
     *
     * @param likeEvent the event containing data about the created like
     */
    @Override
    @KafkaHandler
    public void likePostCreate(LikeEvent likeEvent) {
        likeStatisticServiceImpl.createLikePostStatistic();
    }
}
