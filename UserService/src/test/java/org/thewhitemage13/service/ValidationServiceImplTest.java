package org.thewhitemage13.service;

import com.google.i18n.phonenumbers.NumberParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceImplTest {

    @Mock
    private EmailValidationServiceImpl emailValidationServiceImpl;

    @Mock
    private FollowerValidationServiceImpl followerValidationServiceImpl;

    @Mock
    private MediaValidationServiceImpl mediaValidationServiceImpl;

    @Mock
    private PasswordValidationServiceImpl passwordValidationServiceImpl;

    @Mock
    private PhoneValidationServiceImpl phoneValidationServiceImpl;

    @Mock
    private PostValidationServiceImpl postValidationService;

    @Mock
    private UserActivityValidationServiceImpl userActivityValidationServiceImpl;

    @InjectMocks
    private ValidationServiceImpl validationServiceImpl;

    @Test
    void validateUsername_DelegatesToUserActivityValidationService() {
        // Arrange
        String username = "testUsername";
        doNothing().when(userActivityValidationServiceImpl).validateUsername(username);

        // Act
        validationServiceImpl.validateUsername(username);

        // Assert
        verify(userActivityValidationServiceImpl, times(1)).validateUsername(username);
    }

    @Test
    void validateUpdateUser_DelegatesToUserActivityValidationService() throws NumberParseException {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("email@example.com");
        createUserDTO.setPhoneNumber("phone");
        createUserDTO.setUsername("username");
        createUserDTO.setRegion("US");

        User updateUser = new User();
        updateUser.setEmail("oldemail@example.com");
        updateUser.setPhoneNumber("oldphone");
        updateUser.setUsername("oldusername");


        when(userActivityValidationServiceImpl.updateUserValidationProcessor(createUserDTO, updateUser)).thenReturn("+1 123-456-7890");

        // Act
        String result = validationServiceImpl.validateUpdateUser(createUserDTO, updateUser);

        // Assert
        assertEquals("+1 123-456-7890", result);
        verify(userActivityValidationServiceImpl, times(1)).updateUserValidationProcessor(createUserDTO, updateUser);
    }

    @Test
    void validatePost_DelegatesToPostValidationService() {
        // Arrange
        Long userId = 1L;
        when(postValidationService.countPostValidation(userId)).thenReturn(10L);

        // Act
        Long result = validationServiceImpl.validatePost(userId);

        // Assert
        assertEquals(10L, result);
        verify(postValidationService, times(1)).countPostValidation(userId);
    }

    @Test
    void validateUpdatePhoneNumber_DelegatesToPhoneValidationService() throws NumberParseException {
        // Arrange
        String phoneNum = "1234567890";
        String region = "US";
        when(phoneValidationServiceImpl.validateUpdatePhoneNumber(phoneNum, region)).thenReturn("+1 123-456-7890");

        // Act
        String result = validationServiceImpl.validateUpdatePhoneNumber(phoneNum, region);

        // Assert
        assertEquals("+1 123-456-7890", result);
        verify(phoneValidationServiceImpl, times(1)).validateUpdatePhoneNumber(phoneNum, region);
    }

    @Test
    void validatePhoneNumber_DelegatesToPhoneValidationService() throws NumberParseException {
        // Arrange
        String phoneNum = "1234567890";
        String region = "US";
        when(phoneValidationServiceImpl.validatePhoneNumber(phoneNum, region)).thenReturn("+1 123-456-7890");

        // Act
        String result = validationServiceImpl.validatePhoneNumber(phoneNum, region);

        // Assert
        assertEquals("+1 123-456-7890", result);
        verify(phoneValidationServiceImpl, times(1)).validatePhoneNumber(phoneNum, region);
    }

    @Test
    void validatePassword_DelegatesToPasswordValidationService() {
        // Arrange
        String password = "Test@123";
        doNothing().when(passwordValidationServiceImpl).validatePassword(password);

        // Act
        validationServiceImpl.validatePassword(password);

        // Assert
        verify(passwordValidationServiceImpl, times(1)).validatePassword(password);
    }

    @Test
    void validatePicture_DelegatesToMediaValidationService() {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("email@example.com");
        createUserDTO.setPhoneNumber("phone");
        createUserDTO.setUsername("username");
        createUserDTO.setRegion("US");

        doNothing().when(mediaValidationServiceImpl).validatePicture(createUserDTO);

        // Act
        validationServiceImpl.validatePicture(createUserDTO);

        // Assert
        verify(mediaValidationServiceImpl, times(1)).validatePicture(createUserDTO);
    }

    @Test
    void validateMedia_DelegatesToMediaValidationService() {
        // Arrange
        Long userId = 1L;
        List<String> expectedMedia = List.of("media1", "media2");
        when(mediaValidationServiceImpl.validateMedia(userId)).thenReturn(expectedMedia);

        // Act
        List<String> result = validationServiceImpl.validateMedia(userId);

        // Assert
        assertEquals(expectedMedia, result);
        verify(mediaValidationServiceImpl, times(1)).validateMedia(userId);
    }

    @Test
    void validateFollowing_DelegatesToFollowerValidationService() {
        // Arrange
        Long userId = 1L;
        when(followerValidationServiceImpl.countFollowingValidation(userId)).thenReturn(5L);

        // Act
        Long result = validationServiceImpl.validateFollowing(userId);

        // Assert
        assertEquals(5L, result);
        verify(followerValidationServiceImpl, times(1)).countFollowingValidation(userId);
    }

    @Test
    void validateFollowers_DelegatesToFollowerValidationService() {
        // Arrange
        Long userId = 1L;
        when(followerValidationServiceImpl.countFollowersValidation(userId)).thenReturn(20L);

        // Act
        Long result = validationServiceImpl.validateFollowers(userId);

        // Assert
        assertEquals(20L, result);
        verify(followerValidationServiceImpl, times(1)).countFollowersValidation(userId);
    }

    @Test
    void validateEmail_DelegatesToEmailValidationService() {
        // Arrange
        String email = "test@example.com";
        doNothing().when(emailValidationServiceImpl).validateEmail(email);

        // Act
        validationServiceImpl.validateEmail(email);

        // Assert
        verify(emailValidationServiceImpl, times(1)).validateEmail(email);
    }

    @Test
    void validateUpdateEmail_DelegatesToEmailValidationService() {
        // Arrange
        String email = "test@example.com";
        doNothing().when(emailValidationServiceImpl).validateUpdateEmail(email);

        // Act
        validationServiceImpl.validateUpdateEmail(email);

        // Assert
        verify(emailValidationServiceImpl, times(1)).validateUpdateEmail(email);
    }
}