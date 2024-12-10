package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.PostClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostValidationServiceImplTest {
    @Mock
    private PostClient postClient;
    @InjectMocks
    private PostValidationServiceImpl postValidationService;

    @Test
    void countPostValidation_ValidResponse_ReturnsPostCount() {
        // Arrange
        Long userId = 1L;
        Long expectedCount = 5L;

        when(postClient.getPostCountByUserId(userId)).thenReturn(ResponseEntity.ok(expectedCount));

        // Act
        Long actualCount = postValidationService.countPostValidation(userId);

        // Assert
        assertEquals(expectedCount, actualCount);
        verify(postClient, times(1)).getPostCountByUserId(userId);
    }

    @Test
    void countPostValidation_ExceptionOccurs_ReturnsZero() {
        // Arrange
        Long userId = 1L;

        when(postClient.getPostCountByUserId(userId)).thenThrow(new RuntimeException("Service unavailable"));

        // Act
        Long actualCount = postValidationService.countPostValidation(userId);

        // Assert
        assertEquals(0L, actualCount);
        verify(postClient, times(1)).getPostCountByUserId(userId);
    }
}