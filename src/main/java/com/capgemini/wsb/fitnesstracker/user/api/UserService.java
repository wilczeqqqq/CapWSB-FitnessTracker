package com.capgemini.wsb.fitnesstracker.user.api;

import com.capgemini.wsb.fitnesstracker.user.internal.UserUpdateDto;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates a new User and saves it to the repository.
     *
     * @param user the User to be created.
     * @return the created User.
     * @throws IllegalArgumentException if the User already has an ID.
     * @throws UserEmailAlreadyExistsException if a User with the same email already exists.
     */
    User createUser(User user);

    /**
     * Deletes a User by its ID.
     *
     * @param userId the ID of the User to be deleted.
     * @throws UserNotFoundException if the User with the given ID does not exist.
     */
    void deleteUser(Long userId);

    /**
     * Updates an existing User.
     *
     * @param userId the ID of the User to be updated.
     * @param userDto the DTO containing updated User information.
     * @return the updated User.
     * @throws UserNotFoundException if the User with the given ID does not exist.
     */
    User updateUser(Long userId, UserUpdateDto userDto);

}
