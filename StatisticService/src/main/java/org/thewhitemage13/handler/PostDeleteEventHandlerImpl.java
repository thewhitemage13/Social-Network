package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.interfaces.PostDeleteEventHandlerInterface;
import org.thewhitemage13.service.PostStatisticServiceImpl;

/**
 * Handles events related to the deletion of posts from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "post.deleted" and processes
 * incoming events by delegating the logic to {@link PostStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "post.deleted" topic for post deletion events.</li>
 *     <li>Handle the event by invoking the {@link PostStatisticServiceImpl#deletePostStatistic(PostEvent)} method.</li>
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
@KafkaListener(topics = "post.deleted")
public class PostDeleteEventHandlerImpl implements PostDeleteEventHandlerInterface {

    /**
     * Service to manage statistics related to post deletion.
     * <p>
     * This service contains the business logic for deleting post statistics
     * when a post deletion event is processed.
     * </p>
     */
    private final PostStatisticServiceImpl postStatisticServiceImpl;

    /**
     * Constructs a new {@code PostDeleteEventHandlerImpl} with the specified
     * {@link PostStatisticServiceImpl}.
     *
     * @param postStatisticServiceImpl the service for managing statistics related to post deletion
     */
    @Autowired
    public PostDeleteEventHandlerImpl(PostStatisticServiceImpl postStatisticServiceImpl) {
        this.postStatisticServiceImpl = postStatisticServiceImpl;
    }

    /**
     * Processes a post deletion event.
     * <p>
     * This method is triggered when a message is received from the "post.deleted" Kafka topic.
     * It delegates the deletion of post statistics to the {@link PostStatisticServiceImpl}.
     * </p>
     *
     * @param postEvent the event containing data about the deleted post
     */
    @Override
    @KafkaHandler
    public void postDelete(PostEvent postEvent) {
        postStatisticServiceImpl.deletePostStatistic(postEvent);
    }
}
