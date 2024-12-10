package org.thewhitemage13.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.entity.UserStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.interfaces.UserStatisticServiceInterface;
import org.thewhitemage13.repository.UserStatisticRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service implementation for managing {@link UserStatistic} entities.
 * <p>
 * This service provides methods for creating, deleting, and retrieving statistics related to user activity.
 * It interacts with the {@link UserStatisticRepository} to perform CRUD operations on user statistics.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks the creation and removal of users.</li>
 *     <li>Retrieves user statistics for all dates or a specific date.</li>
 *     <li>Deletes statistics for a specific date.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class UserStatisticServiceImpl implements UserStatisticServiceInterface {
    private final UserStatisticRepository userStatisticRepository;

    /**
     * Constructs a {@link UserStatisticServiceImpl} with the provided {@link UserStatisticRepository}.
     *
     * @param userStatisticRepository the repository for interacting with the database
     */
    @Autowired
    public UserStatisticServiceImpl(UserStatisticRepository userStatisticRepository) {
        this.userStatisticRepository = userStatisticRepository;
    }

    /**
     * Retrieves all user statistics.
     *
     * @return a list of all {@link UserStatistic} entities
     */
    @Override
    public List<UserStatistic> getUserStatistics() {
        return userStatisticRepository.findAll();
    }

    /**
     * Retrieves the user statistic for a specific date.
     * <p>
     * If no statistic exists for the given date, a {@link StatisticNotFoundException} is thrown.
     * </p>
     *
     * @param date the date for which to retrieve the user statistic
     * @return the {@link UserStatistic} for the specified date
     * @throws StatisticNotFoundException if no statistic is found for the given date
     */
    @Override
    public UserStatistic getUserStatisticByDate(LocalDate date) {
        return userStatisticRepository.getByStatisticDate(date).orElseThrow(() -> new StatisticNotFoundException("Statistic with id = %s not found".formatted(date)));
    }

    /**
     * Deletes the user statistic for a specific date.
     * <p>
     * This method will remove the user statistic entry from the repository for the given date.
     * </p>
     *
     * @param date the date for which to delete the user statistic
     * @throws StatisticNotFoundException if no statistic is found for the given date
     */
    @Override
    public void deleteStatisticByDate(LocalDate date) {
        UserStatistic statistic = userStatisticRepository.getByStatisticDate(date).orElseThrow(() -> new StatisticNotFoundException("Statistic with id = %s not found".formatted(date)));
        userStatisticRepository.delete(statistic);
    }

    /**
     * Creates or updates user statistics when a new user is registered.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of new users set to 1.
     * If a statistic already exists for the date, the number of new users is incremented.
     * </p>
     *
     * @param userEvent the event containing user information (used to track user registrations)
     */
    @Override
    public void createUserStatistic(UserEvent userEvent) {
        LocalDate today = LocalDate.now();

        UserStatistic statistic = userStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new UserStatistic();
            statistic.setStatisticDate(today);
            statistic.setNewUsers(1L);
            statistic.setRemoteUsers(0L);
        } else {
            statistic.setNewUsers(statistic.getNewUsers() + 1);
        }
        userStatisticRepository.save(statistic);

    }

    /**
     * Deletes or updates user statistics when a user is removed.
     * <p>
     * If no statistic exists for the current date, a new statistic is created with the number of removed users set to 1.
     * If a statistic already exists for the date, the number of removed users is incremented, and the number of new users is decremented.
     * </p>
     *
     * @param userEvent the event containing user information (used to track user removal)
     */
    @Override
    public void remoteUserStatistic(UserEvent userEvent) {
        LocalDate today = LocalDate.now();

        UserStatistic statistic = userStatisticRepository.findByStatisticDate(today);

        if (statistic == null) {
            statistic = new UserStatistic();
            statistic.setStatisticDate(today);
            statistic.setNewUsers(0L);
            statistic.setRemoteUsers(1L);
        } else {
            statistic.setRemoteUsers(statistic.getRemoteUsers() + 1);
            statistic.setNewUsers(statistic.getNewUsers() - 1);
        }
        userStatisticRepository.save(statistic);

    }
}
