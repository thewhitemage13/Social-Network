package org.thewhitemage13.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.thewhitemage13.MediaEvent;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.interfaces.MediaDeleteEventHandlerInterface;
import org.thewhitemage13.service.NotificationService;

/**
 * Handles events related to the deletion of media files.
 * <p>
 * This class listens to the {@code media.deleted} Kafka topic and processes
 * incoming media deletion events. Upon receiving a media deletion event, it
 * creates a notification to inform the user about the deletion of their file.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Listens for {@code media.deleted} events from the Kafka topic.</li>
 *     <li>Creates a notification for the user about the deletion of their media file.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
@KafkaListener(topics = "media.deleted")
public class MediaDeletedEventHandlerImpl implements MediaDeleteEventHandlerInterface {
    private final NotificationService notificationService;

    /**
     * Constructs a new {@code MediaDeletedEventHandlerImpl} with the specified service.
     *
     * @param notificationService the service used to create notifications
     */
    @Autowired
    public MediaDeletedEventHandlerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Handles the {@link MediaEvent} when a media file is deleted.
     * <p>
     * This method creates a notification informing the user about the deletion of their
     * media file, using the provided URL from the event.
     * </p>
     *
     * @param mediaEvent the event representing the deleted media file
     */
    @Override
    @KafkaHandler
    public void mediaDelete(MediaEvent mediaEvent) {
        notificationService.createNotification(new CreateNotificationDTO(mediaEvent.getUserId(), "SMS", "Your file = %s deleted".formatted(mediaEvent.getUrl())));
    }
}
