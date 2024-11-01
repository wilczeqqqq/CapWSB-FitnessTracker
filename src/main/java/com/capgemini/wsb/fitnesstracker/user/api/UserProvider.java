package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user based on their email.
     * If the user with given email is not found, then {@link Optional#empty()} will be returned.
     *
     * @param email The email of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    List<User> findAllUsers();

    /**
     * Retrieves users whose email contains the specified string, ignoring case.
     *
     * @param email The email substring to search for.
     * @return A list of users whose email contains the specified substring.
     */
    List<User> getUserByEmailContainingIgnoreCase(String email);

    /**
     * Retrieves users who are older than the specified birthdate.
     *
     * @param birthdate The birthdate to compare against.
     * @return A list of users who are older than the specified birthdate.
     */
    List<User> getOlderThanBirthdate(LocalDate birthdate);
}
