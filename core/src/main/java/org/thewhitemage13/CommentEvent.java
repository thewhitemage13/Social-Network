package org.thewhitemage13;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an event related to a comment in the system.
 * <p>
 * This class contains details about a comment event, including the comment's unique ID,
 * the post it is associated with, the user who created or updated the comment, the content
 * of the comment, and timestamps for when the comment was created and last updated.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks the unique identifier of the comment.</li>
 *     <li>Associates the comment with a specific post and user.</li>
 *     <li>Records the content of the comment and timestamps for creation and updates.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class CommentEvent implements Serializable {
    private Long commentId;
    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Default constructor for CommentEvent.
     */
    public CommentEvent() {
    }

    /**
     * Constructs a new {@code CommentEvent} with the specified parameters.
     *
     * @param commentId the unique identifier for the comment
     * @param postId the ID of the post the comment is associated with
     * @param userId the ID of the user who created or updated the comment
     * @param content the content of the comment
     * @param createdAt the timestamp when the comment was created
     * @param updatedAt the timestamp when the comment was last updated
     */
    public CommentEvent(Long commentId, Long postId, Long userId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the unique identifier for the comment.
     *
     * @return the comment ID
     */
    public Long getCommentId() {
        return commentId;
    }

    /**
     * Sets the unique identifier for the comment.
     *
     * @param commentId the comment ID to set
     */
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    /**
     * Gets the ID of the post associated with the comment.
     *
     * @return the post ID
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * Sets the ID of the post associated with the comment.
     *
     * @param postId the post ID to set
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * Gets the ID of the user who created or updated the comment.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who created or updated the comment.
     *
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the content of the comment.
     *
     * @return the comment content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the comment.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the timestamp when the comment was created.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the comment was created.
     *
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp when the comment was last updated.
     *
     * @return the update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the timestamp when the comment was last updated.
     *
     * @param updatedAt the update timestamp to set
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns a string representation of the CommentEvent.
     * <p>
     * The string includes the comment's ID, post ID, user ID, content, and timestamps.
     * </p>
     *
     * @return a string representation of the comment event
     */
    @Override
    public String toString() {
        return "CommentEvent{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
