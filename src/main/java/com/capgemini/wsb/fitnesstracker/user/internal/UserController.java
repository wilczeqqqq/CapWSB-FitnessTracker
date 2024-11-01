package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
/**
 * REST controller for User management.
 * Provides endpoints for creating, getting, updating, and deleting users.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Gets a list of all users.
     *
     * @return list of {@link UserDto}  representing all users.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Gets a simplified list of all users.
     *
     * @return list of {@link UserSimpleDto} representing all users in a simplified format.
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUsersSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * Gets a user by their unique ID.
     *
     * @param id ID of the user to get.
     * @return a {@link UserDto} representing the found User.
     * @throws UserNotFoundException if no user with the specified ID exists in database.
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Gets users whose email contains the specified partial string, ignoring case.
     *
     * @param email partial email string to search for.
     * @return a list of {@link UserFindByEmailDto} representing users that match the email search criteria.
     */
    @GetMapping("/email")
    public List<UserFindByEmailDto> getUserByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmailContainingIgnoreCase(email)
                .stream()
                .map(userMapper::toFindByEmailDto)
                .toList();
    }

    /**
     * Gets users who are older than the specified birthdate.
     *
     * @param time cutoff birthdate in the format "YYYY-MM-DD".
     * @return list of {@link UserDto} representing users older than the given date.
     */
    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThan(@PathVariable("time") LocalDate time) {
        return userService.getOlderThanBirthdate(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Creates a new user.
     *
     * @param userDto DTO containing information for the new user.
     * @return created {@link User}.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) {
        return userService.createUser(userMapper.toEntity(userDto));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id ID of the user to delete.
     * @throws UserNotFoundException if no user with the specified ID exists.
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable("userId") Long id) {
        userService.deleteUser(id);
    }

    /**
     * Updates an existing user.
     *
     * @param userId ID of the user to update.
     * @param userUpdateDto DTO containing updated user information.
     * @return updated {@link User}.
     */
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(userId, userUpdateDto);
    }
}
