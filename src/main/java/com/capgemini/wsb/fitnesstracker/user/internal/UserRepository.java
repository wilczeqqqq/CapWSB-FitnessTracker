package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    /**
     * Searches for users whose email contains the specified partial string, ignoring case.
     *
     * @param emailPart partial email string to search for.
     * @return {@link List} of users whose email contains the specified partial string, ignoring case.
     */
    default List<User> findByEmailContainingIgnoreCase(String emailPart) {
        return findAll().stream()
                .filter(user -> user.getEmail().toLowerCase().contains(emailPart.toLowerCase()))
                .toList();
    }

    /**
     * Finds users who were born before a specified cutoff date.
     *
     * @param cutoffDate date to compare against.
     * @return {@link List} of users who are older than the specified birthdate.
     */
    default List<User> findOlderThanBirthdate(LocalDate cutoffDate) {
        return findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(cutoffDate))
                .toList();
    }
}
