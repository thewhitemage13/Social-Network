package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.entity.PostStatistic;
import org.thewhitemage13.service.PostStatisticServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostStatisticControllerTest {
    @Mock
    private PostStatisticServiceImpl postStatisticServiceImpl;
    @InjectMocks
    private PostStatisticController postStatisticController;

    // Test for deleteByDate method (Success)
    @Test
    void deleteByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();

        // When the service call is successful
        doNothing().when(postStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = postStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted post statistic", response.getBody());

        verify(postStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for deleteByDate method (Error)
    @Test
    void deleteByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        doThrow(new RuntimeException("Error deleting statistic")).when(postStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = postStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error deleting statistic", response.getBody());

        verify(postStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for showAllStatistics method (Success)
    @Test
    void showAllStatistics_ShouldReturnOk_WhenSuccessful() {
        PostStatistic statistic1 = new PostStatistic();
        PostStatistic statistic2 = new PostStatistic();
        List<PostStatistic> statistics = Arrays.asList(statistic1, statistic2);

        // When the service call is successful
        when(postStatisticServiceImpl.getPostStatistics()).thenReturn(statistics);

        ResponseEntity<List<PostStatistic>> response = postStatisticController.showAllStatistics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(postStatisticServiceImpl, times(1)).getPostStatistics();
    }

    // Test for showAllStatistics method (Error)
    @Test
    void showAllStatistics_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        // Simulating an error in the service method
        when(postStatisticServiceImpl.getPostStatistics()).thenThrow(new RuntimeException("Error fetching statistics"));

        ResponseEntity<List<PostStatistic>> response = postStatisticController.showAllStatistics();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(postStatisticServiceImpl, times(1)).getPostStatistics();
    }

    // Test for showStatisticByDate method (Success)
    @Test
    void showStatisticByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();
        PostStatistic statistic = new PostStatistic();

        // When the service call is successful
        when(postStatisticServiceImpl.getStatisticByDate(date)).thenReturn(statistic);

        ResponseEntity<PostStatistic> response = postStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(postStatisticServiceImpl, times(1)).getStatisticByDate(date);
    }

    // Test for showStatisticByDate method (Error)
    @Test
    void showStatisticByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        when(postStatisticServiceImpl.getStatisticByDate(date)).thenThrow(new RuntimeException("Error fetching statistic"));

        ResponseEntity<PostStatistic> response = postStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(postStatisticServiceImpl, times(1)).getStatisticByDate(date);
    }
}