package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.exception.UsernameIsBusyException;
import org.thewhitemage13.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserActivityValidationServiceImplImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailValidationServiceImpl emailValidationServiceImpl;
    @Mock
    private PhoneValidationServiceImpl phoneValidationServiceImpl;
    @InjectMocks
    private UserActivityValidationServiceImpl userActivityValidationServiceImpl;

    @Test
    void validateUsername_UsernameIsBusy_ThrowsException() {
        // Arrange
        String username = "existingusername";
        when(userRepository.existsUserByUsername(username)).thenReturn(true);

        // Act & Assert
        UsernameIsBusyException exception = assertThrows(UsernameIsBusyException.class,
                () -> userActivityValidationServiceImpl.validateUsername(username));
        assertEquals("User with username = existingusername is busy", exception.getMessage());
        verify(userRepository, times(1)).existsUserByUsername(username);
    }

    @Test
    void validateUsername_UsernameIsNotBusy_Success() {
        // Arrange
        String username = "newusername";
        when(userRepository.existsUserByUsername(username)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> userActivityValidationServiceImpl.validateUsername(username));
        verify(userRepository, times(1)).existsUserByUsername(username);
    }
}