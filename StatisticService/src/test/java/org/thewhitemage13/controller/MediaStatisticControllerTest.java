package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.entity.MediaStatistic;
import org.thewhitemage13.service.MediaStatisticServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaStatisticControllerTest {
    @Mock
    private MediaStatisticServiceImpl mediaStatisticServiceImpl;
    @InjectMocks
    private MediaStatisticController mediaStatisticController;

    // Test for deleteByDate method (Success)
    @Test
    void deleteByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();

        // When the service call is successful
        doNothing().when(mediaStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = mediaStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted media statistic", response.getBody());

        verify(mediaStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for deleteByDate method (Error)
    @Test
    void deleteByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        doThrow(new RuntimeException("Error deleting statistic")).when(mediaStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = mediaStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error deleting statistic", response.getBody());

        verify(mediaStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for showAllStatistics method (Success)
    @Test
    void showAllStatistics_ShouldReturnOk_WhenSuccessful() {
        MediaStatistic statistic1 = new MediaStatistic();
        MediaStatistic statistic2 = new MediaStatistic();
        List<MediaStatistic> statistics = Arrays.asList(statistic1, statistic2);

        // When the service call is successful
        when(mediaStatisticServiceImpl.getAllMediaStatistics()).thenReturn(statistics);

        ResponseEntity<List<MediaStatistic>> response = mediaStatisticController.showAllStatistics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(mediaStatisticServiceImpl, times(1)).getAllMediaStatistics();
    }

    // Test for showAllStatistics method (Error)
    @Test
    void showAllStatistics_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        // Simulating an error in the service method
        when(mediaStatisticServiceImpl.getAllMediaStatistics()).thenThrow(new RuntimeException("Error fetching statistics"));

        ResponseEntity<List<MediaStatistic>> response = mediaStatisticController.showAllStatistics();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(mediaStatisticServiceImpl, times(1)).getAllMediaStatistics();
    }

    // Test for showStatisticByDate method (Success)
    @Test
    void showStatisticByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();
        MediaStatistic statistic = new MediaStatistic();

        // When the service call is successful
        when(mediaStatisticServiceImpl.getMediaStatisticByDate(date)).thenReturn(statistic);

        ResponseEntity<MediaStatistic> response = mediaStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(mediaStatisticServiceImpl, times(1)).getMediaStatisticByDate(date);
    }

    // Test for showStatisticByDate method (Error)
    @Test
    void showStatisticByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        when(mediaStatisticServiceImpl.getMediaStatisticByDate(date)).thenThrow(new RuntimeException("Error fetching statistic"));

        ResponseEntity<MediaStatistic> response = mediaStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(mediaStatisticServiceImpl, times(1)).getMediaStatisticByDate(date);
    }
}