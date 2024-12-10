package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.Media;

import java.util.List;

/**
 * Repository interface for accessing and managing {@link Media} entities in the database.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations
 * for the {@link Media} entity. Additionally, it includes custom queries for checking
 * the existence of a media file by its URL, deleting media files by user ID,
 * and retrieving all media files associated with a specific user.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Check if a media file exists by its URL.</li>
 *     <li>Delete all media files associated with a specific user.</li>
 *     <li>Retrieve all media files associated with a specific user.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    /**
     * Checks if a media file exists by its URL.
     * <p>
     * This method returns {@code true} if a media file with the given URL already exists
     * in the system, otherwise it returns {@code false}.
     * </p>
     *
     * @param url the URL of the media to check
     * @return {@code true} if the media exists by the given URL, {@code false} otherwise
     */
    boolean existsByUrl(String url);

    /**
     * Deletes all media files associated with a specific user ID.
     * <p>
     * This method removes all media entries linked to the provided user ID from the database.
     * </p>
     *
     * @param userId the ID of the user whose media files should be deleted
     */
    void deleteAllByUserId(Long userId);

    /**
     * Retrieves all media files associated with a specific user ID.
     * <p>
     * This method returns a list of {@link Media} entities that are linked to the given user ID.
     * </p>
     *
     * @param userId the ID of the user whose media files should be retrieved
     * @return a list of {@link Media} entities associated with the given user ID
     */
    List<Media> findAllByUserId(Long userId);
}
