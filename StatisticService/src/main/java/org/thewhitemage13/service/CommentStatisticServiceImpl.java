package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.entity.CommentStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.interfaces.CommentStatisticServiceInterface;
import org.thewhitemage13.repository.CommentStatisticRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service implementation for managing {@link CommentStatistic} entities.
 * <p>
 * This service provides methods to create, delete, retrieve, and show comment statistics for a given date.
 * It interacts with the {@link CommentStatisticRepository} to perform CRUD operations on the comment statistics.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Creates a new comment statistic or updates the existing one for the current date.</li>
 *     <li>Deletes the comment statistic or updates it based on deleted comments.</li>
 *     <li>Fetches all comment statistics or the statistic for a specific date.</li>
 *     <li>Deletes a comment statistic by a specific date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class CommentStatisticServiceImpl implements CommentStatisticServiceInterface {
    private final CommentStatisticRepository commentStatisticRepository;

    /**
     * Constructs a {@link CommentStatisticServiceImpl} with the provided {@link CommentStatisticRepository}.
     *
     * @param commentStatisticRepository the repository for interacting with the database
     */
    @Autowired
    public CommentStatisticServiceImpl(CommentStatisticRepository commentStatisticRepository) {
        this.commentStatisticRepository = commentStatisticRepository;
    }

    /**
     * Creates or updates the comment statistic for the current date.
     * <p>
     * If no statistics exist for the current date, a new entry is created. If statistics already exist,
     * the number of created comments is incremented.
     * </p>
     */
    @Override
    public void createCommentStatistic() {
        LocalDate today = LocalDate.now();

        CommentStatistic statistic = commentStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new CommentStatistic();
            statistic.setStatisticDate(today);
            statistic.setNumberOfCreatedComments(1L);
            statistic.setNumberOfDeletedComments(0L);
        } else {
            statistic.setNumberOfCreatedComments(statistic.getNumberOfCreatedComments() + 1);
        }
        commentStatisticRepository.save(statistic);
    }

    /**
     * Deletes or updates the comment statistic for the current date when a comment is deleted.
     * <p>
     * If no statistics exist for the current date, a new entry is created. If statistics already exist,
     * the number of created and deleted comments is updated accordingly.
     * </p>
     */
    @Override
    public void deleteCommentStatistic() {
        LocalDate today = LocalDate.now();

        CommentStatistic statistic = commentStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new CommentStatistic();
            statistic.setStatisticDate(today);
            statistic.setNumberOfCreatedComments(0L);
            statistic.setNumberOfDeletedComments(1L);
        } else {
            statistic.setNumberOfDeletedComments(statistic.getNumberOfDeletedComments() + 1);
            statistic.setNumberOfCreatedComments(statistic.getNumberOfCreatedComments() - 1);
        }
        commentStatisticRepository.save(statistic);
    }

    /**
     * Retrieves all the comment statistics.
     *
     * @return a list of all {@link CommentStatistic} entities
     */
    @Override
    public List<CommentStatistic> showAllStatistics() {
        return commentStatisticRepository.findAll();
    }

    /**
     * Retrieves the comment statistic for a specific date.
     * <p>
     * If no statistic exists for the given date, a {@link StatisticNotFoundException} is thrown.
     * </p>
     *
     * @param date the date for which to retrieve the comment statistic
     * @return the {@link CommentStatistic} for the specified date
     * @throws StatisticNotFoundException if no statistic is found for the given date
     */
    @Override
    public CommentStatistic showStatisticsByDate(LocalDate date) {
        return commentStatisticRepository.getByStatisticDate(date)
                .orElseThrow(() -> new StatisticNotFoundException("Statistic with date = %s not found".formatted(date)));
    }

    /**
     * Deletes the comment statistic for a specific date.
     * <p>
     * This method will remove the comment statistic entry from the repository for the given date.
     * </p>
     *
     * @param date the date for which to delete the comment statistic
     */
    @Override
    public void deleteStatisticByDate(LocalDate date) {
        CommentStatistic statistic = commentStatisticRepository.findByStatisticDate(date);
        commentStatisticRepository.delete(statistic);
    }
}
