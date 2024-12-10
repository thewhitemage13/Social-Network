package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.CommentClient;
import org.thewhitemage13.clients.PostClient;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dto.CreateLikeComment;
import org.thewhitemage13.dto.CreateLikePost;
import org.thewhitemage13.exception.CommentNotFoundException;
import org.thewhitemage13.exception.PostNotFoundException;
import org.thewhitemage13.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {
    @Mock
    private CommentClient commentClient;
    @Mock
    private PostClient postClient;
    @Mock
    private UserClient userClient;
    @InjectMocks
    private ValidationServiceImpl validationServiceImpl;

    @Test
    void validatePostLike_ValidPostAndUser_NoExceptionThrown() {
        // given
        CreateLikePost createLikePost = new CreateLikePost(1L, 1L);
        when(postClient.postVerification(1L)).thenReturn(ResponseEntity.ok(true));
        when(userClient.verifyUserExistence(1L)).thenReturn(ResponseEntity.ok(true));

        // when & then
        assertDoesNotThrow(() -> validationServiceImpl.validatePostLike(createLikePost));
        verify(postClient).postVerification(1L);
        verify(userClient).verifyUserExistence(1L);
    }

    @Test
    void validatePostLike_PostNotFound_ThrowsPostNotFoundException() {
        // given
        CreateLikePost createLikePost = new CreateLikePost(1L, 1L);
        when(postClient.postVerification(1L)).thenReturn(ResponseEntity.ok(false));

        // when & then
        PostNotFoundException exception = assertThrows(PostNotFoundException.class,
                () -> validationServiceImpl.validatePostLike(createLikePost));
        assertEquals("Post with id = 1 not found", exception.getMessage());
    }

    @Test
    void validatePostLike_UserNotFound_ThrowsUserNotFoundException() {
        // given
        CreateLikePost createLikePost = new CreateLikePost(1L, 1L);
        when(postClient.postVerification(1L)).thenReturn(ResponseEntity.ok(true));
        when(userClient.verifyUserExistence(1L)).thenReturn(ResponseEntity.ok(false));

        // when & then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> validationServiceImpl.validatePostLike(createLikePost));
        assertEquals("User with id = 1 not found", exception.getMessage());
    }

    @Test
    void validateCommentLike_ValidCommentAndUser_NoExceptionThrown() {
        // given
        CreateLikeComment createLikeComment = new CreateLikeComment(1L, 1L);
        when(commentClient.commentVerification(1L)).thenReturn(ResponseEntity.ok(true));
        when(userClient.verifyUserExistence(1L)).thenReturn(ResponseEntity.ok(true));

        // when & then
        assertDoesNotThrow(() -> validationServiceImpl.validateCommentLike(createLikeComment));
        verify(commentClient).commentVerification(1L);
        verify(userClient).verifyUserExistence(1L);
    }

    @Test
    void validateCommentLike_CommentNotFound_ThrowsCommentNotFoundException() {
        // given
        CreateLikeComment createLikeComment = new CreateLikeComment(1L, 1L);
        when(commentClient.commentVerification(1L)).thenReturn(ResponseEntity.ok(false));

        // when & then
        CommentNotFoundException exception = assertThrows(CommentNotFoundException.class,
                () -> validationServiceImpl.validateCommentLike(createLikeComment));
        assertEquals("Comment with id = 1 not found", exception.getMessage());
    }

    @Test
    void validateCommentLike_UserNotFound_ThrowsUserNotFoundException() {
        // given
        CreateLikeComment createLikeComment = new CreateLikeComment(1L, 1L);
        when(commentClient.commentVerification(1L)).thenReturn(ResponseEntity.ok(true));
        when(userClient.verifyUserExistence(1L)).thenReturn(ResponseEntity.ok(false));

        // when & then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> validationServiceImpl.validateCommentLike(createLikeComment));
        assertEquals("User with id = 1 not found", exception.getMessage());
    }
}