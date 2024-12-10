package org.thewhitemage13;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an event related to a like action in the system.
 * <p>
 * This class contains information about a like event, including the like's unique ID,
 * the user who liked the content, the post or comment that was liked, and the timestamp
 * when the like was created.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks the unique identifier of the like.</li>
 *     <li>Associates the like with a specific user, post, or comment.</li>
 *     <li>Records the timestamp when the like was created.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class LikeEvent implements Serializable {
    private Long likeId;
    private Long userId;
    private Long postId;
    private Long commentId;
    private LocalDateTime createdAt;

    /**
     * Default constructor for LikeEvent.
     */
    public LikeEvent() {
    }

    /**
     * Constructs a new {@code LikeEvent} with the specified parameters.
     *
     * @param likeId the unique identifier for the like
     * @param userId the ID of the user who liked the content
     * @param postId the ID of the post that was liked
     * @param commentId the ID of the comment that was liked
     * @param createdAt the timestamp when the like was created
     */
    public LikeEvent(Long likeId, Long userId, Long postId, Long commentId, LocalDateTime createdAt) {
        this.likeId = likeId;
        this.userId = userId;
        this.postId = postId;
        this.commentId = commentId;
        this.createdAt = createdAt;
    }

    /**
     * Gets the unique identifier for the like.
     *
     * @return the like ID
     */
    public Long getLikeId() {
        return likeId;
    }

    /**
     * Sets the unique identifier for the like.
     *
     * @param likeId the like ID to set
     */
    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    /**
     * Gets the ID of the user who liked the content.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who liked the content.
     *
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the post that was liked.
     *
     * @return the post ID
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * Sets the ID of the post that was liked.
     *
     * @param postId the post ID to set
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * Gets the ID of the comment that was liked.
     *
     * @return the comment ID
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * Sets the ID of the comment that was liked.
     *
     * @param commentId the comment ID to set
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * Gets the timestamp when the like was created.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the like was created.
     *
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns a string representation of the LikeEvent.
     * <p>
     * The string includes the like's ID, user ID, post ID, comment ID, and timestamp.
     * </p>
     *
     * @return a string representation of the like event
     */
    @Override
    public String toString() {
        return "LikeEvent{" +
                "likeId=" + likeId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", commentId=" + commentId +
                ", createdAt=" + createdAt +
                '}';
    }
}
