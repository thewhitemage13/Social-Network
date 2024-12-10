package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.dto.GetNotificationDTO;
import org.thewhitemage13.exception.NotificationNotFoundException;

import java.util.List;

/**
 * Interface for managing notification-related operations.
 * <p>
 * This interface defines methods for creating, retrieving, updating,
 * and deleting notifications in the system. It is designed to provide
 * a structured approach for interacting with notifications, including
 * managing their statuses and linking them to specific users.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Support for creating new notifications.</li>
 *     <li>Ability to update the status of specific notifications.</li>
 *     <li>Retrieve notifications for a specific user.</li>
 *     <li>Delete all notifications for a user.</li>
 *     <li>Retrieve a specific notification by its unique identifier.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface NotificationServiceInterface {

    /**
     * Creates a new notification.
     *
     * @param createNotificationDTO the data transfer object containing the details
     *                              of the notification to be created.
     */
    void createNotification(CreateNotificationDTO createNotificationDTO);

    /**
     * Updates the status of a specific notification.
     *
     * @param notificationId the unique identifier of the notification to be updated.
     * @param status         the new status of the notification (e.g., read or unread).
     * @throws NotificationNotFoundException if no notification with the specified ID is found.
     */
    void updateStatus(Long notificationId, boolean status) throws NotificationNotFoundException;

    /**
     * Retrieves all notifications associated with a specific user.
     *
     * @param userId the unique identifier of the user whose notifications are to be retrieved.
     * @return a list of {@link GetNotificationDTO} objects representing the user's notifications.
     * @throws NotificationNotFoundException if no notifications are found for the specified user.
     */
    List<GetNotificationDTO> getNotificationsByUserId(Long userId) throws NotificationNotFoundException;

    /**
     * Deletes all notifications associated with a specific user.
     *
     * @param userId the unique identifier of the user whose notifications are to be deleted.
     */
    void deleteAllByUserId(Long userId);

    /**
     * Retrieves a specific notification by its unique identifier.
     *
     * @param notificationId the unique identifier of the notification to be retrieved.
     * @return a {@link GetNotificationDTO} object containing the details of the notification.
     * @throws NotificationNotFoundException if no notification with the specified ID is found.
     */
    GetNotificationDTO getNotificationById(Long notificationId) throws NotificationNotFoundException;
}
