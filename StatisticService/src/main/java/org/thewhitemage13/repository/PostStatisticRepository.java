package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thewhitemage13.entity.PostStatistic;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing {@link PostStatistic} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for performing CRUD operations on
 * {@link PostStatistic} entities. It includes custom query methods to find post statistics based on
 * the statistic date.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Find post statistics by a specific statistic date.</li>
 *     <li>Retrieve an {@link Optional} of post statistics for a given statistic date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PostStatisticRepository extends JpaRepository<PostStatistic, Long> {

    /**
     * Finds a post statistic by its statistic date.
     * <p>
     * This method searches for the post statistic associated with the given date.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the post statistic
     * @return the post statistic for the given date, or {@code null} if no statistic exists
     */
    PostStatistic findByStatisticDate(LocalDate statisticDate);

    /**
     * Retrieves an {@link Optional} of a post statistic by its statistic date.
     * <p>
     * This method returns an {@link Optional} containing the post statistic if it exists for the
     * specified date, or an empty {@link Optional} if no statistic is found.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the post statistic
     * @return an {@link Optional} containing the post statistic, or {@link Optional#empty()} if no statistic exists
     */
    Optional<PostStatistic> getByStatisticDate(LocalDate statisticDate);
}
