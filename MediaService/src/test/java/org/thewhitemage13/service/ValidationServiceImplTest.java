package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.clients.UserClient;
import org.thewhitemage13.exceptions.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {
    @Mock
    private UserClient userClient;
    @InjectMocks
    private ValidationServiceImpl validationServiceImpl;

    @Test
    void validateUser_ShouldPass_WhenUserExists() {
        // given
        Long userId = 1L;
        Mockito.when(userClient.verifyUserExistence(userId))
                .thenReturn(ResponseEntity.ok(true));

        // when / then
        assertDoesNotThrow(() -> validationServiceImpl.validateUser(userId));

        // Проверяем, что клиент был вызван
        Mockito.verify(userClient).verifyUserExistence(userId);
    }

    @Test
    void validateUser_ShouldThrowException_WhenUserDoesNotExist() {
        // given
        Long userId = 2L;
        Mockito.when(userClient.verifyUserExistence(userId))
                .thenReturn(ResponseEntity.ok(false));

        // when / then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> validationServiceImpl.validateUser(userId));

        assertEquals("User with id = 2 not found", exception.getMessage());

        // Проверяем, что клиент был вызван
        Mockito.verify(userClient).verifyUserExistence(userId);
    }
}