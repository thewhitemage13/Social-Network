package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.UserEvent;
import org.thewhitemage13.entity.UserStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.repository.UserStatisticRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserStatisticServiceImplTest {
    @Mock
    private UserStatisticRepository userStatisticRepository;
    @InjectMocks
    private UserStatisticServiceImpl userStatisticServiceImpl;

    @Test
    void getUserStatistics_ShouldReturnAllStatistics() {
        // Arrange
        UserStatistic stat1 = new UserStatistic();
        UserStatistic stat2 = new UserStatistic();
        when(userStatisticRepository.findAll()).thenReturn(List.of(stat1, stat2));

        // Act
        List<UserStatistic> result = userStatisticServiceImpl.getUserStatistics();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userStatisticRepository, times(1)).findAll();
    }

    @Test
    void getUserStatisticByDate_ShouldReturnStatistic_WhenExists() {
        // Arrange
        LocalDate date = LocalDate.now();
        UserStatistic expectedStatistic = new UserStatistic();
        when(userStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.of(expectedStatistic));

        // Act
        UserStatistic result = userStatisticServiceImpl.getUserStatisticByDate(date);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStatistic, result);
        verify(userStatisticRepository, times(1)).getByStatisticDate(date);
    }

    @Test
    void getUserStatisticByDate_ShouldThrowException_WhenNotFound() {
        // Arrange
        LocalDate date = LocalDate.now();
        when(userStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticNotFoundException exception = assertThrows(
                StatisticNotFoundException.class,
                () -> userStatisticServiceImpl.getUserStatisticByDate(date)
        );
        assertTrue(exception.getMessage().contains(date.toString()));
        verify(userStatisticRepository, times(1)).getByStatisticDate(date);
    }

    @Test
    void deleteStatisticByDate_ShouldDeleteStatistic_WhenExists() {
        // Arrange
        LocalDate date = LocalDate.now();
        UserStatistic statistic = new UserStatistic();
        when(userStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.of(statistic));

        // Act
        userStatisticServiceImpl.deleteStatisticByDate(date);

        // Assert
        verify(userStatisticRepository, times(1)).delete(statistic);
        verify(userStatisticRepository, times(1)).getByStatisticDate(date);
    }

    @Test
    void deleteStatisticByDate_ShouldThrowException_WhenNotFound() {
        // Arrange
        LocalDate date = LocalDate.now();
        when(userStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticNotFoundException exception = assertThrows(
                StatisticNotFoundException.class,
                () -> userStatisticServiceImpl.deleteStatisticByDate(date)
        );
        assertTrue(exception.getMessage().contains(date.toString()));
        verify(userStatisticRepository, times(1)).getByStatisticDate(date);
    }

    @Test
    void createUserStatistic_ShouldCreateNewStatistic_WhenNotExists() {
        // Arrange
        LocalDate today = LocalDate.now();
        UserEvent userEvent = new UserEvent();
        when(userStatisticRepository.findByStatisticDate(today)).thenReturn(null);

        // Act
        userStatisticServiceImpl.createUserStatistic(userEvent);

        // Assert
        verify(userStatisticRepository, times(1)).save(Mockito.any(UserStatistic.class));
    }

    @Test
    void createUserStatistic_ShouldUpdateStatistic_WhenExists() {
        // Arrange
        LocalDate today = LocalDate.now();
        UserEvent userEvent = new UserEvent();
        UserStatistic existingStatistic = new UserStatistic();
        existingStatistic.setStatisticDate(today);
        existingStatistic.setNewUsers(5L);
        when(userStatisticRepository.findByStatisticDate(today)).thenReturn(existingStatistic);

        // Act
        userStatisticServiceImpl.createUserStatistic(userEvent);

        // Assert
        verify(userStatisticRepository, times(1)).save(existingStatistic);
        assertEquals(6L, existingStatistic.getNewUsers());
    }

    @Test
    void remoteUserStatistic_ShouldCreateNewStatistic_WhenNotExists() {
        // Arrange
        LocalDate today = LocalDate.now();
        UserEvent userEvent = new UserEvent();
        when(userStatisticRepository.findByStatisticDate(today)).thenReturn(null);

        // Act
        userStatisticServiceImpl.remoteUserStatistic(userEvent);

        // Assert
        verify(userStatisticRepository, times(1)).save(Mockito.any(UserStatistic.class));
    }

    @Test
    void remoteUserStatistic_ShouldUpdateStatistic_WhenExists() {
        // Arrange
        LocalDate today = LocalDate.now();
        UserEvent userEvent = new UserEvent();
        UserStatistic existingStatistic = new UserStatistic();
        existingStatistic.setStatisticDate(today);
        existingStatistic.setNewUsers(5L);
        existingStatistic.setRemoteUsers(2L);
        when(userStatisticRepository.findByStatisticDate(today)).thenReturn(existingStatistic);

        // Act
        userStatisticServiceImpl.remoteUserStatistic(userEvent);

        // Assert
        verify(userStatisticRepository, times(1)).save(existingStatistic);
        assertEquals(4L, existingStatistic.getNewUsers());
        assertEquals(3L, existingStatistic.getRemoteUsers());
    }
}