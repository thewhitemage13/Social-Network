package org.thewhitemage13.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a notification.
 * <p>
 * This class is used to encapsulate the necessary information for creating a notification,
 * including the user ID, notification type, and the message content.
 * <p>
 * Validation annotations are used to ensure data integrity and enforce constraints.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates that the user ID is not null and is greater than or equal to 1.</li>
 *     <li>Ensures the notification type and message are not blank and have a maximum length.</li>
 *     <li>Provides a convenient string representation for debugging and logging purposes.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateNotificationDTO {

    /**
     * The unique identifier of the user to whom the notification belongs.
     * <p>
     * Must not be {@code null} and must have a value of at least 1.
     * </p>
     */
    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be at least 1")
    private Long userId;

    /**
     * The type of the notification.
     * <p>
     * Must not be blank and must not exceed 50 characters in length.
     * </p>
     */
    @NotBlank(message = "Notification type cannot be blank")
    @Size(max = 50, message = "Notification type must not exceed 50 characters")
    private String type;

    /**
     * The message content of the notification.
     * <p>
     * Must not be blank and must not exceed 250 characters in length.
     * </p>
     */
    @NotBlank(message = "Message cannot be blank")
    @Size(max = 250, message = "Message must not exceed 250 characters")
    private String message;

    /**
     * Provides a string representation of the {@code CreateNotificationDTO}.
     * <p>
     * This includes the {@code userId}, {@code type}, and {@code message} fields for debugging purposes.
     * </p>
     *
     * @return a string representation of the DTO
     */
    @Override
    public String toString() {
        return "CreateNotificationDTO{" +
                "userId=" + userId +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
