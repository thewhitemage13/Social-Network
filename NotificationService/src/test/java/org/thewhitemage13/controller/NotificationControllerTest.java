package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.thewhitemage13.dto.CreateNotificationDTO;
import org.thewhitemage13.dto.GetNotificationDTO;
import org.thewhitemage13.exception.NotificationNotFoundException;
import org.thewhitemage13.service.NotificationService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private NotificationController notificationController;

    @Test
    void handleCreateNotification_ReturnsValidResponseEntity() throws Exception {
        // given
        CreateNotificationDTO notificationDTO = new CreateNotificationDTO();
        notificationDTO.setMessage("Test Notification");
        notificationDTO.setUserId(1L);

        Mockito.doNothing().when(notificationService).createNotification(notificationDTO);

        // when
        var response = notificationController.createNotification(notificationDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification created", response.getBody());
        verify(notificationService).createNotification(notificationDTO);
    }

    @Test
    void handleCreateNotification_InternalServerError() throws Exception {
        // given
        CreateNotificationDTO notificationDTO = new CreateNotificationDTO();
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(notificationService).createNotification(notificationDTO);

        // when
        var response = notificationController.createNotification(notificationDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }

    @Test
    void handleUpdateNotification_ReturnsValidResponseEntity() throws Exception {
        // given
        Long notificationId = 1L;
        boolean status = true;

        Mockito.doNothing().when(notificationService).updateStatus(notificationId, status);

        // when
        var response = notificationController.updateNotification(notificationId, status);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification updated", response.getBody());
        verify(notificationService).updateStatus(notificationId, status);
    }

    @Test
    void handleUpdateNotification_NotificationNotFound() throws Exception {
        // given
        Long notificationId = 1L;
        boolean status = true;

        Mockito.doThrow(new NotificationNotFoundException("Notification with id = 1 not found")).when(notificationService).updateStatus(notificationId, status);

        // when
        var response = notificationController.updateNotification(notificationId, status);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Notification with id = 1 not found", response.getBody());
    }

    @Test
    void handleUpdateNotification_InternalServerError() throws Exception {
        // given
        Long notificationId = 1L;
        boolean status = true;

        Mockito.doThrow(new RuntimeException("Unexpected error")).when(notificationService).updateStatus(notificationId, status);

        // when
        var response = notificationController.updateNotification(notificationId, status);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }

    @Test
    void handleGetNotificationByUserId_ReturnsValidResponseEntity() throws Exception {
        // given
        Long userId = 1L;
        GetNotificationDTO notificationDTO = new GetNotificationDTO();
        notificationDTO.setMessage("Test Notification");
        //notificationDTO.setUserId(userId);

        Mockito.doReturn(notificationDTO).when(notificationService).getNotificationById(userId);

        // when
        var response = notificationController.getNotificationByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificationDTO, response.getBody());
    }

    @Test
    void handleGetNotificationByUserId_NotificationNotFound() throws Exception {
        // given
        Long userId = 1L;
        Mockito.doThrow(new NotificationNotFoundException("Notification not found")).when(notificationService).getNotificationById(userId);

        // when
        var response = notificationController.getNotificationByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void handleGetNotificationByUserId_InternalServerError() throws Exception {
        // given
        Long userId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(notificationService).getNotificationById(userId);

        // when
        var response = notificationController.getNotificationByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void handleGetNotificationsByUserId_ReturnsValidResponseEntity() throws Exception {
        // given
        Long userId = 1L;
        GetNotificationDTO notification1 = new GetNotificationDTO();
        notification1.setMessage("Notification 1");
        //notification1.setUserId(userId);

        GetNotificationDTO notification2 = new GetNotificationDTO();
        notification2.setMessage("Notification 2");
        //notification2.setUserId(userId);

        List<GetNotificationDTO> notifications = List.of(notification1, notification2);

        Mockito.doReturn(notifications).when(notificationService).getNotificationsByUserId(userId);

        // when
        var response = notificationController.getNotificationsByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notifications, response.getBody());
    }

    @Test
    void handleGetNotificationsByUserId_NotificationNotFound()  throws Exception {
        // given
        Long userId = 1L;
        Mockito.doThrow(new NotificationNotFoundException("No notifications found")).when(notificationService).getNotificationsByUserId(userId);

        // when
        var response = notificationController.getNotificationsByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void handleGetNotificationsByUserId_InternalServerError() throws Exception {
        // given
        Long userId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(notificationService).getNotificationsByUserId(userId);

        // when
        var response = notificationController.getNotificationsByUserId(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}