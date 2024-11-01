package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    private final User userTest = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
    private final UserDto userDtoTest = new UserDto(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnListOfUserDtos() {
        when(userService.findAllUsers()).thenReturn(List.of(userTest));
        when(userMapper.toDto(userTest)).thenReturn(userDtoTest);

        List<UserDto> result = userController.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDtoTest, result.get(0));
    }

    @Test
    void getAllUsersSimple_shouldReturnListOfUserSimpleDtos() {
        UserSimpleDto userSimpleDto = new UserSimpleDto(1L, "John", "Doe");

        when(userService.findAllUsers()).thenReturn(List.of(userTest));
        when(userMapper.toSimpleDto(userTest)).thenReturn(userSimpleDto);

        List<UserSimpleDto> result = userController.getAllUsersSimple();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userSimpleDto, result.get(0));
    }

    @Test
    void getUserById_shouldReturnUserDto() {
        when(userService.getUser(1L)).thenReturn(Optional.of(userTest));
        when(userMapper.toDto(userTest)).thenReturn(userDtoTest);

        UserDto result = userController.getUserById(1L);

        assertNotNull(result);
        assertEquals(userDtoTest, result);
    }

    @Test
    void getUserById_shouldThrowUserNotFoundException() {
        when(userService.getUser(1L)).thenReturn(Optional.empty());

        UserNotFoundException e = assertThrows(UserNotFoundException.class, () -> userController.getUserById(1L));
        assertEquals("User with ID=1 was not found", e.getMessage());
    }

    @Test
    void getUserByEmail_shouldReturnListOfUserFindByEmailDtos() {
        UserFindByEmailDto userFindByEmailDto = new UserFindByEmailDto(1L, "john.doe@example.com");

        when(userService.getUserByEmailContainingIgnoreCase("john")).thenReturn(List.of(userTest));
        when(userMapper.toFindByEmailDto(userTest)).thenReturn(userFindByEmailDto);

        List<UserFindByEmailDto> result = userController.getUserByEmail("john");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userFindByEmailDto, result.get(0));
    }

    @Test
    void getUsersOlderThan_shouldReturnListOfUserDtos() {
        when(userService.getOlderThanBirthdate(LocalDate.of(1990, 1, 1))).thenReturn(List.of(userTest));
        when(userMapper.toDto(userTest)).thenReturn(userDtoTest);

        List<UserDto> result = userController.getUsersOlderThan(LocalDate.of(1990, 1, 1));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDtoTest, result.get(0));
    }

    @Test
    void addUser_shouldCreateAndReturnUser() {
        when(userMapper.toEntity(userDtoTest)).thenReturn(userTest);
        when(userService.createUser(userTest)).thenReturn(userTest);

        User result = userController.addUser(userDtoTest);

        assertNotNull(result);
        assertEquals(userTest, result);
    }

    @Test
    void removeUser_shouldDeleteUser() {
        doNothing().when(userService).deleteUser(1L);

        userController.removeUser(1L);

        verify(userService, times(1)).deleteUser(1L);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {
        UserUpdateDto userUpdateDto = new UserUpdateDto("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

        when(userService.updateUser(1L, userUpdateDto)).thenReturn(userTest);

        User result = userController.updateUser(1L, userUpdateDto);

        assertNotNull(result);
        assertEquals(userTest, result);
    }
}