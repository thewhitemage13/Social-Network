package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.MediaEvent;
import org.thewhitemage13.interfaces.MediaUploadEventHandlerInterface;
import org.thewhitemage13.service.MediaStatisticServiceImpl;

/**
 * Handles events related to the upload of media from a Kafka topic.
 * <p>
 * This implementation listens to the Kafka topic "media.upload" and processes
 * incoming events by delegating the logic to {@link MediaStatisticServiceImpl}.
 * It is annotated as a Spring {@code Component} and is configured as a Kafka listener.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listen to the "media.upload" topic for media upload events.</li>
 *     <li>Handle the event by invoking the {@link MediaStatisticServiceImpl#uploadMediaStatistic(MediaEvent)} method.</li>
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
@KafkaListener(topics = "media.upload")
public class MediaUploadEventHandlerImpl implements MediaUploadEventHandlerInterface {

    /**
     * Service to manage statistics related to media uploads.
     * <p>
     * This service contains the business logic for handling statistics
     * when a media upload event is processed.
     * </p>
     */
    private final MediaStatisticServiceImpl mediaStatisticServiceImpl;

    /**
     * Constructs a new {@code MediaUploadEventHandlerImpl} with the specified
     * {@link MediaStatisticServiceImpl}.
     *
     * @param mediaStatisticServiceImpl the service for managing statistics related to media uploads
     */
    @Autowired
    public MediaUploadEventHandlerImpl(MediaStatisticServiceImpl mediaStatisticServiceImpl) {
        this.mediaStatisticServiceImpl = mediaStatisticServiceImpl;
    }

    /**
     * Processes a media upload event.
     * <p>
     * This method is triggered when a message is received from the "media.upload" Kafka topic.
     * It delegates the processing of the media upload statistics to the {@link MediaStatisticServiceImpl}.
     * </p>
     *
     * @param mediaEvent the event containing data about the uploaded media
     */
    @Override
    @KafkaHandler
    public void mediaUpload(MediaEvent mediaEvent) {
        mediaStatisticServiceImpl.uploadMediaStatistic(mediaEvent);
    }
}
