package org.thewhitemage13.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents statistical data related to comments in the system.
 * <p>
 * This entity tracks the number of comments created and deleted on a specific date.
 * It is designed to store aggregated data for analytical or reporting purposes.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identifier for the statistic entry.</li>
 *     <li>Number of comments created and deleted during the specified date.</li>
 *     <li>Date for which the statistics are recorded.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommentStatistic {

    /**
     * Unique identifier for the comment statistic entry.
     * <p>
     * This ID is the primary key and is auto-generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentStatisticId;

    /**
     * The number of comments created on the specified date.
     * <p>
     * This field captures the total count of new comments added.
     * </p>
     */
    private Long numberOfCreatedComments;

    /**
     * The number of comments deleted on the specified date.
     * <p>
     * This field captures the total count of comments removed.
     * </p>
     */
    private Long numberOfDeletedComments;

    /**
     * The date for which the comment statistics are recorded.
     * <p>
     * Represents the day associated with the creation and deletion counts.
     * </p>
     */
    private LocalDate statisticDate;
}
