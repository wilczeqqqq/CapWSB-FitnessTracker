package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_DATE;

interface UserRepository extends JpaRepository<User, Long> {

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

    default List<UserFindByEmailDto> findByEmailContainingIgnoreCase(String emailPart) {
        return findAll().stream()
                .filter(user -> user.getEmail() != null &&
                        user.getEmail().toLowerCase().contains(emailPart.toLowerCase()))
                .map(user -> new UserFindByEmailDto(
                        user.getId(),
                        user.getEmail()))
                .collect(Collectors.toList());
    }

    default List<UserDto> findOlderThanBirthdate(LocalDate cutoffDate) {
        return findAll().stream()
                .filter(user -> user.getBirthdate() != null && user.getBirthdate().isBefore(cutoffDate))
                .map(user -> new UserDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getBirthdate(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }
}
