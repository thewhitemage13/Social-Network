package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.CommentClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentValidationServiceImplImplTest {
    @Mock
    private CommentClient commentClient;
    @InjectMocks
    private CommentValidationServiceImpl commentValidationServiceImpl;

    @Test
    void testCountCommentValidation_Success() {
        // given
        Long postId = 1L;
        Long expectedCommentCount = 5L;
        // Mock the response from CommentClient
        when(commentClient.getCommentCountByPostId(postId))
                .thenReturn(ResponseEntity.ok(expectedCommentCount));

        // when
        Long actualCommentCount = commentValidationServiceImpl.countCommentValidation(postId);

        // then
        assertNotNull(actualCommentCount);
        assertEquals(expectedCommentCount, actualCommentCount);
    }

    @Test
    void testCountCommentValidation_ExceptionCaught() {
        // given
        Long postId = 1L;
        Long defaultCommentCount = 0L;
        // Mock the response to throw an exception
        when(commentClient.getCommentCountByPostId(postId))
                .thenThrow(new RuntimeException("Error while fetching comments"));

        // when
        Long actualCommentCount = commentValidationServiceImpl.countCommentValidation(postId);

        // then
        assertNotNull(actualCommentCount);
        assertEquals(defaultCommentCount, actualCommentCount);
    }
}