package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.dto.GetNotificationDTO;
import org.thewhitemage13.entity.Notification;
import org.thewhitemage13.exception.NotificationNotFoundException;
import org.thewhitemage13.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;
    @InjectMocks
    private NotificationService notificationService;

    @Test
    void createNotification_ShouldSaveNotification() {
        // given
        CreateNotificationDTO createNotificationDTO = new CreateNotificationDTO();
        createNotificationDTO.setType("INFO");
        createNotificationDTO.setMessage("Test message");
        createNotificationDTO.setUserId(1L);

        // Mock вызов репозитория
        when(notificationRepository.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        notificationService.createNotification(createNotificationDTO);

        // then
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void updateStatus_ShouldUpdateNotificationStatus() throws NotificationNotFoundException {
        // given
        Long notificationId = 1L;
        Notification existingNotification = new Notification();
        //existingNotification.setId(notificationId);
        existingNotification.setRead(false);

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(existingNotification));
        when(notificationRepository.save(any(Notification.class))).thenReturn(existingNotification);

        // when
        notificationService.updateStatus(notificationId, true);

        // then
        verify(notificationRepository, times(1)).findById(notificationId);
        verify(notificationRepository, times(1)).save(existingNotification);
        assertTrue(existingNotification.isRead());
    }

    @Test
    void updateStatus_ShouldThrowNotificationNotFoundException() {
        // given
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotificationNotFoundException.class, () -> notificationService.updateStatus(notificationId, true));
        verify(notificationRepository, times(1)).findById(notificationId);
    }

    @Test
    void getNotificationsByUserId_ShouldReturnListOfNotifications() throws NotificationNotFoundException {
        // given
        Long userId = 1L;
        Notification notification = new Notification();
        notification.setType("INFO");
        notification.setMessage("Test message");
        notification.setUserId(userId);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        when(notificationRepository.findAllByUserId(userId)).thenReturn(Optional.of(List.of(notification)));

        // when
        List<GetNotificationDTO> notifications = notificationService.getNotificationsByUserId(userId);

        // then
        assertNotNull(notifications);
        assertEquals(1, notifications.size());
        verify(notificationRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    void getNotificationsByUserId_ShouldThrowNotificationNotFoundException() {
        // given
        Long userId = 1L;
        when(notificationRepository.findAllByUserId(userId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotificationNotFoundException.class, () -> notificationService.getNotificationsByUserId(userId));
        verify(notificationRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    void getNotificationById_ShouldReturnNotification() throws NotificationNotFoundException {
        // given
        Long notificationId = 1L;
        Notification notification = new Notification();
        notification.setType("INFO");
        notification.setMessage("Test message");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        // when
        GetNotificationDTO dto = notificationService.getNotificationById(notificationId);

        // then
        assertNotNull(dto);
        assertEquals("INFO", dto.getType());
        verify(notificationRepository, times(1)).findById(notificationId);
    }

    @Test
    void getNotificationById_ShouldThrowNotificationNotFoundException() {
        // given
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotificationNotFoundException.class, () -> notificationService.getNotificationById(notificationId));
        verify(notificationRepository, times(1)).findById(notificationId);
    }

    @Test
    void deleteAllByUserId_ShouldCallRepositoryMethod() {
        // given
        Long userId = 1L;

        // when
        notificationService.deleteAllByUserId(userId);

        // then
        verify(notificationRepository, times(1)).deleteAllByUserId(userId);
    }

}