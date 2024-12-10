package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.dto.CreatePostDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {
    @Mock
    private CommentValidationServiceImpl commentValidationServiceImpl;
    @Mock
    private LikeValidationServiceImpl likeValidationServiceImpl;
    @Mock
    private MediaValidationServiceImpl mediaValidationServiceImpl;
    @Mock
    private UserValidationServiceImpl userValidationServiceImpl;
    @InjectMocks
    private ValidationServiceImpl validationServiceImpl;

    @Test
    void testValidateComment() {
        // given
        Long postId = 1L;
        Long expectedCommentCount = 5L;

        // mock the behavior of commentValidationService
        when(commentValidationServiceImpl.countCommentValidation(postId)).thenReturn(expectedCommentCount);

        // when
        Long commentCount = validationServiceImpl.validateComment(postId);

        // then
        assertEquals(expectedCommentCount, commentCount);
        verify(commentValidationServiceImpl, times(1)).countCommentValidation(postId);  // Verify the method was called once
    }

    @Test
    void testValidateLike() {
        // given
        Long postId = 1L;
        Long expectedLikeCount = 10L;

        // mock the behavior of likeValidationService
        when(likeValidationServiceImpl.countLikeValidation(postId)).thenReturn(expectedLikeCount);

        // when
        Long likeCount = validationServiceImpl.validateLike(postId);

        // then
        assertEquals(expectedLikeCount, likeCount);
        verify(likeValidationServiceImpl, times(1)).countLikeValidation(postId);  // Verify the method was called once
    }

    @Test
    void testValidateMedia() {
        // given
        CreatePostDTO createPostDTO = new CreatePostDTO(1L, "content", "http://media.url");

        // mock the behavior of mediaValidationService
        doNothing().when(mediaValidationServiceImpl).isCreateMedia(createPostDTO);

        // when
        validationServiceImpl.validateMedia(createPostDTO);

        // then
        verify(mediaValidationServiceImpl, times(1)).isCreateMedia(createPostDTO);  // Verify the method was called once
    }

    @Test
    void testValidateUser() {
        // given
        CreatePostDTO createPostDTO = new CreatePostDTO(1L, "content", "http://media.url");

        // mock the behavior of userValidationService
        doNothing().when(userValidationServiceImpl).validateUser(createPostDTO);

        // when
        validationServiceImpl.validateUser(createPostDTO);

        // then
        verify(userValidationServiceImpl, times(1)).validateUser(createPostDTO);  // Verify the method was called once
    }
}