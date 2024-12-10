package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.entity.PostStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.repository.PostStatisticRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostStatisticServiceImplTest {
    @Mock
    private PostStatisticRepository postStatisticRepository;
    @InjectMocks
    private PostStatisticServiceImpl postStatisticServiceImpl;

    @Test
    void getPostStatistics_ShouldReturnAllStatistics() {
        // Arrange
        PostStatistic stat1 = new PostStatistic();
        PostStatistic stat2 = new PostStatistic();
        when(postStatisticRepository.findAll()).thenReturn(List.of(stat1, stat2));

        // Act
        List<PostStatistic> result = postStatisticServiceImpl.getPostStatistics();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(postStatisticRepository, times(1)).findAll();
    }

    @Test
    void getStatisticByDate_ShouldReturnStatistic_WhenExists() {
        // Arrange
        LocalDate date = LocalDate.now();
        PostStatistic expectedStatistic = new PostStatistic();
        when(postStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.of(expectedStatistic));

        // Act
        PostStatistic result = postStatisticServiceImpl.getStatisticByDate(date);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStatistic, result);
        verify(postStatisticRepository, times(1)).getByStatisticDate(date);
    }

    @Test
    void getStatisticByDate_ShouldThrowException_WhenNotFound() {
        // Arrange
        LocalDate date = LocalDate.now();
        when(postStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticNotFoundException exception = assertThrows(
                StatisticNotFoundException.class,
                () -> postStatisticServiceImpl.getStatisticByDate(date)
        );
        assertTrue(exception.getMessage().contains(date.toString()));
        verify(postStatisticRepository, times(1)).getByStatisticDate(date);
    }

    @Test
    void deleteStatisticByDate_ShouldDeleteStatistic_WhenExists() {
        // Arrange
        LocalDate date = LocalDate.now();
        PostStatistic statistic = new PostStatistic();
        when(postStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.of(statistic));

        // Act
        postStatisticServiceImpl.deleteStatisticByDate(date);

        // Assert
        verify(postStatisticRepository, times(1)).delete(statistic);
        verify(postStatisticRepository, times(1)).getByStatisticDate(date);
    }

    @Test
    void deleteStatisticByDate_ShouldThrowException_WhenNotFound() {
        // Arrange
        LocalDate date = LocalDate.now();
        when(postStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticNotFoundException exception = assertThrows(
                StatisticNotFoundException.class,
                () -> postStatisticServiceImpl.deleteStatisticByDate(date)
        );
        assertTrue(exception.getMessage().contains(date.toString()));
        verify(postStatisticRepository, times(1)).getByStatisticDate(date);
    }
}