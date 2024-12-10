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
 * Represents statistical data related to user activities in the system.
 * <p>
 * This entity tracks the number of new users registered and users removed for a specific date.
 * It is used for monitoring user trends and generating reports.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identifier for the user statistic entry.</li>
 *     <li>Counts for new users and removed users.</li>
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
public class UserStatistic {

    /**
     * Unique identifier for the user statistic entry.
     * <p>
     * This ID is the primary key and is auto-generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userStatisticId;

    /**
     * The number of new users registered on the specified date.
     * <p>
     * This field captures the total count of users who signed up.
     * </p>
     */
    private Long newUsers;

    /**
     * The number of users removed on the specified date.
     * <p>
     * This field captures the total count of users who were deactivated or removed.
     * </p>
     */
    private Long remoteUsers;

    /**
     * The date for which the user statistics are recorded.
     * <p>
     * Represents the day associated with the user activities.
     * </p>
     */
    private LocalDate statisticDate;
}
