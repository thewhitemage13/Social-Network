package org.thewhitemage13.interfaces;

import org.thewhitemage13.UserEvent;
import org.thewhitemage13.entity.UserStatistic;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for managing and retrieving user statistics.
 * <p>
 * This interface defines methods for handling the creation, deletion, and retrieval of user statistics.
 * It includes operations for managing user statistics by date, as well as creating and deleting statistics
 * based on user events.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Retrieve all user statistics.</li>
 *     <li>Retrieve user statistics for a specific date.</li>
 *     <li>Delete user statistics for a specific date.</li>
 *     <li>Create user statistics based on user events.</li>
 *     <li>Remove user statistics based on user events.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserStatisticServiceInterface {

    /**
     * Retrieves all user statistics.
     * <p>
     * This method returns a list of all available user statistics.
     * </p>
     *
     * @return a list of all user statistics
     */
    List<UserStatistic> getUserStatistics();

    /**
     * Retrieves user statistics for a specific date.
     * <p>
     * This method fetches the user statistics for the specified date.
     * </p>
     *
     * @param date the date for which the statistics are to be retrieved
     * @return the user statistics for the specified date, or {@code null} if none exist
     */
    UserStatistic getUserStatisticByDate(LocalDate date);

    /**
     * Deletes user statistics for a specific date.
     * <p>
     * This method removes user statistics for the given date from the system.
     * </p>
     *
     * @param date the date for which the statistics should be deleted
     */
    void deleteStatisticByDate(LocalDate date);

    /**
     * Creates user statistics based on the provided user event.
     * <p>
     * This method generates new user statistics based on the data from the specified user event.
     * </p>
     *
     * @param userEvent the user event containing information for the new statistics
     */
    void createUserStatistic(UserEvent userEvent);

    /**
     * Removes user statistics based on the provided user event.
     * <p>
     * This method deletes user statistics associated with the given user event.
     * </p>
     *
     * @param userEvent the user event whose statistics should be removed
     */
    void remoteUserStatistic(UserEvent userEvent);
}
