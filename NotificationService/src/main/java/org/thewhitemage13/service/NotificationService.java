package org.thewhitemage13.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.dto.GetNotificationDTO;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.exception.NotificationNotFoundException;
import org.thewhitemage13.interfaces.NotificationServiceInterface;
import org.thewhitemage13.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing notification-related operations.
 * <p>
 * This service class provides implementations for creating, retrieving,
 * updating, and deleting notifications. It leverages caching to enhance
 * performance and transactional annotations to ensure data consistency.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Support for creating new notifications with specified attributes.</li>
 *     <li>Ability to update the read status of notifications.</li>
 *     <li>Retrieve notifications for a specific user with caching support.</li>
 *     <li>Delete all notifications linked to a specific user.</li>
 *     <li>Retrieve a specific notification by its unique identifier.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class NotificationService implements NotificationServiceInterface {
    private final NotificationRepository notificationRepository;

    /**
     * Constructs a new {@code NotificationService} with the specified repository.
     *
     * @param notificationRepository the repository to interact with notification entities.
     */
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Deletes all notifications associated with a specific user.
     *
     * @param userId the unique identifier of the user whose notifications are to be deleted.
     */
    @Override
    @CacheEvict(value = "notifications", key = "#userId")
    public void deleteAllByUserId(Long userId) {
        notificationRepository.deleteAllByUserId(userId);
    }

    /**
     * Creates a new notification with the provided details.
     *
     * @param createNotificationDTO the data transfer object containing the details
     *                              of the notification to be created.
     */
    @Override
    public void createNotification(CreateNotificationDTO createNotificationDTO) {
        Notification notification = new Notification();

        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setType(createNotificationDTO.getType());
        notification.setMessage(createNotificationDTO.getMessage());
        notification.setUserId(createNotificationDTO.getUserId());

        notificationRepository.save(notification);
    }

    /**
     * Updates the read status of a specific notification.
     *
     * @param notificationId the unique identifier of the notification to be updated.
     * @param status         the new status of the notification (e.g., read or unread).
     * @throws NotificationNotFoundException if no notification with the specified ID is found.
     */
    @Override
    @CachePut(value = "notification", key = "#notificationId")
    public void updateStatus(Long notificationId, boolean status) throws NotificationNotFoundException {
        Notification update = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with id = %s not found".formatted(notificationId)));
        update.setRead(status);
        notificationRepository.save(update);
    }

    /**
     * Retrieves all notifications associated with a specific user.
     *
     * @param userId the unique identifier of the user whose notifications are to be retrieved.
     * @return a list of {@link GetNotificationDTO} objects representing the user's notifications.
     * @throws NotificationNotFoundException if no notifications are found for the specified user.
     */
    @Override
    @Cacheable(value = "notifications", key = "#userId")
    public List<GetNotificationDTO> getNotificationsByUserId(Long userId) throws NotificationNotFoundException {
        List<Notification> notifications =
                notificationRepository.findAllByUserId(userId)
                        .orElseThrow(() -> new NotificationNotFoundException("Notifications for user with id = %s is not found".formatted(userId)));

        List<GetNotificationDTO> dtos = new ArrayList<>();
        for (Notification notification : notifications) {
            GetNotificationDTO dto = new GetNotificationDTO();
            dto.setCreatedAt(notification.getCreatedAt());
            dto.setRead(true);
            dto.setType(notification.getType());
            dto.setMessage(notification.getMessage());
            dtos.add(dto);
        }
        return dtos;
    }

    /**
     * Retrieves a specific notification by its unique identifier.
     *
     * @param notificationId the unique identifier of the notification to be retrieved.
     * @return a {@link GetNotificationDTO} object containing the details of the notification.
     * @throws NotificationNotFoundException if no notification with the specified ID is found.
     */
    @Override
    @Cacheable(value = "notification", key = "#notificationId")
    public GetNotificationDTO getNotificationById(Long notificationId) throws NotificationNotFoundException {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with id = %s not found".formatted(notificationId)));

        GetNotificationDTO dto = new GetNotificationDTO();
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setRead(true);
        dto.setType(notification.getType());
        dto.setMessage(notification.getMessage());
        return dto;
    }
}
