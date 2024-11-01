package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class UserRepositoryTest {

    @Spy
    private UserRepository userRepository;

    private final User user1 = new User("John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
    private final User user2 = new User("Jane", "Doe", LocalDate.of(1985, 5, 10), "jane.doe@example.com");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByEmail_shouldReturnUserWhenEmailMatches() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        Optional<User> result = userRepository.findByEmail("john.doe@example.com");

        assertTrue(result.isPresent());
        assertEquals(user1, result.get());
    }

    @Test
    void findByEmailContainingIgnoreCase_shouldReturnMatchingUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = userRepository.findByEmailContainingIgnoreCase("doe");

        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    void findOlderThanBirthdate_shouldReturnUsersOlderThanCutoffDate() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = userRepository.findOlderThanBirthdate(LocalDate.of(1990, 1, 1));

        assertEquals(1, result.size());
        assertEquals(user2, result.get(0));
    }
}
