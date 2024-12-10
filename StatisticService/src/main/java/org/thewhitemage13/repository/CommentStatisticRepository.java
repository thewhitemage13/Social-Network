package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thewhitemage13.entity.CommentStatistic;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing {@link CommentStatistic} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for performing CRUD operations on
 * {@link CommentStatistic} entities. It includes custom query methods to find and delete statistics
 * based on the statistic date.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Find comment statistics by specific statistic date.</li>
 *     <li>Delete comment statistics by statistic date.</li>
 *     <li>Retrieve an {@link Optional} of comment statistics for a given statistic date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface CommentStatisticRepository extends JpaRepository<CommentStatistic, Long> {

    /**
     * Finds a comment statistic by its statistic date.
     * <p>
     * This method searches for the comment statistic associated with the given date.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the comment statistic
     * @return the comment statistic for the given date, or {@code null} if no statistic exists
     */
    CommentStatistic findByStatisticDate(LocalDate statisticDate);

    /**
     * Deletes a comment statistic by its statistic date.
     * <p>
     * This method removes the comment statistic associated with the given date.
     * </p>
     *
     * @param statisticDate the date for which to delete the comment statistic
     */
    void deleteByStatisticDate(LocalDate statisticDate);

    /**
     * Retrieves an {@link Optional} of a comment statistic by its statistic date.
     * <p>
     * This method returns an {@link Optional} containing the comment statistic if it exists for the
     * specified date, or an empty {@link Optional} if no statistic is found.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the comment statistic
     * @return an {@link Optional} containing the comment statistic, or {@link Optional#empty()} if no statistic exists
     */
    Optional<CommentStatistic> getByStatisticDate(LocalDate statisticDate);
}
