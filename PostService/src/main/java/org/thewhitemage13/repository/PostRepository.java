package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thewhitemage13.entity.Post;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Post} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing methods for CRUD
 * operations and custom queries on {@link Post} entities. It includes
 * methods for querying posts by user ID, checking the existence of posts,
 * counting posts by user ID, and deleting posts associated with a user.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Finds all posts associated with a specific user ID.
     * <p>
     * This method retrieves a list of {@link Post} entities that are associated
     * with a given user, identified by their {@code userId}.
     * </p>
     *
     * @param userId the ID of the user whose posts are to be retrieved
     * @return an {@link Optional} containing a list of posts for the user,
     *         or an empty {@link Optional} if no posts are found
     */
    Optional<List<Post>> findAllByUserId(Long userId);

    /**
     * Checks if a post exists by its ID.
     * <p>
     * This method checks if a post with the given {@code postId} exists in the
     * database.
     * </p>
     *
     * @param postId the ID of the post to be checked
     * @return {@code true} if the post exists, {@code false} otherwise
     */
    boolean existsById(Long postId);

    /**
     * Counts the number of posts created by a specific user.
     * <p>
     * This method returns the number of posts associated with a given user,
     * identified by their {@code userId}.
     * </p>
     *
     * @param userId the ID of the user whose posts are to be counted
     * @return the number of posts created by the user
     */
    Integer countByUserId(Long userId);

    /**
     * Deletes all posts associated with a specific user.
     * <p>
     * This method removes all {@link Post} entities from the database that are
     * associated with a given user, identified by their {@code userId}.
     * </p>
     *
     * @param userId the ID of the user whose posts are to be deleted
     */
    void deleteAllByUserId(Long userId);
}
