package org.thewhitemage13.interfaces;

import org.thewhitemage13.PostEvent;
import org.thewhitemage13.entity.PostStatistic;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for managing and retrieving post statistics.
 * <p>
 * This interface defines methods for handling the creation, deletion, and retrieval of post statistics.
 * It includes operations for managing post statistics by date, as well as creating and deleting statistics
 * based on post events.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Retrieve all post statistics.</li>
 *     <li>Retrieve post statistics for a specific date.</li>
 *     <li>Delete post statistics for a specific date.</li>
 *     <li>Create post statistics based on post events.</li>
 *     <li>Delete post statistics based on post events.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PostStatisticServiceInterface {

    /**
     * Retrieves all post statistics.
     * <p>
     * This method returns a list of all available post statistics.
     * </p>
     *
     * @return a list of all post statistics
     */
    List<PostStatistic> getPostStatistics();

    /**
     * Retrieves post statistics for a specific date.
     * <p>
     * This method fetches the post statistics for the specified date.
     * </p>
     *
     * @param date the date for which the statistics are to be retrieved
     * @return the post statistics for the specified date, or {@code null} if none exist
     */
    PostStatistic getStatisticByDate(LocalDate date);

    /**
     * Deletes post statistics for a specific date.
     * <p>
     * This method removes post statistics for the given date from the system.
     * </p>
     *
     * @param date the date for which the statistics should be deleted
     */
    void deleteStatisticByDate(LocalDate date);

    /**
     * Creates post statistics based on the provided post event.
     * <p>
     * This method generates new post statistics based on the data from the specified post event.
     * </p>
     *
     * @param postEvent the post event containing information for the new statistics
     */
    void createPostStatistic(PostEvent postEvent);

    /**
     * Deletes post statistics based on the provided post event.
     * <p>
     * This method removes post statistics associated with the given post event.
     * </p>
     *
     * @param postEvent the post event whose statistics should be deleted
     */
    void deletePostStatistic(PostEvent postEvent);
}
