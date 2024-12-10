package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.dao.SubscriptionDAO;
import org.thewhitemage13.dto.UserSubscriptionDTO;
import org.thewhitemage13.exceotion.SubscriptionNotFoundException;
import org.thewhitemage13.service.SubscriptionServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionControllerTest {
    @Mock
    private SubscriptionServiceImpl subscriptionServiceImpl;
    @InjectMocks
    private SubscriptionController subscriptionController;

    // get user followers

    @Test
    void getUserFollowers_Success() {
        Long userId = 1L;
        List<UserSubscriptionDTO> followers = Arrays.asList(new UserSubscriptionDTO(), new UserSubscriptionDTO());
        when(subscriptionServiceImpl.getFollowers(userId)).thenReturn(followers);

        ResponseEntity<List<UserSubscriptionDTO>> response = subscriptionController.getUserFollowers(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(followers, response.getBody());
        verify(subscriptionServiceImpl).getFollowers(userId);
    }

    @Test
    void getUserFollowing_Success() {
        Long userId = 1L;
        List<UserSubscriptionDTO> following = Arrays.asList(new UserSubscriptionDTO(), new UserSubscriptionDTO());
        when(subscriptionServiceImpl.getFollowing(userId)).thenReturn(following);

        ResponseEntity<List<UserSubscriptionDTO>> response = subscriptionController.getUserFollowing(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(following, response.getBody());
        verify(subscriptionServiceImpl).getFollowing(userId);
    }

    @Test
    void countFollowers_Success() {
        Long userId = 1L;
        Long followersCount = 42L;
        when(subscriptionServiceImpl.countFollowingByFollower(userId)).thenReturn(followersCount);

        ResponseEntity<Long> response = subscriptionController.countFollowers(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(followersCount, response.getBody());
        verify(subscriptionServiceImpl).countFollowingByFollower(userId);
    }

    @Test
    void countFollowing_Success() {
        Long userId = 1L;
        Long followingCount = 24L;
        when(subscriptionServiceImpl.countFollowersByFollowingId(userId)).thenReturn(followingCount);

        ResponseEntity<Long> response = subscriptionController.countFollowing(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(followingCount, response.getBody());
        verify(subscriptionServiceImpl).countFollowersByFollowingId(userId);
    }

    @Test
    void isUserFollowing_Success() {
        Long followerId = 1L;
        Long followingId = 2L;
        when(subscriptionServiceImpl.followingVerification(followerId, followingId)).thenReturn(true);

        ResponseEntity<Boolean> response = subscriptionController.isUserFollowing(followerId, followingId);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody());
        verify(subscriptionServiceImpl).followingVerification(followerId, followingId);
    }

    @Test
    void createSubscription_Success() {
        Long followerId = 1L;
        Long followingId = 2L;
        doNothing().when(subscriptionServiceImpl).createSubscription(followerId, followingId);

        ResponseEntity<String> response = subscriptionController.createSubscription(followerId, followingId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Subscription created", response.getBody());
        verify(subscriptionServiceImpl).createSubscription(followerId, followingId);
    }

    @Test
    void deleteSubscription_Success() {
        Long followerId = 1L;
        Long followingId = 2L;
        doNothing().when(subscriptionServiceImpl).deleteSubscription(followerId, followingId);

        ResponseEntity<String> response = subscriptionController.deleteSubscription(followerId, followingId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Subscription deleted", response.getBody());
        verify(subscriptionServiceImpl).deleteSubscription(followerId, followingId);
    }

    @Test
    void deleteSubscription_NotFound() {
        Long followerId = 1L;
        Long followingId = 2L;
        doThrow(new SubscriptionNotFoundException())
                .when(subscriptionServiceImpl).deleteSubscription(followerId, followingId);

        ResponseEntity<String> response = subscriptionController.deleteSubscription(followerId, followingId);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Subscription with followerId"));
        verify(subscriptionServiceImpl).deleteSubscription(followerId, followingId);
    }

    @Test
    void getAllFollowingByFollowingId_Success() {
        Long followingId = 1L;
        List<SubscriptionDAO> followingList = Arrays.asList(new SubscriptionDAO(), new SubscriptionDAO());
        when(subscriptionServiceImpl.getAllFollowingByFollowingId(followingId)).thenReturn(followingList);

        ResponseEntity<List<SubscriptionDAO>> response = subscriptionController.
                getAllFollowingByFollowingId(followingId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(followingList, response.getBody());
        verify(subscriptionServiceImpl).getAllFollowingByFollowingId(followingId);
    }

    @Test
    void getAllFollowingByFollowingId_InternalServerError() {
        Long followingId = 1L;
        when(subscriptionServiceImpl.getAllFollowingByFollowingId(followingId))
                .thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<List<SubscriptionDAO>> response = subscriptionController
                .getAllFollowingByFollowingId(followingId);

        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(subscriptionServiceImpl).getAllFollowingByFollowingId(followingId);
    }

    @Test
    void getAllFollowingByFollowerId_Success() {
        Long followerId = 2L;
        List<SubscriptionDAO> followerList = Arrays.asList(new SubscriptionDAO(), new SubscriptionDAO());
        when(subscriptionServiceImpl.getAllFollowingByFollowerId(followerId)).thenReturn(followerList);

        ResponseEntity<List<SubscriptionDAO>> response = subscriptionController.getAllFollowingByFollowerId(followerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(followerList, response.getBody());
        verify(subscriptionServiceImpl).getAllFollowingByFollowerId(followerId);
    }

    @Test
    void getAllFollowingByFollowerId_InternalServerError() {
        Long followerId = 2L;
        when(subscriptionServiceImpl.getAllFollowingByFollowerId(followerId))
                .thenThrow(new RuntimeException("Test exception"));

        ResponseEntity<List<SubscriptionDAO>> response = subscriptionController.getAllFollowingByFollowerId(followerId);

        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(subscriptionServiceImpl).getAllFollowingByFollowerId(followerId);
    }
}