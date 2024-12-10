package org.thewhitemage13.interfaces;

import org.thewhitemage13.entity.LikeStatistic;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for managing and retrieving like statistics.
 * <p>
 * This interface defines methods for creating, deleting, and querying like statistics
 * related to both posts and comments. It includes operations for retrieving statistics
 * for all likes or for specific dates.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Retrieve all like statistics.</li>
 *     <li>Retrieve like statistics for a specific date.</li>
 *     <li>Delete like statistics by date.</li>
 *     <li>Create and delete like statistics for posts.</li>
 *     <li>Create and delete like statistics for comments.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface LikeStatisticServiceInterface {

    /**
     * Retrieves all like statistics.
     * <p>
     * This method returns a list of all available like statistics for posts and comments.
     * </p>
     *
     * @return a list of all like statistics
     */
    List<LikeStatistic> getAllLikeStatistics();

    /**
     * Retrieves like statistics for a specific date.
     * <p>
     * This method fetches the like statistics for the specified date.
     * </p>
     *
     * @param date the date for which the statistics are to be retrieved
     * @return the like statistics for the specified date, or {@code null} if none exist
     */
    LikeStatistic getLikeStatisticByDate(LocalDate date);

    /**
     * Deletes like statistics for a specific date.
     * <p>
     * This method removes like statistics for the given date from the system.
     * </p>
     *
     * @param date the date for which the statistics are to be deleted
     */
    void deleteStatisticByDate(LocalDate date);

    /**
     * Creates a like statistic for posts.
     * <p>
     * This method is responsible for generating like statistics related to posts.
     * </p>
     */
    void createLikePostStatistic();

    /**
     * Deletes the like statistic for posts.
     * <p>
     * This method removes like statistics related to posts.
     * </p>
     */
    void deleteLikePostStatistic();

    /**
     * Creates a like statistic for comments.
     * <p>
     * This method is responsible for generating like statistics related to comments.
     * </p>
     */
    void createLikeCommentStatistic();

    /**
     * Deletes the like statistic for comments.
     * <p>
     * This method removes like statistics related to comments.
     * </p>
     */
    void deleteLikeCommentStatistic();
}
