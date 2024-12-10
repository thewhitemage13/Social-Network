package org.thewhitemage13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a notification entity in the system.
 * <p>
 * This entity maps to the "notification" table in the database and stores
 * details of notifications sent to users, including their type, content,
 * read status, and creation timestamp.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identifier for each notification.</li>
 *     <li>Association with a specific user through the user ID.</li>
 *     <li>Tracks the type, content, and read status of the notification.</li>
 *     <li>Records the timestamp when the notification was created.</li>
 *     <li>Optimized for persistence using JPA annotations.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
@Entity
public class Notification {

    /**
     * Unique identifier for the notification.
     * <p>
     * This field is the primary key in the "notification" table, generated automatically
     * using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    /**
     * The unique identifier of the user to whom this notification belongs.
     * <p>
     * Serves as a foreign key linking the notification to a specific user.
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
     * Provides a string representation of the {@code Notification} entity.
     * <p>
     * This includes the {@code notificationId}, {@code userId}, {@code type}, {@code message},
     * {@code read} status, and {@code createdAt} timestamp for debugging and logging purposes.
     * </p>
     *
     * @return a string representation of the notification entity
     */
    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                '}';
    }
}
