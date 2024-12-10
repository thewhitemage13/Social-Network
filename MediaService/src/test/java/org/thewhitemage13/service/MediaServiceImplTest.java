package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.thewhitemage13.entity.Media;
import org.thewhitemage13.exception.MediaNotFoundException;
import org.thewhitemage13.processor.MediaProcessorImpl;
import org.thewhitemage13.repository.MediaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class MediaServiceImplTest {
    @Mock
    private MediaRepository mediaRepository;
    @Mock
    private ValidationServiceImpl validationServiceImpl;
    @Mock
    private KafkaTemplate<Long, Object> kafkaTemplate;
    @Mock
    private MediaProcessorImpl mediaProcessorImpl;
    @InjectMocks
    private MediaServiceImpl mediaServiceImpl;

    @Test
    void mediaVerification_ShouldReturnTrue_WhenMediaExists() {
        // given
        String url = "https://example.com/media.jpg";
        Mockito.when(mediaRepository.existsByUrl(url)).thenReturn(true);

        // when
        boolean result = mediaServiceImpl.mediaVerification(url);

        // then
        assertTrue(result);
        verify(mediaRepository).existsByUrl(url);
    }

    @Test
    void mediaVerification_ShouldReturnFalse_WhenMediaDoesNotExist() {
        // given
        String url = "https://example.com/media.jpg";
        Mockito.when(mediaRepository.existsByUrl(url)).thenReturn(false);

        // when
        boolean result = mediaServiceImpl.mediaVerification(url);

        // then
        assertFalse(result);
        verify(mediaRepository).existsByUrl(url);
    }

    @Test
    void deleteMedia_ShouldDeleteSuccessfully() throws Exception {
        // given
        Long mediaId = 1L;
        Media media = new Media();
        media.setMediaId(mediaId);
        media.setFileName("test.jpg");
        media.setUrl("https://s3.amazonaws.com/media/test.jpg");
        Mockito.when(mediaRepository.findById(mediaId)).thenReturn(Optional.of(media));

        // when
        mediaServiceImpl.deleteMedia(mediaId);

        // then
        verify(mediaProcessorImpl).deleteFileFromS3("media/test.jpg");
        verify(mediaRepository).deleteById(mediaId);
        verify(kafkaTemplate).executeInTransaction(Mockito.any());
    }

    @Test
    void deleteMedia_ShouldThrowException_WhenMediaNotFound() {
        // given
        Long mediaId = 1L;
        Mockito.when(mediaRepository.findById(mediaId)).thenReturn(Optional.empty());

        // then
        assertThrows(MediaNotFoundException.class, () -> mediaServiceImpl.deleteMedia(mediaId));
        verify(mediaRepository).findById(mediaId);
        verifyNoInteractions(mediaProcessorImpl);
        verifyNoInteractions(kafkaTemplate);
    }

    @Test
    void getMedia_ShouldReturnMedia_WhenMediaExists() throws Exception {
        // given
        Long mediaId = 1L;
        Media media = new Media();
        media.setMediaId(mediaId);
        Mockito.when(mediaRepository.findById(mediaId)).thenReturn(Optional.of(media));

        // when
        Media result = mediaServiceImpl.getMedia(mediaId);

        // then
        assertEquals(media, result);
        verify(mediaRepository).findById(mediaId);
    }

    @Test
    void getMedia_ShouldThrowException_WhenMediaNotFound() {
        // given
        Long mediaId = 1L;
        Mockito.when(mediaRepository.findById(mediaId)).thenReturn(Optional.empty());

        // then
        assertThrows(MediaNotFoundException.class, () -> mediaServiceImpl.getMedia(mediaId));
        verify(mediaRepository).findById(mediaId);
    }
}