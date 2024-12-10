package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.IncorrectEmailFormatException;
import org.thewhitemage13.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailValidationServiceImplImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private EmailValidationServiceImpl emailValidationServiceImpl;

    @Test
    void validateEmail_ValidEmail_NoException() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.existsUserByEmail(email)).thenReturn(false);

        // Act & Assert
        assertDoesNotThrow(() -> emailValidationServiceImpl.validateEmail(email));

        verify(userRepository, times(1)).existsUserByEmail(email);
    }

    @Test
    void validateEmail_InvalidFormat_ThrowsIncorrectEmailFormatException() {
        // Arrange
        String email = "invalid-email";
        when(userRepository.existsUserByEmail(email)).thenReturn(false);

        // Act & Assert
        IncorrectEmailFormatException exception = assertThrows(
                IncorrectEmailFormatException.class,
                () -> emailValidationServiceImpl.validateEmail(email)
        );

        assertEquals("Incorrect email format", exception.getMessage());
        verify(userRepository, times(1)).existsUserByEmail(email);
    }

    @Test
    void validateEmail_EmailAlreadyTaken_ThrowsEmailBusyException() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.existsUserByEmail(email)).thenReturn(true);

        // Act & Assert
        EmailBusyException exception = assertThrows(
                EmailBusyException.class,
                () -> emailValidationServiceImpl.validateEmail(email)
        );

        assertEquals("Email = test@example.com is already taken", exception.getMessage());
        verify(userRepository, times(1)).existsUserByEmail(email);
    }

    @Test
    void validateUpdateEmail_ValidEmail_NoException() {
        // Arrange
        String email = "test@example.com";

        // Act & Assert
        assertDoesNotThrow(() -> emailValidationServiceImpl.validateUpdateEmail(email));
    }

    @Test
    void validateUpdateEmail_InvalidFormat_ThrowsIncorrectEmailFormatException() {
        // Arrange
        String email = "invalid-email";

        // Act & Assert
        IncorrectEmailFormatException exception = assertThrows(
                IncorrectEmailFormatException.class,
                () -> emailValidationServiceImpl.validateUpdateEmail(email)
        );

        assertEquals("Incorrect email format", exception.getMessage());
    }
}