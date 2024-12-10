package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.User;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * Provides standard CRUD operations and custom queries for user data.
 * This interface extends {@link JpaRepository}, gaining access to a wide range
 * of database operations without requiring explicit implementation.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Find a user by their username.</li>
 *     <li>Check existence of users by username, email, phone number, or ID.</li>
 * </ul>
 *
 * <h2>Spring Integration:</h2>
 * <ul>
 *     <li>Annotated with {@link Repository} for Spring Data JPA functionality.</li>
 *     <li>Leverages Spring's dependency injection to manage data access seamlessly.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return an {@link Optional} containing the user if found, or empty if not
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with the specified username exists.
     *
     * @param username the username to check
     * @return {@code true} if a user with the given username exists; {@code false} otherwise
     */
    boolean existsUserByUsername(String username);

    /**
     * Checks if a user with the specified email exists.
     *
     * @param email the email to check
     * @return {@code true} if a user with the given email exists; {@code false} otherwise
     */
    boolean existsUserByEmail(String email);

    /**
     * Checks if a user with the specified phone number exists.
     *
     * @param phone the phone number to check
     * @return {@code true} if a user with the given phone number exists; {@code false} otherwise
     */
    boolean existsUserByPhoneNumber(String phone);

    /**
     * Checks if a user with the specified ID exists.
     *
     * @param id the ID to check
     * @return {@code true} if a user with the given ID exists; {@code false} otherwise
     */
    boolean existsUserByUserId(Long id);
}
