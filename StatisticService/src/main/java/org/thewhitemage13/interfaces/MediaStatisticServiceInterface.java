package org.thewhitemage13.interfaces;

import org.thewhitemage13.MediaEvent;
import org.thewhitemage13.entity.MediaStatistic;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface for managing and retrieving media statistics.
 * <p>
 * This interface defines methods for creating, deleting, and querying media statistics.
 * It includes operations for handling media uploads and deletions, as well as managing
 * media statistics by date.
 * </p>
 *
 * <h2>Key Responsibilities:</h2>
 * <ul>
 *     <li>Retrieve all media statistics.</li>
 *     <li>Retrieve media statistics for a specific date.</li>
 *     <li>Delete media statistics by date.</li>
 *     <li>Upload new media statistics based on media events.</li>
 *     <li>Delete media statistics based on media events.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface MediaStatisticServiceInterface {

    /**
     * Retrieves all media statistics.
     * <p>
     * This method returns a list of all available media statistics.
     * </p>
     *
     * @return a list of all media statistics
     */
    List<MediaStatistic> getAllMediaStatistics();

    /**
     * Retrieves media statistics for a specific date.
     * <p>
     * This method fetches the media statistics for the specified date.
     * </p>
     *
     * @param date the date for which the statistics are to be retrieved
     * @return the media statistics for the specified date, or {@code null} if none exist
     */
    MediaStatistic getMediaStatisticByDate(LocalDate date);

    /**
     * Deletes media statistics for a specific date.
     * <p>
     * This method removes media statistics for the given date from the system.
     * </p>
     *
     * @param date the date for which the statistics are to be deleted
     */
    void deleteStatisticByDate(LocalDate date);

    /**
     * Uploads media statistics based on the provided media event.
     * <p>
     * This method creates a new media statistic based on the provided media event.
     * </p>
     *
     * @param mediaEvent the media event containing information for the statistic
     */
    void uploadMediaStatistic(MediaEvent mediaEvent);

    /**
     * Deletes media statistics based on the provided media event.
     * <p>
     * This method removes media statistics associated with the provided media event.
     * </p>
     *
     * @param mediaEvent the media event whose statistics should be deleted
     */
    void deleteMediaStatistic(MediaEvent mediaEvent);
}
