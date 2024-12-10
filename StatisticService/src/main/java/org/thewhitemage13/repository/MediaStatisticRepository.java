package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thewhitemage13.entity.MediaStatistic;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing {@link MediaStatistic} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for performing CRUD operations on
 * {@link MediaStatistic} entities. It includes custom query methods to find media statistics based on
 * the statistic date.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Find media statistics by a specific statistic date.</li>
 *     <li>Retrieve an {@link Optional} of media statistics for a given statistic date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface MediaStatisticRepository extends JpaRepository<MediaStatistic, Long> {

    /**
     * Finds a media statistic by its statistic date.
     * <p>
     * This method searches for the media statistic associated with the given date.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the media statistic
     * @return the media statistic for the given date, or {@code null} if no statistic exists
     */
    MediaStatistic findByStatisticDate(LocalDate statisticDate);

    /**
     * Retrieves an {@link Optional} of a media statistic by its statistic date.
     * <p>
     * This method returns an {@link Optional} containing the media statistic if it exists for the
     * specified date, or an empty {@link Optional} if no statistic is found.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the media statistic
     * @return an {@link Optional} containing the media statistic, or {@link Optional#empty()} if no statistic exists
     */
    Optional<MediaStatistic> getByStatisticDate(LocalDate statisticDate);
}
