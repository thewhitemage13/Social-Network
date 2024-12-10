package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.entity.MediaStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.repository.MediaStatisticRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaStatisticServiceImplTest {
    @Mock
    private MediaStatisticRepository mediaStatisticRepository;
    @InjectMocks
    private MediaStatisticServiceImpl mediaStatisticServiceImpl;

    @Test
    void getAllMediaStatistics_ShouldReturnAllStatistics() {
        // Arrange
        MediaStatistic stat1 = new MediaStatistic();
        MediaStatistic stat2 = new MediaStatistic();
        when(mediaStatisticRepository.findAll()).thenReturn(List.of(stat1, stat2));

        // Act
        List<MediaStatistic> result = mediaStatisticServiceImpl.getAllMediaStatistics();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mediaStatisticRepository, times(1)).findAll();
    }

    @Test
    void getMediaStatisticByDate_ShouldReturnStatistic_WhenExists() {
        // Arrange
        LocalDate date = LocalDate.now();
        MediaStatistic expectedStatistic = new MediaStatistic();
        when(mediaStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.of(expectedStatistic));

        // Act
        MediaStatistic result = mediaStatisticServiceImpl.getMediaStatisticByDate(date);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStatistic, result);
        verify(mediaStatisticRepository, times(1)).getByStatisticDate(date);
    }

    @Test
    void getMediaStatisticByDate_ShouldThrowException_WhenNotFound() {
        // Arrange
        LocalDate date = LocalDate.now();
        when(mediaStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.empty());

        // Act & Assert
        StatisticNotFoundException exception = assertThrows(
                StatisticNotFoundException.class,
                () -> mediaStatisticServiceImpl.getMediaStatisticByDate(date)
        );
        assertTrue(exception.getMessage().contains(date.toString()));
        verify(mediaStatisticRepository, times(1)).getByStatisticDate(date);
    }
}