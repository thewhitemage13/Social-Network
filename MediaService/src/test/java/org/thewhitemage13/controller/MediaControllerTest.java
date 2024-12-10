package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.thewhitemage13.entity.Media;
import org.thewhitemage13.exceptions.MediaNotFoundException;
import org.thewhitemage13.service.MediaServiceImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MediaControllerTest {
    @Mock
    private MediaServiceImpl mediaServiceImpl;
    @InjectMocks
    private MediaController mediaController;

    @Test
    void mediaVerification_ReturnsTrue() throws Exception {
        // given
        String testUrl = "https://example.com/test";
        Mockito.doReturn(true).when(mediaServiceImpl).mediaVerification(testUrl);

        // when
        var response = mediaController.mediaVerification(testUrl);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(mediaServiceImpl).mediaVerification(testUrl);
    }

    @Test
    void mediaVerification_InternalServerError() {
        // given
        String testUrl = "https://example.com/test";
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(mediaServiceImpl).mediaVerification(testUrl);

        // when
        var response = mediaController.mediaVerification(testUrl);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    void uploadMedia_Success() throws Exception {
        // given
        Long userId = 1L;
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        String expectedUrl = "https://example.com/media/1";
        Mockito.doReturn(expectedUrl).when(mediaServiceImpl).uploadMedia(userId, mockFile);

        // when
        var response = mediaController.upload(userId, mockFile);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUrl, response.getBody());
        verify(mediaServiceImpl).uploadMedia(userId, mockFile);
    }

    @Test
    void uploadMedia_Failed() throws Exception {
        // given
        Long userId = 1L;
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        Mockito.doThrow(new IOException("Upload error")).when(mediaServiceImpl).uploadMedia(userId, mockFile);

        // when
        var response = mediaController.upload(userId, mockFile);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Upload error"));
    }

    @Test
    void deleteMedia_Success() throws Exception{
        // given
        Long mediaId = 1L;
        Mockito.doNothing().when(mediaServiceImpl).deleteMedia(mediaId);

        // when
        var response = mediaController.delete(mediaId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File deleted successfully.", response.getBody());
        verify(mediaServiceImpl).deleteMedia(mediaId);
    }

    @Test
    void deleteMedia_NotFound() throws Exception {
        // given
        Long mediaId = 1L;
        Mockito.doThrow(new MediaNotFoundException("Media not found")).when(mediaServiceImpl).deleteMedia(mediaId);

        // when
        var response = mediaController.delete(mediaId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Media with id = 1 not found.", response.getBody());
    }

    @Test
    void deleteMedia_InternalServerError() throws Exception {
        // given
        Long mediaId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(mediaServiceImpl).deleteMedia(mediaId);

        // when
        var response = mediaController.delete(mediaId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Unexpected error"));
    }

    @Test
    void getInformation_Success() throws Exception {
        // given
        Long mediaId = 1L;
        Media mockMedia = new Media();
        Mockito.doReturn(mockMedia).when(mediaServiceImpl).getMedia(mediaId);

        // when
        var response = mediaController.getInformation(mediaId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(mediaServiceImpl).getMedia(mediaId);
    }

    @Test
    void getInformation_NotFound() throws Exception {
        // given
        Long mediaId = 1L;
        Mockito.doThrow(new MediaNotFoundException("Media not found")).when(mediaServiceImpl).getMedia(mediaId);

        // when
        var response = mediaController.getInformation(mediaId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Media with id = 1 not found.", response.getBody());
    }

    @Test
    void getInformation_InternalServerError() throws Exception {
        // given
        Long mediaId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(mediaServiceImpl).getMedia(mediaId);

        // when
        var response = mediaController.getInformation(mediaId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Unexpected error"));
    }
}