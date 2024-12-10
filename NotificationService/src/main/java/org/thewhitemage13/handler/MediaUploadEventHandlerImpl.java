package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.MediaEvent;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.MediaUploadEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;


/**
 * Handles events related to the upload of media files.
 * <p>
 * This class listens to the {@code media.upload} Kafka topic and processes
 * incoming media upload events. Upon receiving a media upload event, it creates
 * a notification to inform the user that their file has been successfully uploaded.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code media.upload} events from the Kafka topic.</li>
 *     <li>Creates a notification for the user about the successful upload of their media file.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "media.upload")
public class MediaUploadEventHandlerImpl implements MediaUploadEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code MediaUploadEventHandlerImpl} with the specified service.
     *
     * @param notificationService the service used to create notifications
     */
    @Autowired
    public MediaUploadEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link MediaEvent} when a media file is successfully uploaded.
     * <p>
     * This method creates a notification informing the user about the successful upload
     * of their media file, using the provided URL from the event.
     * </p>
     *
     * @param mediaEvent the event representing the uploaded media file
     */
    @Override
    @KafkaHandler
    public void mediaUpload(MediaEvent mediaEvent) {
        notificationService.createNotification(new CreateNotificationDTO(mediaEvent.getUserId(), "SMS", "Your file = %s  is uploaded successfully".formatted(mediaEvent.getUrl())));

    }
}
