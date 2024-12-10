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
 * Represents statistical data related to likes in the system.
 * <p>
 * This entity tracks the number of likes and unlikes for posts and comments on a specific date.
 * It is designed to store aggregated data for analytical or reporting purposes.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identifier for the like statistic entry.</li>
 *     <li>Counts for likes and unlikes on posts and comments.</li>
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
public class LikeStatistic {

    /**
     * Unique identifier for the like statistic entry.
     * <p>
     * This ID is the primary key and is auto-generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeStatisticId;

    /**
     * The number of likes on comments for the specified date.
     * <p>
     * This field captures the total count of new likes added to comments.
     * </p>
     */
    private Long commentLike;

    /**
     * The number of likes on posts for the specified date.
     * <p>
     * This field captures the total count of new likes added to posts.
     * </p>
     */
    private Long postLike;

    /**
     * The number of likes removed from posts on the specified date.
     * <p>
     * This field captures the total count of likes removed from posts.
     * </p>
     */
    private Long removePostLike;

    /**
     * The number of likes removed from comments on the specified date.
     * <p>
     * This field captures the total count of likes removed from comments.
     * </p>
     */
    private Long removeCommentLike;

    /**
     * The date for which the like statistics are recorded.
     * <p>
     * Represents the day associated with the like and unlike counts.
     * </p>
     */
    private LocalDate statisticDate;
}
