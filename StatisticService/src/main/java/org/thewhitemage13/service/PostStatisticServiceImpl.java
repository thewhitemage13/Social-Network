package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.entity.PostStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.interfaces.PostStatisticServiceInterface;
import org.thewhitemage13.repository.PostStatisticRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service implementation for managing {@link PostStatistic} entities.
 * <p>
 * This service provides methods for creating, deleting, and retrieving statistics related to posts.
 * It interacts with the {@link PostStatisticRepository} to perform CRUD operations on post statistics.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks the creation and deletion of posts.</li>
 *     <li>Retrieves post statistics for all dates or a specific date.</li>
 *     <li>Deletes statistics for a specific date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class PostStatisticServiceImpl implements PostStatisticServiceInterface {
    private final PostStatisticRepository postStatisticRepository;

    /**
     * Constructs a {@link PostStatisticServiceImpl} with the provided {@link PostStatisticRepository}.
     *
     * @param postStatisticRepository the repository for interacting with the database
     */
    @Autowired
    public PostStatisticServiceImpl(PostStatisticRepository postStatisticRepository) {
        this.postStatisticRepository = postStatisticRepository;
    }

    /**
     * Retrieves all post statistics.
     *
     * @return a list of all {@link PostStatistic} entities
     */
    @Override
    public List<PostStatistic> getPostStatistics() {
        return postStatisticRepository.findAll();
    }

    /**
     * Retrieves the post statistic for a specific date.
     * <p>
     * If no statistic exists for the given date, a {@link StatisticNotFoundException} is thrown.
     * </p>
     *
     * @param date the date for which to retrieve the post statistic
     * @return the {@link PostStatistic} for the specified date
     * @throws StatisticNotFoundException if no statistic is found for the given date
     */
    @Override
    public PostStatistic getStatisticByDate(LocalDate date) {
        return postStatisticRepository.getByStatisticDate(date).orElseThrow(() -> new StatisticNotFoundException("Statistic with id = %s not found".formatted(date)));
    }

    /**
     * Deletes the post statistic for a specific date.
     * <p>
     * This method will remove the post statistic entry from the repository for the given date.
     * </p>
     *
     * @param date the date for which to delete the post statistic
     * @throws StatisticNotFoundException if no statistic is found for the given date
     */
    @Override
    public void deleteStatisticByDate(LocalDate date) {
        PostStatistic statistic = postStatisticRepository.getByStatisticDate(date).orElseThrow(() -> new StatisticNotFoundException("Statistic with id = %s not found".formatted(date)));
        postStatisticRepository.delete(statistic);
    }

    /**
     * Creates or updates post statistics when a post is created.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of created posts set to 1.
     * If a statistic already exists for the date, the number of created posts is incremented.
     * </p>
     *
     * @param postEvent the event containing the post information
     */
    @Override
    public void createPostStatistic(PostEvent postEvent) {
        LocalDate today = LocalDate.now();

        PostStatistic statistic = postStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new PostStatistic();
            statistic.setStatisticDate(today);
            statistic.setPostsCreated(1L);
            statistic.setPostsDeleted(0L);
        } else {
            statistic.setPostsCreated(statistic.getPostsCreated() + 1);
        }
        postStatisticRepository.save(statistic);
    }

    /**
     * Deletes or updates post statistics when a post is deleted.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of deleted posts set to 1.
     * If a statistic already exists for the date, the number of deleted posts is incremented, and the number of created posts is decremented.
     * </p>
     *
     * @param postEvent the event containing the post information
     */
    @Override
    public void deletePostStatistic(PostEvent postEvent) {
        LocalDate today = LocalDate.now();

        PostStatistic statistic = postStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new PostStatistic();
            statistic.setStatisticDate(today);
            statistic.setPostsCreated(0L);
            statistic.setPostsDeleted(1L);
        } else {
            statistic.setPostsDeleted(statistic.getPostsDeleted() + 1);
            statistic.setPostsCreated(statistic.getPostsCreated() - 1);
        }
        postStatisticRepository.save(statistic);
    }
}
