package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.entity.CommentStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.repository.CommentStatisticRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentStatisticServiceImplTest {
    @Mock
    private CommentStatisticRepository commentStatisticRepository;
    @InjectMocks
    private CommentStatisticServiceImpl commentStatisticServiceImpl;

    // Test for createCommentStatistic (When statistic doesn't exist)
    @Test
    void createCommentStatistic_ShouldCreateNewStatistic_WhenNotExists() {
        LocalDate today = LocalDate.now();
        CommentStatistic statistic = null; // No statistic for today

        when(commentStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);
        when(commentStatisticRepository.save(any(CommentStatistic.class))).thenReturn(new CommentStatistic());

        commentStatisticServiceImpl.createCommentStatistic();

        // Verify save method is called
        verify(commentStatisticRepository, times(1)).save(any(CommentStatistic.class));
    }

    // Test for createCommentStatistic (When statistic exists)
    @Test
    void createCommentStatistic_ShouldUpdateStatistic_WhenExists() {
        LocalDate today = LocalDate.now();
        CommentStatistic statistic = new CommentStatistic();
        statistic.setStatisticDate(today);
        statistic.setNumberOfCreatedComments(1L);
        statistic.setNumberOfDeletedComments(0L);

        when(commentStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);

        commentStatisticServiceImpl.createCommentStatistic();

        // Verify save method is called
        verify(commentStatisticRepository, times(1)).save(statistic);
        assertEquals(2L, statistic.getNumberOfCreatedComments()); // Check if the count of created comments has been updated
    }

    // Test for deleteCommentStatistic (When statistic doesn't exist)
    @Test
    void deleteCommentStatistic_ShouldCreateStatistic_WhenNotExists() {
        LocalDate today = LocalDate.now();
        CommentStatistic statistic = null; // No statistic for today

        when(commentStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);
        when(commentStatisticRepository.save(any(CommentStatistic.class))).thenReturn(new CommentStatistic());

        commentStatisticServiceImpl.deleteCommentStatistic();

        // Verify save method is called
        verify(commentStatisticRepository, times(1)).save(any(CommentStatistic.class));
    }

    // Test for deleteCommentStatistic (When statistic exists)
    @Test
    void deleteCommentStatistic_ShouldUpdateStatistic_WhenExists() {
        LocalDate today = LocalDate.now();
        CommentStatistic statistic = new CommentStatistic();
        statistic.setStatisticDate(today);
        statistic.setNumberOfCreatedComments(2L);
        statistic.setNumberOfDeletedComments(0L);

        when(commentStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);

        commentStatisticServiceImpl.deleteCommentStatistic();

        // Verify save method is called
        verify(commentStatisticRepository, times(1)).save(statistic);
        assertEquals(1L, statistic.getNumberOfCreatedComments()); // Check if the count of created comments has been updated
        assertEquals(1L, statistic.getNumberOfDeletedComments()); // Check if the count of deleted comments has been updated
    }

    // Test for showAllStatistics (Success)
    @Test
    void showAllStatistics_ShouldReturnAllStatistics_WhenSuccessful() {
        CommentStatistic statistic1 = new CommentStatistic();
        CommentStatistic statistic2 = new CommentStatistic();
        when(commentStatisticRepository.findAll()).thenReturn(Arrays.asList(statistic1, statistic2));

        var statistics = commentStatisticServiceImpl.showAllStatistics();

        assertEquals(2, statistics.size());
        verify(commentStatisticRepository, times(1)).findAll();
    }

    // Test for showAllStatistics (Empty result)
    @Test
    void showAllStatistics_ShouldReturnEmptyList_WhenNoStatistics() {
        when(commentStatisticRepository.findAll()).thenReturn(Arrays.asList());

        var statistics = commentStatisticServiceImpl.showAllStatistics();

        assertEquals(0, statistics.size());
        verify(commentStatisticRepository, times(1)).findAll();
    }

    // Test for showStatisticsByDate (Success)
    @Test
    void showStatisticsByDate_ShouldReturnStatistic_WhenFound() {
        LocalDate date = LocalDate.now();
        CommentStatistic statistic = new CommentStatistic();
        when(commentStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.of(statistic));

        CommentStatistic result = commentStatisticServiceImpl.showStatisticsByDate(date);

        assertNotNull(result);
        verify(commentStatisticRepository, times(1)).getByStatisticDate(date);
    }

    // Test for showStatisticsByDate (Not found)
    @Test
    void showStatisticsByDate_ShouldThrowStatisticNotFoundException_WhenNotFound() {
        LocalDate date = LocalDate.now();
        when(commentStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.empty());

        StatisticNotFoundException exception = assertThrows(StatisticNotFoundException.class, () -> {
            commentStatisticServiceImpl.showStatisticsByDate(date);
        });

        assertEquals("Statistic with date = " + date + " not found", exception.getMessage());
        verify(commentStatisticRepository, times(1)).getByStatisticDate(date);
    }

    // Test for deleteStatisticByDate (Success)
    @Test
    void deleteStatisticByDate_ShouldDeleteStatistic_WhenFound() {
        LocalDate date = LocalDate.now();
        CommentStatistic statistic = new CommentStatistic();
        when(commentStatisticRepository.findByStatisticDate(date)).thenReturn(statistic);

        commentStatisticServiceImpl.deleteStatisticByDate(date);

        verify(commentStatisticRepository, times(1)).delete(statistic);
    }

    // Test for deleteStatisticByDate (Statistic Not Found)
    @Test
    void deleteStatisticByDate_ShouldNotDelete_WhenNotFound() {
        LocalDate date = LocalDate.now();
        when(commentStatisticRepository.findByStatisticDate(date)).thenReturn(null);

        commentStatisticServiceImpl.deleteStatisticByDate(date);

        verify(commentStatisticRepository, never()).delete(any(CommentStatistic.class));
    }
}