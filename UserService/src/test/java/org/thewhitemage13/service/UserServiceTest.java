package org.thewhitemage13.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.thewhitemage13.dto.CreateUserDTO;
import org.thewhitemage13.dto.GetUserDTO;
import org.thewhitemage13.entity.User;
import org.thewhitemage13.exception.UserNotFoundException;
import org.thewhitemage13.processor.UserProcessor;
import org.thewhitemage13.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private KafkaTemplate<Long, Object> kafkaTemplate;
    @Mock
    private ValidationService validationService;
    @Mock
    private UserProcessor userProcessor;
    @InjectMocks
    private UserService userService;

    // get all

    @Test
    void handleGetAllUsers_ReturnsAllUsers() {
        User user1 = new User();
        user1.setEmail("mytestgmail@gmail.com");
        user1.setFirstName("Test First Name 1");
        user1.setLastname("Test Last Name 1");
        user1.setRegion("US");
        user1.setPhone("213412222");
        user1.setSurname("Test Surname 1");

        User user2 = new User();
        user2.setEmail("mytestgmail2@gmail.com");
        user2.setFirstName("My Test First Name 2");
        user2.setLastname("My Test Last Name 2");
        user2.setRegion("US");
        user2.setPhone("213412223");
        user2.setSurname("Test Surname 2");

        List<User> mockUsers = Arrays.asList(user1, user2);

        Mockito.doReturn(mockUsers).when(this.userRepository).findAll();

        var response = this.userService.getAllUsers();

        assertNotNull(response);
        assertEquals(2, response.size());
    }

    // find by id

    @Test
    void handleFindUserById_ReturnsUser() {
        // given
        User user1 = new User();
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

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        // when
        var response = this.userService.getUserById(1L);

        // then
        assertNotNull(response);
    }

    @Test
    void handleFindUserById_UserNotFound() {
        // given
        Long userId = 999L;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when / then
        Assertions.assertThrows(UserNotFoundException.class, () -> this.userService.getUserById(userId));
    }

    // create user

    @Test
    void handleCreateUser_Success() throws Exception {
        // Given
        CreateUserDTO createUserDto = new CreateUserDTO();
        createUserDto.setEmail("newuser@gmail.com");
        createUserDto.setPassword("StrongPass123!");
        createUserDto.setPhone("1234567890");
        createUserDto.setRegion("US");

        User user = new User();
        user.setId(1L);
        user.setEmail(createUserDto.getEmail());

        Mockito.doNothing().when(validationService).validateEmail(createUserDto.getEmail());
        Mockito.doNothing().when(validationService).validatePassword(createUserDto.getPassword());
        Mockito.when(validationService.validateNewPhone(createUserDto.getPhone(), createUserDto.getRegion()))
                .thenReturn(createUserDto.getPhone());
        Mockito.doAnswer(invocation -> {
            User u = invocation.getArgument(1);
            u.setId(1L);
            return null;
        }).when(userProcessor).createProcessor(Mockito.any(CreateUserDTO.class), Mockito.any(User.class), Mockito.anyString());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // When
        userService.createUser(createUserDto);

        // Then
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send("user.created", 1L);
    }

    // delete user

    @Test
    void handleDeleteUser_Success() throws Exception {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setEmail("user@gmail.com");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).delete(user);

        // When
        userService.deleteUser(userId);

        // Then
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    void handleDeleteUser_UserNotFound() {
        // Given
        Long userId = 999L;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When / Then
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser(userId));
    }

    // update

    @Test
    void handleUpdateUser_Success() throws Exception {
        // Given
        Long userId = 1L;
        CreateUserDTO createUserDto = new CreateUserDTO();
        createUserDto.setEmail("updated@gmail.com");
        createUserDto.setPassword("UpdatedPass123!");
        createUserDto.setPhone("1234567890");
        createUserDto.setRegion("US");

        User user = new User();
        user.setId(userId);
        user.setEmail("old@gmail.com");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.doNothing().when(validationService).validatePassword(createUserDto.getPassword());
        Mockito.when(validationService.validateUserForUpdate(user, createUserDto)).thenReturn(createUserDto.getPhone());
        Mockito.doAnswer(invocation -> {
            User u = invocation.getArgument(1);
            u.setEmail(createUserDto.getEmail());
            return null;
        }).when(userProcessor).createProcessor(Mockito.any(CreateUserDTO.class), Mockito.any(User.class), Mockito.anyString());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        // When
        userService.updateUser(userId, createUserDto);

        // Then
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    // check

    @Test
    void handleCheckUserId_Exists() {
        // Given
        Long userId = 1L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);

        // When
        boolean exists = userService.checkUserId(userId);

        // Then
        assertTrue(exists);
        Mockito.verify(userRepository, Mockito.times(1)).existsById(userId);
    }

    @Test
    void handleCheckUserId_NotExists() {
        // Given
        Long userId = 999L;
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        // When
        boolean exists = userService.checkUserId(userId);

        // Then
        assertFalse(exists);
        Mockito.verify(userRepository, Mockito.times(1)).existsById(userId);
    }

}