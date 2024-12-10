package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.exceptions.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidationServiceImplImplTest {
    @Mock
    private UserClient userClient;
    @InjectMocks
    private UserValidationServiceImpl userValidationServiceImpl;

    @Test
    void testValidateUser_UserExists() {
        // given
        Long userId = 100L;
        CreatePostDTO createPostDTO = new CreatePostDTO(userId, "content", "http://media.url");

        // mock the behavior of userClient.verifyUserExistence to return true
        when(userClient.verifyUserExistence(userId)).thenReturn(ResponseEntity.ok(true));

        // when
        userValidationServiceImpl.validateUser(createPostDTO);

        // then
        // No exception is thrown, so this is a successful test
    }

    @Test
    void testValidateUser_UserNotFound() {
        // given
        Long userId = 100L;
        CreatePostDTO createPostDTO = new CreatePostDTO(userId, "content", "http://media.url");

        // mock the behavior of userClient.verifyUserExistence to return false
        when(userClient.verifyUserExistence(userId)).thenReturn(ResponseEntity.ok(false));

        // when & then
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class,
                () -> userValidationServiceImpl.validateUser(createPostDTO));

        // verify the exception message
        assertEquals("User with id = 100 not found", thrown.getMessage());
    }
}