package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.entity.LikeStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.interfaces.LikeStatisticServiceInterface;
import org.thewhitemage13.repository.LikeStatisticRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service implementation for managing {@link LikeStatistic} entities.
 * <p>
 * This service provides methods to create, delete, retrieve, and show like statistics for posts and comments.
 * It interacts with the {@link LikeStatisticRepository} to perform CRUD operations on the like statistics.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Creates or updates like statistics for posts and comments.</li>
 *     <li>Deletes like statistics when likes are removed.</li>
 *     <li>Retrieves all like statistics or statistics for a specific date.</li>
 *     <li>Deletes a like statistic for a specific date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class LikeStatisticServiceImpl implements LikeStatisticServiceInterface {
    private final LikeStatisticRepository likeStatisticRepository;

    /**
     * Constructs a {@link LikeStatisticServiceImpl} with the provided {@link LikeStatisticRepository}.
     *
     * @param likeStatisticRepository the repository for interacting with the database
     */
    @Autowired
    public LikeStatisticServiceImpl(LikeStatisticRepository likeStatisticRepository) {
        this.likeStatisticRepository = likeStatisticRepository;
    }

    /**
     * Retrieves all like statistics.
     *
     * @return a list of all {@link LikeStatistic} entities
     */
    @Override
    public List<LikeStatistic> getAllLikeStatistics() {
        return likeStatisticRepository.findAll();
    }

    /**
     * Retrieves the like statistic for a specific date.
     * <p>
     * If no statistic exists for the given date, a {@link StatisticNotFoundException} is thrown.
     * </p>
     *
     * @param date the date for which to retrieve the like statistic
     * @return the {@link LikeStatistic} for the specified date
     * @throws StatisticNotFoundException if no statistic is found for the given date
     */
    @Override
    public LikeStatistic getLikeStatisticByDate(LocalDate date) {
        return likeStatisticRepository.getByStatisticDate(date).orElseThrow(() -> new StatisticNotFoundException("Statistic with date = %s not found".formatted(date)));
    }

    /**
     * Deletes the like statistic for a specific date.
     * <p>
     * This method will remove the like statistic entry from the repository for the given date.
     * </p>
     *
     * @param date the date for which to delete the like statistic
     * @throws StatisticNotFoundException if no statistic is found for the given date
     */
    @Override
    public void deleteStatisticByDate(LocalDate date) {
        LikeStatistic statistic = likeStatisticRepository.getByStatisticDate(date).orElseThrow(() -> new StatisticNotFoundException("Statistic with date = %s not found".formatted(date)));
        likeStatisticRepository.delete(statistic);
    }

    /**
     * Creates or updates the like statistic for posts.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of post likes set to 1.
     * If a statistic exists, the number of post likes is incremented.
     * </p>
     */
    @Override
    public void createLikePostStatistic() {
        LocalDate today = LocalDate.now();

        LikeStatistic statistic = likeStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new LikeStatistic();
            statistic.setStatisticDate(today);
            statistic.setPostLike(1L);
            statistic.setCommentLike(0L);
            statistic.setRemovePostLike(0L);
            statistic.setRemoveCommentLike(0L);
        } else {
            statistic.setPostLike(statistic.getPostLike() + 1);
        }
        likeStatisticRepository.save(statistic);

    }

    /**
     * Deletes or updates the like statistic for posts when a post like is removed.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of removed post likes set to 1.
     * If a statistic exists, the number of post likes and removed post likes are updated accordingly.
     * </p>
     */
    @Override
    public void deleteLikePostStatistic() {
        LocalDate today = LocalDate.now();

        LikeStatistic statistic = likeStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new LikeStatistic();
            statistic.setStatisticDate(today);
            statistic.setPostLike(0L);
            statistic.setCommentLike(0L);
            statistic.setRemovePostLike(1L);
            statistic.setRemoveCommentLike(0L);
        } else {
            statistic.setRemovePostLike(statistic.getRemovePostLike() + 1);
            statistic.setPostLike(statistic.getPostLike() - 1);
        }
        likeStatisticRepository.save(statistic);

    }

    /**
     * Creates or updates the like statistic for comments.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of comment likes set to 1.
     * If a statistic exists, the number of comment likes is incremented.
     * </p>
     */
    @Override
    public void createLikeCommentStatistic() {
        LocalDate today = LocalDate.now();

        LikeStatistic statistic = likeStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new LikeStatistic();
            statistic.setStatisticDate(today);
            statistic.setPostLike(0L);
            statistic.setCommentLike(1L);
            statistic.setRemovePostLike(0L);
            statistic.setRemoveCommentLike(0L);
        } else {
            statistic.setCommentLike(statistic.getCommentLike() + 1);
        }
        likeStatisticRepository.save(statistic);

    }

    /**
     * Deletes or updates the like statistic for comments when a comment like is removed.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of removed comment likes set to 1.
     * If a statistic exists, the number of comment likes and removed comment likes are updated accordingly.
     * </p>
     */
    @Override
    public void deleteLikeCommentStatistic() {
        LocalDate today = LocalDate.now();

        LikeStatistic statistic = likeStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new LikeStatistic();
            statistic.setStatisticDate(today);
            statistic.setPostLike(0L);
            statistic.setCommentLike(0L);
            statistic.setRemovePostLike(0L);
            statistic.setRemoveCommentLike(1L);
        } else {
            statistic.setRemoveCommentLike(statistic.getRemoveCommentLike() + 1);
            statistic.setCommentLike(statistic.getCommentLike() - 1);
        }
        likeStatisticRepository.save(statistic);

    }
}
