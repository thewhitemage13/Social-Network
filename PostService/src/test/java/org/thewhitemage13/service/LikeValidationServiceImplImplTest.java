package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.LikeClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeValidationServiceImplImplTest {
    @Mock
    private LikeClient likeClient;
    @InjectMocks
    private LikeValidationServiceImpl likeValidationServiceImpl;

    @Test
    void testCountLikeValidation_Success() {
        // given
        Long postId = 1L;
        Long expectedLikeCount = 10L;
        // Mock the response from LikeClient
        when(likeClient.getPostLikeCount(postId))
                .thenReturn(ResponseEntity.ok(expectedLikeCount));

        // when
        Long actualLikeCount = likeValidationServiceImpl.countLikeValidation(postId);

        // then
        assertNotNull(actualLikeCount);
        assertEquals(expectedLikeCount, actualLikeCount);
    }

    @Test
    void testCountLikeValidation_ExceptionCaught() {
        // given
        Long postId = 1L;
        Long defaultLikeCount = 0L;
        // Mock the response to throw an exception
        when(likeClient.getPostLikeCount(postId))
                .thenThrow(new RuntimeException("Error while fetching likes"));

        // when
        Long actualLikeCount = likeValidationServiceImpl.countLikeValidation(postId);

        // then
        assertNotNull(actualLikeCount);
        assertEquals(defaultLikeCount, actualLikeCount);
    }
}