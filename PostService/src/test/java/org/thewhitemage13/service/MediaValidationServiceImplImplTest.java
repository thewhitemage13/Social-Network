package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.MediaClient;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.exceptions.MediaNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MediaValidationServiceImplImplTest {
    @Mock
    private MediaClient mediaClient;
    @InjectMocks
    private MediaValidationServiceImpl mediaValidationServiceImpl;

    @Test
    void testIsCreateMedia_Success() {
        // given
        String mediaUrl = "http://example.com/media.jpg";
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setMediaUrl(mediaUrl);

        // Mock the response from mediaClient to return true (media is found)
        when(mediaClient.mediaVerification(mediaUrl))
                .thenReturn(ResponseEntity.ok(true));

        // when
        // We don't expect an exception to be thrown
        assertDoesNotThrow(() -> mediaValidationServiceImpl.isCreateMedia(createPostDTO));

        // then
        // The method should complete without throwing an exception
    }

    @Test
    void testIsCreateMedia_MediaNotFound() {
        // given
        String mediaUrl = "http://example.com/media.jpg";
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setMediaUrl(mediaUrl);

        // Mock the response from mediaClient to return false (media not found)
        when(mediaClient.mediaVerification(mediaUrl))
                .thenReturn(ResponseEntity.ok(false));

        // when & then
        MediaNotFoundException thrown = assertThrows(MediaNotFoundException.class,
                () -> mediaValidationServiceImpl.isCreateMedia(createPostDTO));

        // Assert that the exception message is correct
        assertEquals("Media with url = " + mediaUrl + " not found", thrown.getMessage());
    }
}