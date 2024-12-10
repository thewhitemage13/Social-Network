package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.exception.IncorrectPhoneNumberException;
import org.thewhitemage13.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneValidationServiceImplImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PhoneValidationServiceImpl phoneValidationServiceImpl;

    @Test
    void validatePhoneNumber_ValidPhoneNumber_NoException() throws NumberParseException {
        // Arrange
        String validPhone = "+14155552671";
        String region = "US";

        when(userRepository.existsUserByPhoneNumber(validPhone)).thenReturn(false);

        // Act
        String formattedNumber = phoneValidationServiceImpl.validatePhoneNumber(validPhone, region);

        // Assert
        assertEquals("+1 415-555-2671", formattedNumber);
        verify(userRepository, times(1)).existsUserByPhoneNumber(validPhone);
    }

    @Test
    void validatePhoneNumber_PhoneNumberAlreadyExists_ThrowsException() {
        // Arrange
        String existingPhone = "+14155552671";
        String region = "US";

        when(userRepository.existsUserByPhoneNumber(existingPhone)).thenReturn(true);

        // Act & Assert
        IncorrectPhoneNumberException exception = assertThrows(
                IncorrectPhoneNumberException.class,
                () -> phoneValidationServiceImpl.validatePhoneNumber(existingPhone, region)
        );

        assertEquals("Phone number is already in use", exception.getMessage());
        verify(userRepository, times(1)).existsUserByPhoneNumber(existingPhone);
    }

    @Test
    void validatePhoneNumber_InvalidPhoneNumber_ThrowsException() {
        // Arrange
        String invalidPhone = "12345";
        String region = "US";

        when(userRepository.existsUserByPhoneNumber(invalidPhone)).thenReturn(false);

        // Act & Assert
        IncorrectPhoneNumberException exception = assertThrows(
                IncorrectPhoneNumberException.class,
                () -> phoneValidationServiceImpl.validatePhoneNumber(invalidPhone, region)
        );

        assertEquals("Incorrect phone number", exception.getMessage());
        verify(userRepository, times(1)).existsUserByPhoneNumber(invalidPhone);
    }

    @Test
    void validateUpdatePhoneNumber_ValidPhoneNumber_NoException() throws NumberParseException {
        // Arrange
        String validPhone = "+447911123456";
        String region = "GB";

        // Act
        String formattedNumber = phoneValidationServiceImpl.validateUpdatePhoneNumber(validPhone, region);

        // Assert
        assertEquals("+44 7911 123456", formattedNumber);
        verifyNoInteractions(userRepository);
    }

    @Test
    void validateUpdatePhoneNumber_InvalidPhoneNumber_ThrowsException() {
        // Arrange
        String invalidPhone = "abcdef";
        String region = "US";

        // Act & Assert
        NumberParseException exception = assertThrows(
                NumberParseException.class,
                () -> phoneValidationServiceImpl.validateUpdatePhoneNumber(invalidPhone, region)
        );

        assertEquals("The string supplied did not seem to be a phone number.", exception.getMessage());
        verifyNoInteractions(userRepository);
    }
}