package org.thewhitemage13.interfaces;

import org.thewhitemage13.entity.CommentStatistic;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for managing and retrieving comment statistics.
 * <p>
 * This interface defines methods for creating, deleting, and querying comment statistics.
 * It includes operations for managing statistics by date and retrieving statistics for all comments.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Create new comment statistics.</li>
 *     <li>Delete comment statistics.</li>
 *     <li>Show all comment statistics.</li>
 *     <li>Show statistics by a specific date.</li>
 *     <li>Delete statistics by a specific date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface CommentStatisticServiceInterface {

    /**
     * Creates a new comment statistic.
     * <p>
     * This method is responsible for generating statistics related to the creation of a comment.
     * </p>
     */
    void createCommentStatistic();

    /**
     * Deletes a comment statistic.
     * <p>
     * This method is responsible for removing existing comment statistics.
     * </p>
     */
    void deleteCommentStatistic();

    /**
     * Retrieves all comment statistics.
     * <p>
     * This method returns a list of all available comment statistics in the system.
     * </p>
     *
     * @return a list of all comment statistics
     */
    List<CommentStatistic> showAllStatistics();

    /**
     * Retrieves the comment statistics for a specific date.
     * <p>
     * This method fetches the statistics for the specified date.
     * </p>
     *
     * @param date the date for which the statistics are to be retrieved
     * @return the comment statistics for the specified date, or {@code null} if none exist
     */
    CommentStatistic showStatisticsByDate(LocalDate date);

    /**
     * Deletes the comment statistics for a specific date.
     * <p>
     * This method removes comment statistics for the given date from the system.
     * </p>
     *
     * @param date the date for which the statistics are to be deleted
     */
    void deleteStatisticByDate(LocalDate date);
}
