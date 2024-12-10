package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.thewhitemage13.exception.IncorrectPasswordFormatException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidationServiceImplImplTest {
    private final PasswordValidationServiceImpl passwordValidationServiceImpl = new PasswordValidationServiceImpl();

    @Test
    void validatePassword_ValidPassword_NoException() {
        // Arrange
        String validPassword = "Valid1@Password";

        // Act & Assert
        assertDoesNotThrow(() -> passwordValidationServiceImpl.validatePassword(validPassword));
    }

    @Test
    void validatePassword_NoUpperCase_ThrowsException() {
        // Arrange
        String invalidPassword = "valid1@password";

        // Act & Assert
        IncorrectPasswordFormatException exception = assertThrows(
                IncorrectPasswordFormatException.class,
                () -> passwordValidationServiceImpl.validatePassword(invalidPassword)
        );

        assertEquals("Incorrect password format", exception.getMessage());
    }

    @Test
    void validatePassword_NoDigit_ThrowsException() {
        // Arrange
        String invalidPassword = "Valid@Password";

        // Act & Assert
        IncorrectPasswordFormatException exception = assertThrows(
                IncorrectPasswordFormatException.class,
                () -> passwordValidationServiceImpl.validatePassword(invalidPassword)
        );

        assertEquals("Incorrect password format", exception.getMessage());
    }

    @Test
    void validatePassword_NoSpecialCharacter_ThrowsException() {
        // Arrange
        String invalidPassword = "Valid1Password";

        // Act & Assert
        IncorrectPasswordFormatException exception = assertThrows(
                IncorrectPasswordFormatException.class,
                () -> passwordValidationServiceImpl.validatePassword(invalidPassword)
        );

        assertEquals("Incorrect password format", exception.getMessage());
    }

    @Test
    void validatePassword_WithWhitespace_ThrowsException() {
        // Arrange
        String invalidPassword = "Valid1 @Password";

        // Act & Assert
        IncorrectPasswordFormatException exception = assertThrows(
                IncorrectPasswordFormatException.class,
                () -> passwordValidationServiceImpl.validatePassword(invalidPassword)
        );

        assertEquals("Incorrect password format", exception.getMessage());
    }

    @Test
    void validatePassword_EmptyPassword_ThrowsException() {
        // Arrange
        String invalidPassword = "";

        // Act & Assert
        IncorrectPasswordFormatException exception = assertThrows(
                IncorrectPasswordFormatException.class,
                () -> passwordValidationServiceImpl.validatePassword(invalidPassword)
        );

        assertEquals("Incorrect password format", exception.getMessage());
    }

    @Test
    void validatePassword_NullPassword_ThrowsException() {
        // Arrange
        String invalidPassword = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> passwordValidationServiceImpl.validatePassword(invalidPassword));
    }
}