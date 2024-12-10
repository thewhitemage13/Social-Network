package org.thewhitemage13.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exceptions.UserNotFoundException;
import org.thewhitemage13.processor.UserProcessorImpl;
import org.thewhitemage13.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ValidationServiceImpl validationServiceImpl;
    @Mock
    private UserProcessorImpl userProcessorImpl;
    @Mock
    private KafkaTemplate<Long, Object> kafkaTemplate;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUsernameById_ValidId_ReturnsUsername() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockUser.setUsername("testuser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        String username = userService.getUsernameById(userId);

        // Assert
        assertEquals("testuser", username);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUsernameById_InvalidId_ThrowsUserNotFoundException() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUsernameById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void registerNewUser_ValidData_UserRegistered() throws Exception {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername("newuser");
        createUserDTO.setEmail("email@example.com");
        createUserDTO.setPassword("password123");
        createUserDTO.setPhoneNumber("1234567890");
        createUserDTO.setRegion("US");

        String validatedPhoneNumber = "+11234567890";
        when(validationServiceImpl.validatePhoneNumber(createUserDTO.getPhoneNumber(), createUserDTO.getRegion())).thenReturn(validatedPhoneNumber);

        User savedUser = new User();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        userService.registerNewUser(createUserDTO);

        // Assert
        verify(validationServiceImpl, times(1)).validatePhoneNumber(createUserDTO.getPhoneNumber(), createUserDTO.getRegion());
        verify(userRepository, times(1)).save(any(User.class));
        verify(kafkaTemplate, times(1)).executeInTransaction(any());
    }

    @Test
    void deleteUser_ValidId_UserDeleted() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        assertDoesNotThrow(() -> userService.deleteUser(userId));

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(mockUser);
        verify(kafkaTemplate, times(1)).executeInTransaction(any());
    }

    @Test
    void deleteUser_InvalidId_ThrowsUserNotFoundException() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
        verify(kafkaTemplate, never()).executeInTransaction(any());
    }

    @Test
    void getUserById_ValidId_ReturnsGetUserDTO() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setUserId(userId);
        GetUserDTO mockGetUserDTO = new GetUserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userProcessorImpl.getGetUserDTO(mockUser)).thenReturn(mockGetUserDTO);

        // Act
        GetUserDTO result = userService.getUserById(userId);

        // Assert
        assertEquals(mockGetUserDTO, result);
        verify(userRepository, times(1)).findById(userId);
        verify(userProcessorImpl, times(1)).getGetUserDTO(mockUser);
    }

    @Test
    void getUserById_InvalidId_ThrowsUserNotFoundException() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUserProfile_ValidData_UpdatesUser() throws Exception {
        // Arrange
        Long userId = 1L;
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername("updateduser");

        User mockUser = new User();
        mockUser.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        userService.updateUserProfile(userId, createUserDTO);

        // Assert
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(mockUser);
        verify(kafkaTemplate, times(1)).executeInTransaction(any());
    }
}