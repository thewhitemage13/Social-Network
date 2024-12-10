package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.interfaces.PostCreateEventHandlerInterface;
import org.thewhitemage13.service.PostStatisticServiceImpl;

/**
 * Handles events related to the creation of posts from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "post.created" and processes
 * incoming events by delegating the logic to {@link PostStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "post.created" topic for post creation events.</li>
 *     <li>Handle the event by invoking the {@link PostStatisticServiceImpl#createPostStatistic(PostEvent)} method.</li>
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
@KafkaListener(topics = "post.created")
public class PostCreateEventHandlerImpl implements PostCreateEventHandlerInterface {

    /**
     * Service to manage statistics related to post creation.
     * <p>
     * This service contains the business logic for creating post statistics
     * when a post creation event is processed.
     * </p>
     */
    private final PostStatisticServiceImpl postStatisticServiceImpl;

    /**
     * Constructs a new {@code PostCreateEventHandlerImpl} with the specified
     * {@link PostStatisticServiceImpl}.
     *
     * @param postStatisticServiceImpl the service for managing statistics related to post creation
     */
    @Autowired
    public PostCreateEventHandlerImpl(PostStatisticServiceImpl postStatisticServiceImpl) {
        this.postStatisticServiceImpl = postStatisticServiceImpl;
    }

    /**
     * Processes a post creation event.
     * <p>
     * This method is triggered when a message is received from the "post.created" Kafka topic.
     * It delegates the creation of post statistics to the {@link PostStatisticServiceImpl}.
     * </p>
     *
     * @param postEvent the event containing data about the created post
     */
    @Override
    @KafkaHandler
    public void postCreate(PostEvent postEvent) {
        postStatisticServiceImpl.createPostStatistic(postEvent);
    }
}
