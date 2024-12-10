package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.Comment;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for managing {@link Comment} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * for {@link Comment} entities, along with custom query methods for operations
 * related to comments, posts, and users. It allows for efficient querying and
 * modification of the underlying data store, specifically targeting comments
 * associated with posts and users.
 * </p>
 *
 * <h2>Key Methods:</h2>
 * <ul>
 *     <li>Find all comments by post ID.</li>
 *     <li>Count the number of comments for a specific post.</li>
 *     <li>Delete all comments by user ID.</li>
 *     <li>Delete all comments by post ID.</li>
 *     <li>Find all comments by post ID.</li>
 *     <li>Find all comments by user ID.</li>
 * </ul>
 *
 * <h2>Usage:</h2>
 * <p>
 * This repository is automatically injected by Spring and is used for interacting
 * with the {@link Comment} entity in the database. It provides both standard CRUD
 * operations and custom queries tailored to the needs of the comment management
 * functionality.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieves a list of comments associated with a specific post ID.
     * <p>
     * This method returns all comments that are linked to the specified post ID.
     * It is useful for fetching comments for a particular post.
     * </p>
     *
     * @param id the ID of the post for which comments are to be fetched
     * @return an {@link Optional} containing the list of comments for the post,
     *         or an empty {@link Optional} if no comments are found
     */
    Optional<List<Comment>> findAllByPostId(Long id);

    /**
     * Counts the number of comments associated with a specific post ID.
     * <p>
     * This method returns the total number of comments that belong to the specified
     * post ID, useful for tracking comment counts for posts.
     * </p>
     *
     * @param id the ID of the post for which the comment count is to be retrieved
     * @return the count of comments associated with the post
     */
    Long countByPostId(Long id);

    /**
     * Deletes all comments made by a specific user ID.
     * <p>
     * This method removes all comments from the database that are associated with
     * the specified user ID. It is useful when a user is deleted or wishes to
     * remove all of their comments.
     * </p>
     *
     * @param userId the ID of the user whose comments are to be deleted
     */
    void deleteAllByUserId(Long userId);

    /**
     * Deletes all comments associated with a specific post ID.
     * <p>
     * This method removes all comments from the database that belong to the specified
     * post ID. It is typically used when a post is deleted.
     * </p>
     *
     * @param postId the ID of the post whose comments are to be deleted
     */
    void deleteAllByPostId(Long postId);

    /**
     * Retrieves a list of comments associated with a specific post ID.
     * <p>
     * This method is similar to {@link #findAllByPostId(Long)}, but it returns
     * a non-optional list of comments. It is useful for fetching comments
     * associated with a post when it's guaranteed that comments exist.
     * </p>
     *
     * @param postId the ID of the post for which comments are to be fetched
     * @return the list of comments associated with the post
     */
    List<Comment> findByPostId(Long postId);

    /**
     * Retrieves a list of comments associated with a specific user ID.
     * <p>
     * This method returns all comments made by the user with the specified user ID.
     * It is useful for fetching comments for a particular user.
     * </p>
     *
     * @param userId the ID of the user for which comments are to be fetched
     * @return the list of comments made by the user
     */
    List<Comment> findByUserId(Long userId);
}
