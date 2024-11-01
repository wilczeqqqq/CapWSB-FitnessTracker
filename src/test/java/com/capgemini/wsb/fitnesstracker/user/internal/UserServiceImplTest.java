package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailAlreadyExistsException;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldThrowExceptionIfUserHasId() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        user.setId(1L);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        assertEquals("User has already DB ID, update is not permitted!", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowExceptionIfEmailAlreadyExists() {
        User user = new User(null, "John", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserEmailAlreadyExistsException exception = assertThrows(UserEmailAlreadyExistsException.class, () -> userService.createUser(user));

        assertEquals("User with email=john.doe@example.com already exists", exception.getMessage());
    }

    @Test
    void createUser_shouldSaveUserIfValid() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void getUser_shouldReturnUserIfExists() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUser(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void getUser_shouldReturnEmptyIfNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUser(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void getUserByEmail_shouldReturnUserIfExists() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByEmail("john.doe@example.com");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void getUserByEmail_shouldReturnEmptyIfNotExists() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserByEmail("john.doe@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    void findAllUsers_shouldReturnListOfUsers() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.findAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    void deleteUser_shouldThrowExceptionIfUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));

        assertEquals("User with ID=1 was not found", exception.getMessage());
    }

    @Test
    void deleteUser_shouldDeleteUserIfExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateUser_shouldThrowExceptionIfUserNotFound() {
        UserUpdateDto userUpdateDto = new UserUpdateDto("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, userUpdateDto));

        assertEquals("User with ID=1 was not found", exception.getMessage());
    }

    @Test
    void updateUser_shouldUpdateUserIfExists() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        UserUpdateDto userUpdateDto = new UserUpdateDto("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1L, userUpdateDto);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void getUserByEmailContainingIgnoreCase_shouldReturnListOfUsers() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findByEmailContainingIgnoreCase("john")).thenReturn(List.of(user));

        List<User> result = userService.getUserByEmailContainingIgnoreCase("john");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @Test
    void getOlderThanBirthdate_shouldReturnListOfUsers() {
        User user = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findOlderThanBirthdate(LocalDate.of(2000, 1, 1))).thenReturn(List.of(user));

        List<User> result = userService.getOlderThanBirthdate(LocalDate.of(2000, 1, 1));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }
}