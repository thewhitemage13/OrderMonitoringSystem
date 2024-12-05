package org.thewhitemage13.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.exception.EmailBusyException;
import org.thewhitemage13.exception.PhoneNumberAlreadyTakenException;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    // get all users

    @Test
    void handleGetAllUsers_ReturnsValidResponseEntity() {
        // User Test 1
        GetUserDTO user1 = new GetUserDTO();
        user1.setEmail("mytestgmail@gmail.com");
        user1.setFirstName("Test First Name 1");
        user1.setLastname("Test Last Name 1");
        user1.setRegion("US");
        user1.setPhone("213412222");
        user1.setSurname("Test Surname 1");

        // User Test 2
        GetUserDTO user2 = new GetUserDTO();
        user2.setEmail("mytestgmail2@gmail.com");
        user2.setFirstName("My Test First Name 2");
        user2.setLastname("My Test Last Name 2");
        user2.setRegion("US");
        user2.setPhone("213412222");
        user2.setSurname("Test Surname 1");

        // given
        List<GetUserDTO> mockUsers = Arrays.asList
                (
                        user1,
                        user1
                );

        Mockito.doReturn(mockUsers).when(this.userService).getAllUsers();

        // when
        var response = this.userController.getAll();

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUsers, response.getBody());
    }

    @Test
    void handleGetAllUsers_EmptyList() {
        // given
        Mockito.doReturn(List.of()).when(this.userService).getAllUsers();

        // when
        var response = this.userController.getAll();

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void handleGetAllUsers_InternalServerError() {
        // given
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(this.userService).getAllUsers();

        // when
        var response = this.userController.getAll();

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    // create user

    @Test
    void handleCreateUser_ReturnsValidResponseEntity() throws Exception {
        // given
        CreateUserDTO user1 = new CreateUserDTO();
        user1.setEmail("mytestgmail@gmail.com");
        user1.setFirstName("Test First Name 1");
        user1.setLastname("Test Last Name 1");
        user1.setRegion("US");
        user1.setPhone("213412222");
        user1.setSurname("Test Surname 1");

        //when
        var responseEntity = this.userController.createUser(user1);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User created successfully", responseEntity.getBody());
        verify(this.userService).createUser(user1);
    }

    @Test
    void handleCreateUser_EmailBusyException() throws Exception {
        // given
        CreateUserDTO user1 = new CreateUserDTO();
        user1.setEmail("mytestgmail@gmail.com");
        user1.setFirstName("Test First Name 1");
        user1.setLastname("Test Last Name 1");
        user1.setRegion("US");
        user1.setPhone("213412222");
        user1.setSurname("Test Surname 1");

        Mockito.doThrow(new EmailBusyException("User with email = %s already exists")).when(this.userService).createUser(user1);

        // when
        var response = this.userController.createUser(user1);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User with email = %s already exists".formatted(user1.getEmail()), response.getBody());
    }

    @Test
    void handleCreateUser_PhoneNumberAlreadyTakenException() throws Exception {
        // given
        CreateUserDTO user1 = new CreateUserDTO();
        user1.setEmail("mytestgmail@gmail.com");
        user1.setFirstName("Test First Name 1");
        user1.setLastname("Test Last Name 1");
        user1.setRegion("US");
        user1.setPhone("213412222");
        user1.setSurname("Test Surname 1");

        Mockito.doThrow(new PhoneNumberAlreadyTakenException("User with phone number = %s already exists")).when(this.userService).createUser(user1);

        // when
        var response = this.userController.createUser(user1);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User with phone number = %s already exists".formatted(user1.getPhone()), response.getBody());
    }

    @Test
    void handleCreateUser_InternalServerError() throws Exception {
        // given
        CreateUserDTO user1 = new CreateUserDTO();
        user1.setEmail("mytestgmail@gmail.com");
        user1.setFirstName("Test First Name 1");
        user1.setLastname("Test Last Name 1");
        user1.setRegion("US");
        user1.setPhone("213412222");
        user1.setSurname("Test Surname 1");

        Mockito.doThrow(new RuntimeException("Unexpected error")).when(this.userService).createUser(user1);

        // when
        var response = this.userController.createUser(user1);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }

    // find by id

    @Test
    void handleFindUserById_ReturnsValidResponseEntity() {
        // given
        CreateUserDTO user1 = new CreateUserDTO();
        user1.setEmail("mytestgmail@gmail.com");
        user1.setFirstName("Test First Name 1");
        user1.setLastname("Test Last Name 1");
        user1.setRegion("US");
        user1.setPhone("213412222");
        user1.setSurname("Test Surname 1");

        GetUserDTO user2 = new GetUserDTO();
        user2.setSurname(user1.getSurname());
        user2.setEmail(user1.getEmail());
        user2.setFirstName(user1.getFirstName());
        user2.setLastname(user1.getLastname());
        user2.setRegion(user1.getRegion());
        user2.setPhone(user1.getPhone());

        Mockito.doReturn(user2).when(this.userService).getUserById(1L);

        // when
        var response = this.userController.getById(1L);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void handleFindUserById_UserNotFound() {
        // given
        Mockito.doThrow(new UserNotFoundException("User with id = %s not found".formatted(1L))).when(this.userService).getUserById(1L);

        // when
        var response = this.userController.getById(1L);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with id = %s not found".formatted(1L), response.getBody());
    }

    @Test
    void handleFindUserById_InternalServerError() {
        // given
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(this.userService).getUserById(1L);

        // when
        var response = this.userController.getById(1L);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }

    // update

    @Test
    void handleUpdateUser_ReturnsValidResponseEntity() throws Exception {
        // given
        CreateUserDTO user = new CreateUserDTO();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastname("Doe");
        user.setRegion("EU");
        user.setPhone("123456789");
        user.setSurname("Smith");

        Mockito.doNothing().when(userService).updateUser(1L, user);

        // when
        var response = userController.updateUser(1L, user);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody());
        verify(userService).updateUser(1L, user);
    }

    @Test
    void handleUpdateUser_UserNotFound() throws Exception {
        // given
        CreateUserDTO user = new CreateUserDTO();
        Mockito.doThrow(new UserNotFoundException("User with id = 1 not found")).when(userService).updateUser(1L, user);

        // when
        var response = userController.updateUser(1L, user);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with id = 1 not found", response.getBody());
    }

    @Test
    void handleUpdateUser_EmailBusyException() throws Exception {
        // given
        CreateUserDTO user = new CreateUserDTO();
        user.setEmail("test@example.com");
        Mockito.doThrow(new EmailBusyException("Email is already in use")).when(userService).updateUser(1L, user);

        // when
        var response = userController.updateUser(1L, user);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Email address = test@example.com is already in use", response.getBody());
    }

    @Test
    void handleUpdateUser_PhoneNumberAlreadyTakenException() throws Exception {
        // given
        CreateUserDTO user = new CreateUserDTO();
        user.setPhone("87899876553");
        Mockito.doThrow(new PhoneNumberAlreadyTakenException("Phone number = 87899876553 already taken")).when(userService).updateUser(1L, user);

        // when
        var response = userController.updateUser(1L, user);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Phone number = 87899876553 already taken", response.getBody());
    }

    @Test
    void handleUpdateUser_InternalServerError() throws Exception {
        // given
        CreateUserDTO user = new CreateUserDTO();
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).updateUser(1L, user);

        // when
        var response = userController.updateUser(1L, user);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }

    // delete

    @Test
    void handleDeleteUser_ReturnsValidResponseEntity() {
        // given
        Mockito.doNothing().when(userService).deleteUser(1L);

        // when
        var response = userController.deleteUser(1L);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted", response.getBody());
        verify(userService).deleteUser(1L);
    }

    @Test
    void handleDeleteUser_UserNotFound() {
        // given
        Mockito.doThrow(new UserNotFoundException("User with id = 1 not found")).when(userService).deleteUser(1L);

        // when
        var response = userController.deleteUser(1L);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with id = 1 not found", response.getBody());
    }

    @Test
    void handleDeleteUser_InternalServerError() {
        // given
        Mockito.doThrow(new RuntimeException("Unexpected error")).when(userService).deleteUser(1L);

        // when
        var response = userController.deleteUser(1L);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error", response.getBody());
    }

    // check

    @Test
    void handleCheckUser_ReturnsTrue() {
        // given
        Mockito.doReturn(true).when(userService).checkUserId(1L);

        // when
        var response = userController.checkUser(1L);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(userService).checkUserId(1L);
    }

    @Test
    void handleCheckUser_ReturnsFalse() {
        // given
        Mockito.doReturn(false).when(userService).checkUserId(1L);

        // when
        var response = userController.checkUser(1L);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
        verify(userService).checkUserId(1L);
    }

}