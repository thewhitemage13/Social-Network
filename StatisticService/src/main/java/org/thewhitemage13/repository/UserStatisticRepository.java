package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thewhitemage13.entity.UserStatistic;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing {@link UserStatistic} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods for performing CRUD operations on
 * {@link UserStatistic} entities. It includes custom query methods to find user statistics based on
 * the statistic date.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Find user statistics by a specific statistic date.</li>
 *     <li>Retrieve an {@link Optional} of user statistics for a given statistic date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface UserStatisticRepository extends JpaRepository<UserStatistic, Long> {

    /**
     * Finds a user statistic by its statistic date.
     * <p>
     * This method searches for the user statistic associated with the given date.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the user statistic
     * @return the user statistic for the given date, or {@code null} if no statistic exists
     */
    UserStatistic findByStatisticDate(LocalDate statisticDate);

    /**
     * Retrieves an {@link Optional} of a user statistic by its statistic date.
     * <p>
     * This method returns an {@link Optional} containing the user statistic if it exists for the
     * specified date, or an empty {@link Optional} if no statistic is found.
     * </p>
     *
     * @param statisticDate the date for which to retrieve the user statistic
     * @return an {@link Optional} containing the user statistic, or {@link Optional#empty()} if no statistic exists
     */
    Optional<UserStatistic> getByStatisticDate(LocalDate statisticDate);
}
