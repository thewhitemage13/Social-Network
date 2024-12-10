package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.entity.CommentStatistic;
import org.thewhitemage13.service.CommentStatisticServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentStatisticControllerTest {
    @Mock
    private CommentStatisticServiceImpl commentStatisticServiceImpl;
    @InjectMocks
    private CommentStatisticController commentStatisticController;

    // Test for deleteByDate method (Success)
    @Test
    void deleteByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();

        // When the service call is successful
        doNothing().when(commentStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = commentStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted comment statistic", response.getBody());

        verify(commentStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for deleteByDate method (Error)
    @Test
    void deleteByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        doThrow(new RuntimeException("Error deleting statistic")).when(commentStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = commentStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error deleting statistic", response.getBody());

        verify(commentStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for showAllStatistics method (Success)
    @Test
    void showAllStatistics_ShouldReturnOk_WhenSuccessful() {
        CommentStatistic statistic1 = new CommentStatistic();
        CommentStatistic statistic2 = new CommentStatistic();
        List<CommentStatistic> statistics = Arrays.asList(statistic1, statistic2);

        // When the service call is successful
        when(commentStatisticServiceImpl.showAllStatistics()).thenReturn(statistics);

        ResponseEntity<List<CommentStatistic>> response = commentStatisticController.showAllStatistics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(commentStatisticServiceImpl, times(1)).showAllStatistics();
    }

    // Test for showAllStatistics method (Error)
    @Test
    void showAllStatistics_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        // Simulating an error in the service method
        when(commentStatisticServiceImpl.showAllStatistics()).thenThrow(new RuntimeException("Error fetching statistics"));

        ResponseEntity<List<CommentStatistic>> response = commentStatisticController.showAllStatistics();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(commentStatisticServiceImpl, times(1)).showAllStatistics();
    }

    // Test for showStatisticByDate method (Success)
    @Test
    void showStatisticByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();
        CommentStatistic statistic = new CommentStatistic();

        // When the service call is successful
        when(commentStatisticServiceImpl.showStatisticsByDate(date)).thenReturn(statistic);

        ResponseEntity<CommentStatistic> response = commentStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(commentStatisticServiceImpl, times(1)).showStatisticsByDate(date);
    }

    // Test for showStatisticByDate method (Error)
    @Test
    void showStatisticByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        when(commentStatisticServiceImpl.showStatisticsByDate(date)).thenThrow(new RuntimeException("Error fetching statistic"));

        ResponseEntity<CommentStatistic> response = commentStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(commentStatisticServiceImpl, times(1)).showStatisticsByDate(date);
    }
}