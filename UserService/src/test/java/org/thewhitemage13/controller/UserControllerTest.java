package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.dto.OpenUserDTO;
import org.thewhitemage13.dto.UserSubscriptionDTO;
import org.thewhitemage13.exception.EmailAlreadyTakenException;
import org.thewhitemage13.exceptions.UserNotFoundException;
import org.thewhitemage13.exception.UsernameIsBusyException;
import org.thewhitemage13.service.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private UserController userController;

    // get by id

    @Test
    void testGetUserById_Success() {
        // given
        Long userId = 1L;
        GetUserDTO user = new GetUserDTO();
        user.setEmail("test@example.com");
        Mockito.doReturn(user).when(userService).getUserById(userId);

        // when
        ResponseEntity<GetUserDTO> response = userController.getUserById(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetUserById_UserNotFound() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new UserNotFoundException("User not found")).when(userService).getUserById(userId);

        // when
        ResponseEntity<GetUserDTO> response = userController.getUserById(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetUserById_InternalServerError() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).getUserById(userId);

        // when
        ResponseEntity<GetUserDTO> response = userController.getUserById(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    // add multiple users

    @Test
    void testAddMultipleUsers_Success() throws Exception{
        // given
        List<CreateUserDTO> users = List.of(new CreateUserDTO());
        Mockito.doNothing().when(userService).addUsers(users);

        // when
        ResponseEntity<String> response = userController.addMultipleUsers(users);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User added successfully", response.getBody());
    }

    @Test
    void testAddMultipleUsers_InternalServerError() throws Exception{
        // given
        List<CreateUserDTO> users = List.of(new CreateUserDTO());
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).addUsers(users);

        // when
        ResponseEntity<String> response = userController.addMultipleUsers(users);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // get users by ids

    @Test
    void testGetUsersByIds_Success() {
        // given
        List<Long> ids = List.of(1L, 2L);
        List<UserSubscriptionDTO> users = List.of(new UserSubscriptionDTO());
        Mockito.doReturn(users).when(userService).getUsersByIds(ids);

        // when
        List<UserSubscriptionDTO> response = userController.getUsersByIds(ids);

        // then
        assertNotNull(response);
        assertEquals(users, response);
    }

    // get public user details

    @Test
    void testGetPublicUserDetails_Success() {
        // given
        Long userId = 1L;
        OpenUserDTO user = new OpenUserDTO();
        Mockito.doReturn(user).when(userService).openUser(userId);

        // when
        ResponseEntity<OpenUserDTO> response = userController.getPublicUserDetails(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testGetPublicUserDetails_UserNotFound() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new UserNotFoundException("User not found")).when(userService).openUser(userId);

        // when
        ResponseEntity<OpenUserDTO> response = userController.getPublicUserDetails(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetPublicUserDetails_InternalServerError() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).openUser(userId);

        // when
        ResponseEntity<OpenUserDTO> response = userController.getPublicUserDetails(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    // register new user

    @Test
    void testRegisterNewUser_Success() throws Exception{
        // given
        CreateUserDTO createUserDTO = new CreateUserDTO();
        Mockito.doNothing().when(userService).registerNewUser(createUserDTO);

        // when
        ResponseEntity<String> response = userController.registerNewUser(createUserDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User created successfully", response.getBody());
    }

    @Test
    void testRegisterNewUser_UsernameIsBusy() throws Exception{
        // given
        Long userId = 1L;
        String username = "Test User Name 1";
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername(username);
        Mockito.doThrow(new UsernameIsBusyException("Username = %s is busy!"
                        .formatted(username)))
                .when(userService)
                .registerNewUser(createUserDTO);

        // when
        ResponseEntity<String> response = userController.registerNewUser(createUserDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Username = %s is busy!".formatted(username), response.getBody());
    }

    @Test
    void testRegisterNewUser_EmailAlreadyTaken() throws Exception{
        // given
        Long userId = 1L;
        String email = "testemail@example.com";
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail(email);
        Mockito.doThrow(new EmailAlreadyTakenException("Email = %s is already taken!"
                        .formatted(email)))
                .when(userService)
                .registerNewUser(createUserDTO);

        // when
        ResponseEntity<String> response = userController.registerNewUser(createUserDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email = %s is already taken!".formatted(email), response.getBody());
    }

    @Test
    void testRegisterNewUser_InternetServerError() throws Exception{
        // given
        CreateUserDTO createUserDTO = new CreateUserDTO();
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).registerNewUser(createUserDTO);

        // when
        ResponseEntity<String> response = userController.registerNewUser(createUserDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }

    // ger username by id

    @Test
    void testGetUserNameById_Success() {
        // given
        Long userId = 1L;
        String username = "testUser";
        Mockito.doReturn(username).when(userService).getUsernameById(userId);

        // when
        ResponseEntity<String> response = userController.getUserNameById(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(username, response.getBody());
    }

    @Test
    void testGetUserNameById_UserNotFound() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new UserNotFoundException("User not found")).when(userService).getUsernameById(userId);

        // when
        ResponseEntity<String> response = userController.getUserNameById(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with id = %s not found".formatted(userId), response.getBody());
    }

    @Test
    void testGetUserNameById_InternalServerError() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).getUsernameById(userId);

        // when
        ResponseEntity<String> response = userController.getUserNameById(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }

    // verify

    @Test
    void testVerifyUserExistence_Success() {
        // given
        Long userId = 1L;
        Mockito.doReturn(true).when(userService).userVerification(userId);

        // when
        ResponseEntity<Boolean> response = userController.verifyUserExistence(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void testVerifyUserExistence_InternalServerError() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).userVerification(userId);

        // when
        ResponseEntity<Boolean> response = userController.verifyUserExistence(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody());
    }

    // update

    @Test
    void testUpdateUser_Success() throws Exception{
        // given
        Long userId = 1L;
        CreateUserDTO createUserDTO = new CreateUserDTO();
        Mockito.doNothing().when(userService).updateUserProfile(userId, createUserDTO);

        // when
        ResponseEntity<String> response = userController.updateUser(userId, createUserDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated", response.getBody());
    }

    @Test
    void testUpdateUser_UserNotFound() throws Exception{
        // given
        Long userId = 1L;
        CreateUserDTO createUserDTO = new CreateUserDTO();
        Mockito.doThrow(new UserNotFoundException("User not found")).when(userService).updateUserProfile(userId, createUserDTO);

        // when
        ResponseEntity<String> response = userController.updateUser(userId, createUserDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with id = %s not found!".formatted(userId), response.getBody());
    }

    @Test
    void testUpdateUser_UsernameIsBusy() throws Exception{
        // given
        Long userId = 1L;
        CreateUserDTO createUserDTO = new CreateUserDTO();
        Mockito.doThrow(new UsernameIsBusyException("Username is busy")).when(userService).updateUserProfile(userId, createUserDTO);

        // when
        ResponseEntity<String> response = userController.updateUser(userId, createUserDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Username = %s is busy!".formatted(createUserDTO.getUsername()), response.getBody());
    }

    @Test
    void testUpdateUser_EmailAlreadyTaken() throws Exception{
        // given
        Long userId = 1L;
        String email = "test@test.com";
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail(email);
        Mockito.doThrow(new EmailAlreadyTakenException("Email = %s is already taken!".formatted(email))).when(userService).updateUserProfile(userId, createUserDTO);

        // when
        ResponseEntity<String> response = userController.updateUser(userId, createUserDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email = %s is already taken!".formatted(email), response.getBody());
    }

    @Test
    void testUpdateUser_InternetServerError() throws Exception{
        // given
        Long userId = 1L;
        CreateUserDTO createUserDTO = new CreateUserDTO();
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).updateUserProfile(userId, createUserDTO);

        // when
        ResponseEntity<String> response = userController.updateUser(userId, createUserDTO);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }

    // delete

    @Test
    void testDeleteUser_Success() {
        // given
        Long userId = 1L;
        Mockito.doNothing().when(userService).deleteUser(userId);

        // when
        ResponseEntity<String> response = userController.deleteUser(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted", response.getBody());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new UserNotFoundException("User with id = %s not found!".formatted(userId))).when(userService).deleteUser(userId);

        // when
        ResponseEntity<String> response = userController.deleteUser(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with id = %s not found!".formatted(userId), response.getBody());
    }

    @Test
    void testDeleteUser_InternalServerError() {
        // given
        Long userId = 1L;
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).deleteUser(userId);

        // when
        ResponseEntity<String> response = userController.deleteUser(userId);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred: Unexpected error", response.getBody());
    }

    // find user

    @Test
    void testFindUser_UserNotFound() {
        // given
        String username = "testUser";
        Mockito.doThrow(new UserNotFoundException("User not found")).when(userService).getInformationAboutUser(username);

        // when
        Object response = userController.findUser(username);

        // then
        assertNotNull(response);
        assertTrue(response instanceof ResponseEntity);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User with id = %s not found!", responseEntity.getBody());
        Mockito.verify(userService).getInformationAboutUser(username);
    }

    @Test
    void testFindUser_InternalServerError() {
        // given
        String username = "testUser";
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).getInformationAboutUser(username);

        // when
        Object response = userController.findUser(username);

        // then
        assertNotNull(response);
        assertTrue(response instanceof ResponseEntity);
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) response;
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("An error occurred: Unexpected error", responseEntity.getBody());
        Mockito.verify(userService).getInformationAboutUser(username);
    }
}