package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.entity.LikeStatistic;
import org.thewhitemage13.service.LikeStatisticServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeStatisticControllerTest {
    @Mock
    private LikeStatisticServiceImpl likeStatisticServiceImpl;
    @InjectMocks
    private LikeStatisticController likeStatisticController;

    // Test for deleteByDate method (Success)
    @Test
    void deleteByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();

        // When the service call is successful
        doNothing().when(likeStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = likeStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted like statistic", response.getBody());

        verify(likeStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for deleteByDate method (Error)
    @Test
    void deleteByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        doThrow(new RuntimeException("Error deleting statistic")).when(likeStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = likeStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error deleting statistic", response.getBody());

        verify(likeStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for showAllStatistics method (Success)
    @Test
    void showAllStatistics_ShouldReturnOk_WhenSuccessful() {
        LikeStatistic statistic1 = new LikeStatistic();
        LikeStatistic statistic2 = new LikeStatistic();
        List<LikeStatistic> statistics = Arrays.asList(statistic1, statistic2);

        // When the service call is successful
        when(likeStatisticServiceImpl.getAllLikeStatistics()).thenReturn(statistics);

        ResponseEntity<List<LikeStatistic>> response = likeStatisticController.showAllStatistics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(likeStatisticServiceImpl, times(1)).getAllLikeStatistics();
    }

    // Test for showAllStatistics method (Error)
    @Test
    void showAllStatistics_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        // Simulating an error in the service method
        when(likeStatisticServiceImpl.getAllLikeStatistics()).thenThrow(new RuntimeException("Error fetching statistics"));

        ResponseEntity<List<LikeStatistic>> response = likeStatisticController.showAllStatistics();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(likeStatisticServiceImpl, times(1)).getAllLikeStatistics();
    }

    // Test for showStatisticByDate method (Success)
    @Test
    void showStatisticByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();
        LikeStatistic statistic = new LikeStatistic();

        // When the service call is successful
        when(likeStatisticServiceImpl.getLikeStatisticByDate(date)).thenReturn(statistic);

        ResponseEntity<LikeStatistic> response = likeStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(likeStatisticServiceImpl, times(1)).getLikeStatisticByDate(date);
    }

    // Test for showStatisticByDate method (Error)
    @Test
    void showStatisticByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        when(likeStatisticServiceImpl.getLikeStatisticByDate(date)).thenThrow(new RuntimeException("Error fetching statistic"));

        ResponseEntity<LikeStatistic> response = likeStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(likeStatisticServiceImpl, times(1)).getLikeStatisticByDate(date);
    }
}