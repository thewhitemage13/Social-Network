package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.Notification;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing and managing notifications in the database.
 * <p>
 * This repository provides methods for performing CRUD operations on the
 * {@link Notification} entity, including finding all notifications for a user
 * and deleting all notifications linked to a specific user.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Retrieve all notifications for a specific user.</li>
 *     <li>Delete all notifications associated with a specific user.</li>
 *     <li>Built-in JPA repository methods for standard operations (e.g., save, delete, find by ID).</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Finds all notifications associated with a specific user.
     *
     * @param userId the unique identifier of the user whose notifications are to be retrieved.
     * @return an {@link Optional} containing a list of {@link Notification} objects, or an empty Optional if no notifications are found.
     */
    Optional<List<Notification>> findAllByUserId(Long userId);

    /**
     * Deletes all notifications associated with a specific user.
     *
     * @param userId the unique identifier of the user whose notifications are to be deleted.
     */
    void deleteAllByUserId(Long userId);
}
