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
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public void deleteUser(final Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteById(userId);

    }

    public User updateUser(final Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        //Checks if any incoming parameter is initial
        //Allows you to update any parameter
        if (userDto.firstName() != null) user.setFirstName(userDto.firstName());
        if (userDto.lastName() != null) user.setLastName(userDto.lastName());
        if (userDto.birthdate() != null) user.setBirthdate(userDto.birthdate());
        if (userDto.email() != null) user.setEmail(userDto.email());

        return userRepository.save(user);
    }
    public List<UserFindByEmailDto> getUserByEmailContainingIgnoreCase(final String email){ return userRepository.findByEmailContainingIgnoreCase(email); }
    public List<UserDto> getOlderThanBirthdate(final LocalDate birthdate) {return userRepository.findOlderThanBirthdate(birthdate);}
}