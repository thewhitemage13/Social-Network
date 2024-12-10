package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.thewhitemage13.SubscriptionEvent;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dao.SubscriptionDAO;
import org.thewhitemage13.dto.UserSubscriptionDTO;
import org.thewhitemage13.entity.Subscription;
import org.thewhitemage13.exceotion.SubscriptionNotFoundException;
import org.thewhitemage13.processor.SubscriptionProcessorImpl;
import org.thewhitemage13.repository.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    @Mock
    private SubscriptionProcessorImpl subscriptionProcessorImpl;
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private ValidationServiceImpl validationServiceImpl;
    @Mock
    private KafkaTemplate<Long, Object> kafkaTemplate;
    @Mock
    private UserClient userClient;
    @InjectMocks
    private SubscriptionServiceImpl subscriptionServiceImpl;

    @Test
    void getAllFollowingByFollowerId() {
        Long followerId = 1L;
        List<Subscription> subscriptions = Arrays.asList(new Subscription(), new Subscription());
        List<SubscriptionDAO> subscriptionDAOs = Arrays.asList(new SubscriptionDAO(), new SubscriptionDAO());
        when(subscriptionRepository.findAllByFollowerId(followerId)).thenReturn(subscriptions);
        when(subscriptionProcessorImpl.getSubscriptionDAOS(subscriptions)).thenReturn(subscriptionDAOs);

        List<SubscriptionDAO> result = subscriptionServiceImpl.getAllFollowingByFollowerId(followerId);

        assertEquals(subscriptionDAOs, result);
        verify(subscriptionRepository).findAllByFollowerId(followerId);
        verify(subscriptionProcessorImpl).getSubscriptionDAOS(subscriptions);
    }

    @Test
    void getAllFollowingByFollowingId() {
        Long followingId = 2L;
        List<Subscription> subscriptions = Arrays.asList(new Subscription(), new Subscription());
        List<SubscriptionDAO> subscriptionDAOs = Arrays.asList(new SubscriptionDAO(), new SubscriptionDAO());
        when(subscriptionRepository.findAllByFollowingId(followingId)).thenReturn(subscriptions);
        when(subscriptionProcessorImpl.getSubscriptionDAOS(subscriptions)).thenReturn(subscriptionDAOs);

        List<SubscriptionDAO> result = subscriptionServiceImpl.getAllFollowingByFollowingId(followingId);

        assertEquals(subscriptionDAOs, result);
        verify(subscriptionRepository).findAllByFollowingId(followingId);
        verify(subscriptionProcessorImpl).getSubscriptionDAOS(subscriptions);
    }

    @Test
    void followingVerification() {
        Long followerId = 1L;
        Long followingId = 2L;
        when(subscriptionRepository.existsByFollowerIdAndFollowingId(followerId, followingId)).thenReturn(true);

        boolean result = subscriptionServiceImpl.followingVerification(followerId, followingId);

        assertTrue(result);
        verify(subscriptionRepository).existsByFollowerIdAndFollowingId(followerId, followingId);
    }

    @Test
    void countFollowersByFollowingId() {
        Long followingId = 2L;
        when(subscriptionRepository.countByFollowingId(followingId)).thenReturn(5L);

        Long result = subscriptionServiceImpl.countFollowersByFollowingId(followingId);

        assertEquals(5L, result);
        verify(subscriptionRepository).countByFollowingId(followingId);
    }

    @Test
    void countFollowingByFollower() {
        Long followerId = 1L;
        when(subscriptionRepository.countByFollowerId(followerId)).thenReturn(10L);

        Long result = subscriptionServiceImpl.countFollowingByFollower(followerId);

        assertEquals(10L, result);
        verify(subscriptionRepository).countByFollowerId(followerId);
    }

    @Test
    void deleteFollowersById() {
        Long followerId = 1L;
        doNothing().when(subscriptionRepository).deleteAllByFollowerId(followerId);

        subscriptionServiceImpl.deleteFollowersById(followerId);

        verify(subscriptionRepository).deleteAllByFollowerId(followerId);
    }

    @Test
    void deleteFollowingById() {
        Long followingId = 2L;
        doNothing().when(subscriptionRepository).deleteAllByFollowingId(followingId);

        subscriptionServiceImpl.deleteFollowingById(followingId);

        verify(subscriptionRepository).deleteAllByFollowingId(followingId);
    }

    @Test
    void getFollowers_withNullIds() {
        Long userId = 1L;
        List<Long> followerIds = Arrays.asList(null, null);  // Case with null IDs
        List<UserSubscriptionDTO> userSubscriptionDTOS = Arrays.asList(new UserSubscriptionDTO(), new UserSubscriptionDTO());

        // Mock repository response for null IDs
        when(subscriptionRepository.findAllByFollowingId(userId)).thenReturn(Arrays.asList(new Subscription(), new Subscription()));

        // Stubbing for null follower IDs
        when(userClient.getUsersByIds(followerIds)).thenReturn(userSubscriptionDTOS);

        // Call the service method
        List<UserSubscriptionDTO> result = subscriptionServiceImpl.getFollowers(userId);

        // Assertions
        assertEquals(userSubscriptionDTOS, result);
        verify(subscriptionRepository).findAllByFollowingId(userId);
        verify(userClient).getUsersByIds(followerIds);
    }
    @Test
    void createSubscription() {
        Long followerId = 1L;
        Long followingId = 2L;
        Subscription subscription = new Subscription();
        subscription.setFollowerId(followerId);
        subscription.setFollowingId(followingId);
        subscription.setCreatedAt(LocalDateTime.now());
        SubscriptionEvent event = new SubscriptionEvent();
        when(subscriptionProcessorImpl.getSubscriptionEvent(any())).thenReturn(event);
        doNothing().when(validationServiceImpl).validateSubscriptionVerification(followerId, followingId);
        doNothing().when(validationServiceImpl).validateUser(followerId);
        doNothing().when(validationServiceImpl).validateUser(followingId);

        subscriptionServiceImpl.createSubscription(followerId, followingId);

        verify(validationServiceImpl).validateSubscriptionVerification(followerId, followingId);
        verify(validationServiceImpl).validateUser(followerId);
        verify(validationServiceImpl).validateUser(followingId);
        verify(subscriptionRepository).save(any(Subscription.class));
        verify(kafkaTemplate).executeInTransaction(any());
    }

    @Test
    void deleteSubscription() {
        Long followerId = 1L;
        Long followingId = 2L;
        Subscription subscription = new Subscription();
        subscription.setSubscriptionId(3L);
        subscription.setFollowerId(followerId);
        subscription.setFollowingId(followingId);
        SubscriptionEvent event = new SubscriptionEvent();
        when(subscriptionRepository.findByFollowerIdAndFollowingId(followerId, followingId)).thenReturn(Optional.of(subscription));
        when(subscriptionProcessorImpl.getSubscriptionEvent(subscription)).thenReturn(event);

        subscriptionServiceImpl.deleteSubscription(followerId, followingId);

        verify(subscriptionRepository).findByFollowerIdAndFollowingId(followerId, followingId);
        verify(subscriptionRepository).delete(subscription);
        verify(kafkaTemplate).executeInTransaction(any());
    }

    @Test
    void deleteSubscription_NotFound() {
        Long followerId = 1L;
        Long followingId = 2L;
        when(subscriptionRepository.findByFollowerIdAndFollowingId(followerId, followingId)).thenReturn(Optional.empty());

        SubscriptionNotFoundException exception = assertThrows(SubscriptionNotFoundException.class,
                () -> subscriptionServiceImpl.deleteSubscription(followerId, followingId));

        assertTrue(exception.getMessage().contains("Subscription with followerId"));
        verify(subscriptionRepository).findByFollowerIdAndFollowingId(followerId, followingId);
        verify(subscriptionRepository, never()).delete(any());
    }
}