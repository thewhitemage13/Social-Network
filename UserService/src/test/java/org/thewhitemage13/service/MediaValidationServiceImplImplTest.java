package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.MediaClient;
import org.thewhitemage13.clients.PostClient;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.exceptions.MediaNotFoundException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MediaValidationServiceImplImplTest {
    @Mock
    private MediaClient mediaClient;
    @Mock
    private PostClient postClient;
    @InjectMocks
    private MediaValidationServiceImpl mediaValidationServiceImpl;

    @Test
    void validateMedia_ValidResponse_ReturnsMediaUrls() {
        // Arrange
        Long userId = 1L;
        List<String> expectedUrls = List.of("url1", "url2");

        when(postClient.getMediaUrlByUserId(userId))
                .thenReturn(ResponseEntity.ok(expectedUrls));

        // Act
        List<String> actualUrls = mediaValidationServiceImpl.validateMedia(userId);

        // Assert
        assertEquals(expectedUrls, actualUrls);
        verify(postClient, times(1)).getMediaUrlByUserId(userId);
    }

    @Test
    void validateMedia_Exception_ReturnsEmptyList() {
        // Arrange
        Long userId = 1L;

        when(postClient.getMediaUrlByUserId(userId))
                .thenThrow(new RuntimeException("Service unavailable"));

        // Act
        List<String> actualUrls = mediaValidationServiceImpl.validateMedia(userId);

        // Assert
        assertEquals(Collections.emptyList(), actualUrls);
        verify(postClient, times(1)).getMediaUrlByUserId(userId);
    }

    @Test
    void validatePicture_ValidPicture_NoException() {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setProfilePictureUrl("valid-url");

        when(mediaClient.mediaVerification("valid-url"))
                .thenReturn(ResponseEntity.ok(true));

        // Act & Assert
        assertDoesNotThrow(() -> mediaValidationServiceImpl.validatePicture(createUserDTO));
        verify(mediaClient, times(1)).mediaVerification("valid-url");
    }

    @Test
    void validatePicture_InvalidPicture_ThrowsMediaNotFoundException() {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setProfilePictureUrl("invalid-url");

        when(mediaClient.mediaVerification("invalid-url"))
                .thenReturn(ResponseEntity.ok(false));

        // Act & Assert
        MediaNotFoundException exception = assertThrows(
                MediaNotFoundException.class,
                () -> mediaValidationServiceImpl.validatePicture(createUserDTO)
        );

        assertEquals("The profile picture with url = invalid-url not found", exception.getMessage());
        verify(mediaClient, times(1)).mediaVerification("invalid-url");
    }

    @Test
    void validatePicture_NullPicture_NoException() {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setProfilePictureUrl(null);

        // Act & Assert
        assertDoesNotThrow(() -> mediaValidationServiceImpl.validatePicture(createUserDTO));
        verifyNoInteractions(mediaClient); // mediaClient не вызывается
    }
}