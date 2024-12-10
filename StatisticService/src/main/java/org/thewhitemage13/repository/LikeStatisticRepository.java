package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thewhitemage13.entity.LikeStatistic;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing {@link LikeStatistic} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for performing CRUD operations on
 * {@link LikeStatistic} entities. It includes custom query methods to find like statistics based on
 * the statistic date.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Find like statistics by a specific statistic date.</li>
 *     <li>Retrieve an {@link Optional} of like statistics for a given statistic date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface LikeStatisticRepository extends JpaRepository<LikeStatistic, Long> {

    /**
     * Finds a like statistic by its statistic date.
     * <p>
     * This method searches for the like statistic associated with the given date.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the like statistic
     * @return the like statistic for the given date, or {@code null} if no statistic exists
     */
    LikeStatistic findByStatisticDate(LocalDate statisticDate);

    /**
     * Retrieves an {@link Optional} of a like statistic by its statistic date.
     * <p>
     * This method returns an {@link Optional} containing the like statistic if it exists for the
     * specified date, or an empty {@link Optional} if no statistic is found.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the like statistic
     * @return an {@link Optional} containing the like statistic, or {@link Optional#empty()} if no statistic exists
     */
    Optional<LikeStatistic> getByStatisticDate(LocalDate statisticDate);
}
