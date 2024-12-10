package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.entity.LikeStatistic;
import org.thewhitemage13.exception.StatisticNotFoundException;
import org.thewhitemage13.repository.LikeStatisticRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeStatisticServiceImplTest {
    @Mock
    private LikeStatisticRepository likeStatisticRepository;
    @InjectMocks
    private LikeStatisticServiceImpl likeStatisticServiceImpl;

    // Test for createLikePostStatistic (When statistic doesn't exist)
    @Test
    void createLikePostStatistic_ShouldCreateNewStatistic_WhenNotExists() {
        LocalDate today = LocalDate.now();
        LikeStatistic statistic = null; // No statistic for today

        when(likeStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);
        when(likeStatisticRepository.save(any(LikeStatistic.class))).thenReturn(new LikeStatistic());

        likeStatisticServiceImpl.createLikePostStatistic();

        // Verify save method is called
        verify(likeStatisticRepository, times(1)).save(any(LikeStatistic.class));
    }

    // Test for createLikePostStatistic (When statistic exists)
    @Test
    void createLikePostStatistic_ShouldUpdateStatistic_WhenExists() {
        LocalDate today = LocalDate.now();
        LikeStatistic statistic = new LikeStatistic();
        statistic.setStatisticDate(today);
        statistic.setPostLike(1L);
        statistic.setCommentLike(0L);
        statistic.setRemovePostLike(0L);
        statistic.setRemoveCommentLike(0L);

        when(likeStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);

        likeStatisticServiceImpl.createLikePostStatistic();

        // Verify save method is called
        verify(likeStatisticRepository, times(1)).save(statistic);
        assertEquals(2L, statistic.getPostLike()); // Check if the post like count is incremented
    }

    // Test for deleteLikePostStatistic (When statistic doesn't exist)
    @Test
    void deleteLikePostStatistic_ShouldCreateStatistic_WhenNotExists() {
        LocalDate today = LocalDate.now();
        LikeStatistic statistic = null; // No statistic for today

        when(likeStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);
        when(likeStatisticRepository.save(any(LikeStatistic.class))).thenReturn(new LikeStatistic());

        likeStatisticServiceImpl.deleteLikePostStatistic();

        // Verify save method is called
        verify(likeStatisticRepository, times(1)).save(any(LikeStatistic.class));
    }

    // Test for deleteLikePostStatistic (When statistic exists)
    @Test
    void deleteLikePostStatistic_ShouldUpdateStatistic_WhenExists() {
        LocalDate today = LocalDate.now();
        LikeStatistic statistic = new LikeStatistic();
        statistic.setStatisticDate(today);
        statistic.setPostLike(2L);
        statistic.setCommentLike(0L);
        statistic.setRemovePostLike(0L);
        statistic.setRemoveCommentLike(0L);

        when(likeStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);

        likeStatisticServiceImpl.deleteLikePostStatistic();

        // Verify save method is called
        verify(likeStatisticRepository, times(1)).save(statistic);
        assertEquals(1L, statistic.getPostLike()); // Check if the post like count is decremented
        assertEquals(1L, statistic.getRemovePostLike()); // Check if the removed post like count is incremented
    }

    // Test for createLikeCommentStatistic (When statistic doesn't exist)
    @Test
    void createLikeCommentStatistic_ShouldCreateNewStatistic_WhenNotExists() {
        LocalDate today = LocalDate.now();
        LikeStatistic statistic = null; // No statistic for today

        when(likeStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);
        when(likeStatisticRepository.save(any(LikeStatistic.class))).thenReturn(new LikeStatistic());

        likeStatisticServiceImpl.createLikeCommentStatistic();

        // Verify save method is called
        verify(likeStatisticRepository, times(1)).save(any(LikeStatistic.class));
    }

    // Test for createLikeCommentStatistic (When statistic exists)
    @Test
    void createLikeCommentStatistic_ShouldUpdateStatistic_WhenExists() {
        LocalDate today = LocalDate.now();
        LikeStatistic statistic = new LikeStatistic();
        statistic.setStatisticDate(today);
        statistic.setPostLike(0L);
        statistic.setCommentLike(1L);
        statistic.setRemovePostLike(0L);
        statistic.setRemoveCommentLike(0L);

        when(likeStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);

        likeStatisticServiceImpl.createLikeCommentStatistic();

        // Verify save method is called
        verify(likeStatisticRepository, times(1)).save(statistic);
        assertEquals(2L, statistic.getCommentLike()); // Check if the comment like count is incremented
    }

    // Test for deleteLikeCommentStatistic (When statistic doesn't exist)
    @Test
    void deleteLikeCommentStatistic_ShouldCreateStatistic_WhenNotExists() {
        LocalDate today = LocalDate.now();
        LikeStatistic statistic = null; // No statistic for today

        when(likeStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);
        when(likeStatisticRepository.save(any(LikeStatistic.class))).thenReturn(new LikeStatistic());

        likeStatisticServiceImpl.deleteLikeCommentStatistic();

        // Verify save method is called
        verify(likeStatisticRepository, times(1)).save(any(LikeStatistic.class));
    }

    // Test for deleteLikeCommentStatistic (When statistic exists)
    @Test
    void deleteLikeCommentStatistic_ShouldUpdateStatistic_WhenExists() {
        LocalDate today = LocalDate.now();
        LikeStatistic statistic = new LikeStatistic();
        statistic.setStatisticDate(today);
        statistic.setPostLike(0L);
        statistic.setCommentLike(2L);
        statistic.setRemovePostLike(0L);
        statistic.setRemoveCommentLike(0L);

        when(likeStatisticRepository.findByStatisticDate(today)).thenReturn(statistic);

        likeStatisticServiceImpl.deleteLikeCommentStatistic();

        // Verify save method is called
        verify(likeStatisticRepository, times(1)).save(statistic);
        assertEquals(1L, statistic.getCommentLike()); // Check if the comment like count is decremented
        assertEquals(1L, statistic.getRemoveCommentLike()); // Check if the removed comment like count is incremented
    }

    // Test for getAllLikeStatistics (Success)
    @Test
    void getAllLikeStatistics_ShouldReturnAllStatistics_WhenSuccessful() {
        LikeStatistic statistic1 = new LikeStatistic();
        LikeStatistic statistic2 = new LikeStatistic();
        when(likeStatisticRepository.findAll()).thenReturn(Arrays.asList(statistic1, statistic2));

        var statistics = likeStatisticServiceImpl.getAllLikeStatistics();

        assertEquals(2, statistics.size());
        verify(likeStatisticRepository, times(1)).findAll();
    }

    // Test for getAllLikeStatistics (Empty result)
    @Test
    void getAllLikeStatistics_ShouldReturnEmptyList_WhenNoStatistics() {
        when(likeStatisticRepository.findAll()).thenReturn(Arrays.asList());

        var statistics = likeStatisticServiceImpl.getAllLikeStatistics();

        assertEquals(0, statistics.size());
        verify(likeStatisticRepository, times(1)).findAll();
    }

    // Test for getLikeStatisticByDate (Success)
    @Test
    void getLikeStatisticByDate_ShouldReturnStatistic_WhenFound() {
        LocalDate date = LocalDate.now();
        LikeStatistic statistic = new LikeStatistic();
        when(likeStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.of(statistic));

        LikeStatistic result = likeStatisticServiceImpl.getLikeStatisticByDate(date);

        assertNotNull(result);
        verify(likeStatisticRepository, times(1)).getByStatisticDate(date);
    }

    // Test for getLikeStatisticByDate (Not found)
    @Test
    void getLikeStatisticByDate_ShouldThrowStatisticNotFoundException_WhenNotFound() {
        LocalDate date = LocalDate.now();
        when(likeStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.empty());

        StatisticNotFoundException exception = assertThrows(StatisticNotFoundException.class, () -> {
            likeStatisticServiceImpl.getLikeStatisticByDate(date);
        });

        assertEquals("Statistic with date = " + date + " not found", exception.getMessage());
        verify(likeStatisticRepository, times(1)).getByStatisticDate(date);
    }

    // Test for deleteStatisticByDate (Success)
    @Test
    void deleteStatisticByDate_ShouldDeleteStatistic_WhenFound() {
        LocalDate date = LocalDate.now();
        LikeStatistic statistic = new LikeStatistic();
        when(likeStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.of(statistic));

        likeStatisticServiceImpl.deleteStatisticByDate(date);

        verify(likeStatisticRepository, times(1)).delete(statistic);
    }

    // Test for deleteStatisticByDate (Statistic Not Found)
    @Test
    void deleteStatisticByDate_ShouldNotDelete_WhenNotFound() {
        LocalDate date = LocalDate.now();
        when(likeStatisticRepository.getByStatisticDate(date)).thenReturn(Optional.empty());

        // Verify that the exception is thrown and the delete method is not called
        StatisticNotFoundException exception = assertThrows(StatisticNotFoundException.class, () -> {
            likeStatisticServiceImpl.deleteStatisticByDate(date);
        });

        assertEquals("Statistic with date = " + date + " not found", exception.getMessage());
        verify(likeStatisticRepository, never()).delete(any(LikeStatistic.class));
    }
}