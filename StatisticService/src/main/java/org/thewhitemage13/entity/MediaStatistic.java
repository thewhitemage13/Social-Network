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
 * Represents statistical data related to media operations in the system.
 * <p>
 * This entity tracks the number of files uploaded and deleted, as well as the total size of the files,
 * for a specific date. It is used for analytical and reporting purposes.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identifier for the media statistic entry.</li>
 *     <li>Counts for uploaded and deleted files.</li>
 *     <li>Total size of the files processed.</li>
 *     <li>Date for which the statistics are recorded.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MediaStatistic {

    /**
     * Unique identifier for the media statistic entry.
     * <p>
     * This ID is the primary key and is auto-generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaStatisticId;

    /**
     * The number of files uploaded on the specified date.
     * <p>
     * This field captures the total count of newly uploaded files.
     * </p>
     */
    private Long numberOfUploadedFiles;

    /**
     * The number of files deleted on the specified date.
     * <p>
     * This field captures the total count of files removed from the system.
     * </p>
     */
    private Long numberOfDeletedFiles;

    /**
     * The total size of all files processed on the specified date.
     * <p>
     * This field stores the aggregated size of all uploaded and deleted files, measured in bytes.
     * </p>
     */
    private Double totalFileSize;

    /**
     * The date for which the media statistics are recorded.
     * <p>
     * Represents the day associated with the media operations.
     * </p>
     */
    private LocalDate statisticDate;
}