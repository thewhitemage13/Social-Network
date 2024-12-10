package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.repository.SubscriptionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {
    @Mock
    private UserClient userClient;
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @InjectMocks
    private ValidationServiceImpl validationServiceImpl;

    @Test
    void testValidateUser_UserNotFound() {
        Long userId = 1L;

        // Mock userClient to return a "false" response indicating user does not exist
        when(userClient.verifyUserExistence(userId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(false));

        // Assert that UserNotFoundException is thrown
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            validationServiceImpl.validateUser(userId);
        });

        assertEquals("User with id = 1 not found", exception.getMessage());
    }

    @Test
    void testValidateUser_UserFound() {
        Long userId = 1L;

        // Mock userClient to return "true" response indicating user exists
        when(userClient.verifyUserExistence(userId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(true));

        // No exception should be thrown
        assertDoesNotThrow(() -> validationServiceImpl.validateUser(userId));
    }

    @Test
    void testValidateSubscriptionVerification_FollowerAndFollowingIdsMatch() {
        Long followerId = 1L;
        Long followingId = 1L;

        // Assert that an exception is thrown if the IDs match
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            validationServiceImpl.validateSubscriptionVerification(followerId, followingId);
        });

        assertEquals("The id's don't have to match", exception.getMessage());
    }

    @Test
    void testValidateSubscriptionVerification_AlreadySubscribed() {
        Long followerId = 1L;
        Long followingId = 2L;

        // Mock subscriptionRepository to return true indicating the user is already subscribed
        when(subscriptionRepository.existsByFollowerIdAndFollowingId(followerId, followingId)).thenReturn(true);

        // Assert that an exception is thrown if already subscribed
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            validationServiceImpl.validateSubscriptionVerification(followerId, followingId);
        });

        assertEquals("You are already subscribed to this user", exception.getMessage());
    }

    @Test
    void testValidateSubscriptionVerification_Success() {
        Long followerId = 1L;
        Long followingId = 2L;

        // Mock subscriptionRepository to return false, meaning the user is not subscribed
        when(subscriptionRepository.existsByFollowerIdAndFollowingId(followerId, followingId)).thenReturn(false);

        // No exception should be thrown
        assertDoesNotThrow(() -> validationServiceImpl.validateSubscriptionVerification(followerId, followingId));
    }
}