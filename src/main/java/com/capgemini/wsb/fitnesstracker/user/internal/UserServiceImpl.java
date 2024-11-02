package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailAlreadyExistsException;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Creates a new User and saves it to the repository.
     *
     * @param user User to be created.
     * @return created User.
     * @throws IllegalArgumentException if the User already has an ID.
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) { // actually never happens
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserEmailAlreadyExistsException(user.getEmail());
        }
        return userRepository.save(user);
    }

    /**
     * Gets a User by its ID.
     *
     * @param userId ID of the User to retrieve.
     * @return an Optional containing the User if found, otherwise empty list.
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        log.info("Fetching User with ID={}", userId);
        return userRepository.findById(userId);
    }

    /**
     * Gets a User by their email.
     *
     * @param email email of the User to retrieve.
     * @return an Optional containing the User if found, otherwise empty list.
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        log.info("Fetching User with email={}", email);
        return userRepository.findByEmail(email);
    }

    /**
     * Gets all Users from the repository.
     *
     * @return List of all Users.
     */
    @Override
    public List<User> findAllUsers() {
        log.info("Fetching all Users");
        return userRepository.findAll();
    }

    /**
     * Deletes a User by its ID.
     *
     * @param userId ID of the User.
     * @throws UserNotFoundException if the User with the given ID does not exist.
     */
    @Override
    public void deleteUser(final Long userId) {
        log.info("Deleting User with ID={}", userId);
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);
    }

    /**
     * Updates an existing User.
     *
     * @param userId ID of the User to update.
     * @param userUpdateDto DTO containing updated User information.
     * @return updated User.
     * @throws UserNotFoundException if the User with the given ID does not exist.
     */
    @Override
    public User updateUser(final Long userId, UserUpdateDto userUpdateDto) {
        log.info("Updating User with ID={}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userRepository.save(merge(user, userUpdateDto));
    }

    /**
     * Gets Users whose email partially matches the given string.
     *
     * @param email email string to search for.
     * @return List of Users with matching email.
     */
    @Override
    public List<User> getUserByEmailContainingIgnoreCase(final String email){
        log.info("Fetching Users containing emails with part={}", email);
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    /**
     * Gets Users who are older than the specified birthdate.
     *
     * @param birthdate the birthdate to compare.
     * @return List of Users older than the given birthdate.
     */
    @Override
    public List<User> getOlderThanBirthdate(final LocalDate birthdate) {
        log.info("Fetching Users older than birthdate={}", birthdate);
        return userRepository.findOlderThanBirthdate(birthdate);
    }

    /**
     * Merges fields from a UserUpdateDto into an existing User.
     *
     * @param user original User entity.
     * @param userUpdateDto DTO containing updated User fields.
     * @return new User with merged fields.
     */
    private User merge(User user, UserUpdateDto userUpdateDto) {
        User updatedUser = new User(
                userUpdateDto.firstName() != null ? userUpdateDto.firstName() : user.getFirstName(),
                userUpdateDto.lastName() != null ? userUpdateDto.lastName() : user.getLastName(),
                userUpdateDto.birthdate() != null ? userUpdateDto.birthdate() : user.getBirthdate(),
                userUpdateDto.email() != null ? userUpdateDto.email() : user.getEmail()
        );
        updatedUser.setId(user.getId());
        return updatedUser;
    }
}