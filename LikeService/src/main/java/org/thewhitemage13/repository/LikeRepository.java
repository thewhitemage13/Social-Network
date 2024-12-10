package org.thewhitemage13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thewhitemage13.entity.Like;

import java.util.List;

/**
 * Repository interface for managing {@link Like} entities in the database.
 * <p>
 * This interface extends {@link JpaRepository} and provides various methods for interacting
 * with the "likes" table, including retrieving, counting, and deleting likes associated with
 * posts, comments, and users.
 * </p>
 * <p>
 * All repository methods are derived from JPA repository conventions, allowing for efficient
 * database operations with minimal boilerplate code.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Find all likes by a specific post ID or comment ID.</li>
 *     <li>Count the number of likes on a specific post.</li>
 *     <li>Delete likes by user ID, post ID, or comment ID.</li>
 *     <li>Find all likes for a specific user.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * LikeRepository likeRepository = new LikeRepositoryImpl();
 * List<Like> likes = likeRepository.findAllByPostId(postId);
 * Long likeCount = likeRepository.countAllByPostId(postId);
 * likeRepository.deleteAllByUserId(userId);
 * }</pre>
 *
 * @see Like
 * @see JpaRepository
 * @see org.springframework.stereotype.Repository
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * Finds all likes associated with a specific post.
     * <p>
     * This method retrieves all {@link Like} entities where the {@code postId} matches the
     * provided value.
     * </p>
     *
     * @param postId the ID of the post to find likes for
     * @return a list of {@link Like} entities associated with the given post
     */
    List<Like> findAllByPostId(Long postId);

    /**
     * Finds all likes associated with a specific comment.
     * <p>
     * This method retrieves all {@link Like} entities where the {@code commentId} matches the
     * provided value.
     * </p>
     *
     * @param commentId the ID of the comment to find likes for
     * @return a list of {@link Like} entities associated with the given comment
     */
    List<Like> findAllByCommentId(Long commentId);

    /**
     * Counts the number of likes associated with a specific post.
     * <p>
     * This method returns the total number of likes for the specified post ID.
     * </p>
     *
     * @param postId the ID of the post to count likes for
     * @return the number of likes for the given post
     */
    Long countAllByPostId(Long postId);

    /**
     * Deletes all likes associated with a specific user.
     * <p>
     * This method removes all {@link Like} entities where the {@code userId} matches the
     * provided value.
     * </p>
     *
     * @param userId the ID of the user whose likes are to be deleted
     */
    void deleteAllByUserId(Long userId);

    /**
     * Deletes all likes associated with a specific post.
     * <p>
     * This method removes all {@link Like} entities where the {@code postId} matches the
     * provided value.
     * </p>
     *
     * @param postId the ID of the post whose likes are to be deleted
     */
    void deleteAllByPostId(Long postId);

    /**
     * Deletes all likes associated with a specific comment.
     * <p>
     * This method removes all {@link Like} entities where the {@code commentId} matches the
     * provided value.
     * </p>
     *
     * @param commentId the ID of the comment whose likes are to be deleted
     */
    void deleteAllByCommentId(Long commentId);

    /**
     * Finds all likes associated with a specific user.
     * <p>
     * This method retrieves all {@link Like} entities where the {@code userId} matches the
     * provided value.
     * </p>
     *
     * @param userId the ID of the user to find likes for
     * @return a list of {@link Like} entities associated with the given user
     */
    List<Like> findAllByUserId(Long userId);
}
