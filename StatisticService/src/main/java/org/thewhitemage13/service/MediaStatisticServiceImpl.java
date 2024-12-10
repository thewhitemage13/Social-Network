package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.MediaEvent;
import org.thewhitemage13.entity.MediaStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.interfaces.MediaStatisticServiceInterface;
import org.thewhitemage13.repository.MediaStatisticRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service implementation for managing {@link MediaStatistic} entities.
 * <p>
 * This service provides methods to create, delete, and retrieve media statistics.
 * It interacts with the {@link MediaStatisticRepository} to perform CRUD operations on media statistics.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Uploads new media statistics for uploaded files.</li>
 *     <li>Deletes media statistics when files are deleted.</li>
 *     <li>Retrieves all media statistics or statistics for a specific date.</li>
 *     <li>Deletes media statistics for a specific date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class MediaStatisticServiceImpl implements MediaStatisticServiceInterface {
    private final MediaStatisticRepository mediaStatisticRepository;

    /**
     * Constructs a {@link MediaStatisticServiceImpl} with the provided {@link MediaStatisticRepository}.
     *
     * @param mediaStatisticRepository the repository for interacting with the database
     */
    @Autowired
    public MediaStatisticServiceImpl(MediaStatisticRepository mediaStatisticRepository) {
        this.mediaStatisticRepository = mediaStatisticRepository;
    }

    /**
     * Retrieves all media statistics.
     *
     * @return a list of all {@link MediaStatistic} entities
     */
    @Override
    public List<MediaStatistic> getAllMediaStatistics() {
        return mediaStatisticRepository.findAll();
    }

    /**
     * Retrieves the media statistic for a specific date.
     * <p>
     * If no statistic exists for the given date, a {@link StatisticNotFoundException} is thrown.
     * </p>
     *
     * @param date the date for which to retrieve the media statistic
     * @return the {@link MediaStatistic} for the specified date
     * @throws StatisticNotFoundException if no statistic is found for the given date
     */
    @Override
    public MediaStatistic getMediaStatisticByDate(LocalDate date) {
        return mediaStatisticRepository.getByStatisticDate(date).orElseThrow(() -> new StatisticNotFoundException("Statistic with id = %s not found".formatted(date)));
    }

    /**
     * Deletes the media statistic for a specific date.
     * <p>
     * This method will remove the media statistic entry from the repository for the given date.
     * </p>
     *
     * @param date the date for which to delete the media statistic
     * @throws StatisticNotFoundException if no statistic is found for the given date
     */
    @Override
    public void deleteStatisticByDate(LocalDate date) {
        MediaStatistic statistic = mediaStatisticRepository.getByStatisticDate(date).orElseThrow(() -> new StatisticNotFoundException("Statistic with id = %s not found".formatted(date)));
        mediaStatisticRepository.delete(statistic);
    }

    /**
     * Uploads new media statistics when a media file is uploaded.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of uploaded files set to 1.
     * The total file size is updated with the size of the uploaded file.
     * If a statistic exists, the number of uploaded files and the total file size are updated accordingly.
     * </p>
     *
     * @param mediaEvent the event containing the media file information
     */
    @Override
    public void uploadMediaStatistic(MediaEvent mediaEvent) {
        LocalDate today = LocalDate.now();

        MediaStatistic statistic = mediaStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new MediaStatistic();
            statistic.setStatisticDate(today);
            statistic.setNumberOfUploadedFiles(1L);
            statistic.setTotalFileSize(mediaEvent.getFileSize());
            statistic.setNumberOfDeletedFiles(0L);
        } else {
            statistic.setNumberOfUploadedFiles(statistic.getNumberOfUploadedFiles() + 1);
            statistic.setTotalFileSize(statistic.getTotalFileSize() + mediaEvent.getFileSize());
        }
        mediaStatisticRepository.save(statistic);
    }

    /**
     * Deletes or updates the media statistic when a media file is deleted.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of deleted files set to 1.
     * The total file size is reduced by the size of the deleted file.
     * If a statistic exists, the number of deleted files and the total file size are updated accordingly.
     * </p>
     *
     * @param mediaEvent the event containing the media file information
     */
    @Override
    public void deleteMediaStatistic(MediaEvent mediaEvent) {
        LocalDate today = LocalDate.now();

        MediaStatistic statistic = mediaStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new MediaStatistic();
            statistic.setStatisticDate(today);
            statistic.setNumberOfUploadedFiles(0L);
            statistic.setTotalFileSize(0.0);
            statistic.setNumberOfDeletedFiles(1L);
        } else {
            statistic.setNumberOfDeletedFiles(statistic.getNumberOfDeletedFiles() + 1);
            statistic.setTotalFileSize(statistic.getTotalFileSize() - mediaEvent.getFileSize());
        }
        mediaStatisticRepository.save(statistic);
    }
}
