package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.SubscriptionClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowerValidationServiceImplImplTest {
    @Mock
    private SubscriptionClient subscriptionClient;
    @InjectMocks
    private FollowerValidationServiceImpl followerValidationServiceImpl;


    @Test
    void countFollowersValidation_ValidResponse_ReturnsCorrectCount() {
        // Arrange
        Long userId = 1L;
        Long expectedFollowersCount = 100L;

        when(subscriptionClient.countFollowers(userId))
                .thenReturn(ResponseEntity.ok(expectedFollowersCount));

        // Act
        Long actualFollowersCount = followerValidationServiceImpl.countFollowersValidation(userId);

        // Assert
        assertEquals(expectedFollowersCount, actualFollowersCount);
        verify(subscriptionClient, times(1)).countFollowers(userId);
    }

    @Test
    void countFollowersValidation_Exception_ReturnsZero() {
        // Arrange
        Long userId = 1L;

        when(subscriptionClient.countFollowers(userId))
                .thenThrow(new RuntimeException("Service unavailable"));

        // Act
        Long actualFollowersCount = followerValidationServiceImpl.countFollowersValidation(userId);

        // Assert
        assertEquals(0L, actualFollowersCount);
        verify(subscriptionClient, times(1)).countFollowers(userId);
    }

    @Test
    void countFollowingValidation_ValidResponse_ReturnsCorrectCount() {
        // Arrange
        Long userId = 1L;
        Long expectedFollowingCount = 50L;

        when(subscriptionClient.countFollowing(userId))
                .thenReturn(ResponseEntity.ok(expectedFollowingCount));

        // Act
        Long actualFollowingCount = followerValidationServiceImpl.countFollowingValidation(userId);

        // Assert
        assertEquals(expectedFollowingCount, actualFollowingCount);
        verify(subscriptionClient, times(1)).countFollowing(userId);
    }

    @Test
    void countFollowingValidation_Exception_ReturnsZero() {
        // Arrange
        Long userId = 1L;

        when(subscriptionClient.countFollowing(userId))
                .thenThrow(new RuntimeException("Service unavailable"));

        // Act
        Long actualFollowingCount = followerValidationServiceImpl.countFollowingValidation(userId);

        // Assert
        assertEquals(0L, actualFollowingCount);
        verify(subscriptionClient, times(1)).countFollowing(userId);
    }
}