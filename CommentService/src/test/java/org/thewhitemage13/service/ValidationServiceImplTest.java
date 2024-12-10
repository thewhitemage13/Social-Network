package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.PostClient;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dto.CommentCreateDto;
import org.thewhitemage13.exceptions.PostNotFoundException;
import org.thewhitemage13.exceptions.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {
    @Mock
    private PostClient postClient;
    @Mock
    private UserClient userClient;
    @InjectMocks
    private ValidationServiceImpl validationServiceImpl;

    @Test
    void validateUser_UserExists_NoExceptionThrown() {
        // given
        Long userId = 1L;
        CommentCreateDto dto = new CommentCreateDto();
        dto.setUserId(userId);

        Mockito.when(userClient.verifyUserExistence(userId))
                .thenReturn(ResponseEntity.ok(true));

        // when
        assertDoesNotThrow(() -> validationServiceImpl.validateUser(dto));

        // then
        Mockito.verify(userClient).verifyUserExistence(userId);
    }

    @Test
    void validateUser_UserDoesNotExist_ThrowsUserNotFoundException() {
        // given
        Long userId = 1L;
        CommentCreateDto dto = new CommentCreateDto();
        dto.setUserId(userId);

        Mockito.when(userClient.verifyUserExistence(userId))
                .thenReturn(ResponseEntity.ok(false));

        // when & then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> validationServiceImpl.validateUser(dto));

        assertEquals("User with id = 1 not found", exception.getMessage());
        Mockito.verify(userClient).verifyUserExistence(userId);
    }

    @Test
    void validatePost_PostExists_NoExceptionThrown() {
        // given
        Long postId = 1L;
        CommentCreateDto dto = new CommentCreateDto();
        dto.setPostId(postId);

        Mockito.when(postClient.postVerification(postId))
                .thenReturn(ResponseEntity.ok(true));

        // when
        assertDoesNotThrow(() -> validationServiceImpl.validatePost(dto));

        // then
        Mockito.verify(postClient).postVerification(postId);
    }

    @Test
    void validatePost_PostDoesNotExist_ThrowsPostNotFoundException() {
        // given
        Long postId = 1L;
        CommentCreateDto dto = new CommentCreateDto();
        dto.setPostId(postId);

        Mockito.when(postClient.postVerification(postId))
                .thenReturn(ResponseEntity.ok(false));

        // when & then
        PostNotFoundException exception = assertThrows(PostNotFoundException.class,
                () -> validationServiceImpl.validatePost(dto));

        assertEquals("Post with id = 1 not found", exception.getMessage());
        Mockito.verify(postClient).postVerification(postId);
    }
}