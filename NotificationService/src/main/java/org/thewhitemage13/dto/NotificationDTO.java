package org.thewhitemage13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a notification.
 * <p>
 * This class encapsulates all the essential details of a notification, including the user it belongs to,
 * the type of notification, its content, read status, and the timestamp when it was created.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Associates a notification with a specific user through the user ID.</li>
 *     <li>Provides information on the type and content of the notification.</li>
 *     <li>Tracks whether the notification has been read by the user.</li>
 *     <li>Includes the timestamp of when the notification was created.</li>
 *     <li>Offers a readable string representation for debugging and logging.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationDTO {

    /**
     * The unique identifier of the user to whom this notification belongs.
     * <p>
     * This field is used to associate the notification with a specific user.
     * </p>
     */
    private Long userId;

    /**
     * The type of the notification.
     * <p>
     * Represents a categorical description of the notification, such as "INFO", "WARNING", or "ALERT."
     * </p>
     */
    private String type;

    /**
     * The message content of the notification.
     * <p>
     * Contains the detailed information or context of the notification.
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
     * Stored as a {@link LocalDateTime} object to provide precise date and time information.
     * </p>
     */
    private LocalDateTime createdAt;

    /**
     * Provides a string representation of the {@code NotificationDTO}.
     * <p>
     * This includes the {@code userId}, {@code type}, {@code message}, {@code read} status,
     * and {@code createdAt} timestamp for debugging and logging purposes.
     * </p>
     *
     * @return a string representation of the DTO
     */
    @Override
    public String toString() {
        return "NotificationDTO{" +
                "userId=" + userId +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                '}';
    }
}
