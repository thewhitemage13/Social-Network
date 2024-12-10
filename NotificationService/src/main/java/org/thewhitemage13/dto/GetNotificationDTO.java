package org.thewhitemage13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for retrieving notification details.
 * <p>
 * This class encapsulates the details of a notification, including its type, message content,
 * read status, and creation timestamp.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Provides information about the notification type and its content.</li>
 *     <li>Includes the read status to indicate whether the notification has been viewed.</li>
 *     <li>Tracks the timestamp of when the notification was created.</li>
 *     <li>Convenient string representation for debugging and logging purposes.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetNotificationDTO {

    /**
     * The type of the notification.
     * <p>
     * Represents a categorical description of the notification, such as "INFO" or "ALERT."
     * </p>
     */
    private String type;


    /**
     * The message content of the notification.
     * <p>
     * Contains the detailed information or description of the notification.
     * </p>
     */
    private String message;

    /**
     * Indicates whether the notification has been read.
     * <p>
     * {@code true} if the notification has been viewed by the user; {@code false} otherwise.
     * </p>
     */
    private boolean read;


    /**
     * The timestamp of when the notification was created.
     * <p>
     * Stored as a {@link LocalDateTime} object to provide date and time information.
     * </p>
     */
    private LocalDateTime createdAt;

    /**
     * Provides a string representation of the {@code GetNotificationDTO}.
     * <p>
     * This includes the {@code type}, {@code message}, {@code read} status, and {@code createdAt} timestamp.
     * </p>
     *
     * @return a string representation of the DTO
     */
    @Override
    public String toString() {
        return "GetNotificationDTO{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                '}';
    }
}
