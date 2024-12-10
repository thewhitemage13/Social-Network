package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.entity.UserStatistic;
import org.thewhitemage13.service.UserStatisticServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserStatisticControllerTest {
    @Mock
    private UserStatisticServiceImpl userStatisticServiceImpl;
    @InjectMocks
    private UserStatisticController userStatisticController;

    @Test
    void deleteByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();

        // When the service call is successful
        doNothing().when(userStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = userStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted user statistic", response.getBody());

        verify(userStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for deleteByDate method (Error)
    @Test
    void deleteByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        doThrow(new RuntimeException("Error deleting statistic")).when(userStatisticServiceImpl).deleteStatisticByDate(date);

        ResponseEntity<String> response = userStatisticController.deleteByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error deleting statistic", response.getBody());

        verify(userStatisticServiceImpl, times(1)).deleteStatisticByDate(date);
    }

    // Test for showAllStatistics method (Success)
    @Test
    void showAllStatistics_ShouldReturnOk_WhenSuccessful() {
        UserStatistic statistic1 = new UserStatistic();
        UserStatistic statistic2 = new UserStatistic();
        List<UserStatistic> statistics = Arrays.asList(statistic1, statistic2);

        // When the service call is successful
        when(userStatisticServiceImpl.getUserStatistics()).thenReturn(statistics);

        ResponseEntity<List<UserStatistic>> response = userStatisticController.showAllStatistics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(userStatisticServiceImpl, times(1)).getUserStatistics();
    }

    // Test for showAllStatistics method (Error)
    @Test
    void showAllStatistics_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        // Simulating an error in the service method
        when(userStatisticServiceImpl.getUserStatistics()).thenThrow(new RuntimeException("Error fetching statistics"));

        ResponseEntity<List<UserStatistic>> response = userStatisticController.showAllStatistics();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(userStatisticServiceImpl, times(1)).getUserStatistics();
    }

    // Test for showStatisticByDate method (Success)
    @Test
    void showStatisticByDate_ShouldReturnOk_WhenSuccessful() {
        LocalDate date = LocalDate.now();
        UserStatistic statistic = new UserStatistic();

        // When the service call is successful
        when(userStatisticServiceImpl.getUserStatisticByDate(date)).thenReturn(statistic);

        ResponseEntity<UserStatistic> response = userStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(userStatisticServiceImpl, times(1)).getUserStatisticByDate(date);
    }

    // Test for showStatisticByDate method (Error)
    @Test
    void showStatisticByDate_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        LocalDate date = LocalDate.now();

        // Simulating an error in the service method
        when(userStatisticServiceImpl.getUserStatisticByDate(date)).thenThrow(new RuntimeException("Error fetching statistic"));

        ResponseEntity<UserStatistic> response = userStatisticController.showStatisticByDate(date);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(userStatisticServiceImpl, times(1)).getUserStatisticByDate(date);
    }
}