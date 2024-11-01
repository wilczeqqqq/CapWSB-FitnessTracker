package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
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

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) { // actually never happens
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        log.info("Fetching User with ID={}", userId);
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        log.info("Fetching User with email={}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Fetching all Users");
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(final Long userId) {
        log.info("Deleting User with ID={}", userId);
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(final Long userId, UserUpdateDto userUpdateDto) {
        log.info("Updating User with ID={}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userRepository.save(merge(user, userUpdateDto));
    }

    public List<User> getUserByEmailContainingIgnoreCase(final String email){
        log.info("Fetching Users containing partly email={}", email);
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<User> getOlderThanBirthdate(final LocalDate birthdate) {
        log.info("Fetching Users older than birthdate={}", birthdate);
        return userRepository.findOlderThanBirthdate(birthdate);
    }

    private User merge(User user, UserUpdateDto userUpdateDto) {
        User updatedUser = new User(
                userUpdateDto.firstName() != null ? userUpdateDto.firstName() : user.getFirstName(),
                userUpdateDto.lastName() != null ? userUpdateDto.lastName() : user.getLastName(),
                userUpdateDto.birthdate() != null ? userUpdateDto.birthdate() : user.getBirthdate(),
                userUpdateDto.email() != null ? userUpdateDto.email() : user.getEmail()
        );
        user.setId(user.getId());
        return updatedUser;
    }
}