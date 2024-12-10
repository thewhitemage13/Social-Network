package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.MediaEvent;
import org.thewhitemage13.interfaces.MediaDeleteEventHandlerInterface;
import org.thewhitemage13.service.MediaStatisticServiceImpl;

/**
 * Handles events related to the deletion of media from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "media.deleted" and processes
 * incoming events by delegating the logic to {@link MediaStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "media.deleted" topic for media deletion events.</li>
 *     <li>Handle the event by invoking the {@link MediaStatisticServiceImpl#deleteMediaStatistic(MediaEvent)} method.</li>
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
@KafkaListener(topics = "media.deleted")
public class MediaDeleteEventHandlerImpl implements MediaDeleteEventHandlerInterface {

    /**
     * Service to manage statistics related to media.
     * <p>
     * This service contains the business logic for deleting media statistics
     * when a media deletion event is processed.
     * </p>
     */
    private final MediaStatisticServiceImpl mediaStatisticServiceImpl;

    /**
     * Constructs a new {@code MediaDeleteEventHandlerImpl} with the specified
     * {@link MediaStatisticServiceImpl}.
     *
     * @param mediaStatisticServiceImpl the service for managing statistics related to media
     */
    @Autowired
    public MediaDeleteEventHandlerImpl(MediaStatisticServiceImpl mediaStatisticServiceImpl) {
        this.mediaStatisticServiceImpl = mediaStatisticServiceImpl;
    }

    /**
     * Processes a media deletion event.
     * <p>
     * This method is triggered when a message is received from the "media.deleted" Kafka topic.
     * It delegates the deletion of media statistics to the {@link MediaStatisticServiceImpl}.
     * </p>
     *
     * @param mediaEvent the event containing data about the deleted media
     */
    @Override
    @KafkaHandler
    public void mediaDelete(MediaEvent mediaEvent) {
        mediaStatisticServiceImpl.deleteMediaStatistic(mediaEvent);
    }

}
