package org.thewhitemage13;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an event related to posts in the system.
 * <p>
 * This class encapsulates details about posts, including the post's unique ID, the user
 * who created it, the post content, any associated media URL, and timestamps for when the
 * post was created and last updated.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Tracks the unique identifier for the post.</li>
 *     <li>Associates the post with the user who created it.</li>
 *     <li>Stores the content of the post.</li>
 *     <li>Contains a URL for any associated media.</li>
 *     <li>Records timestamps for when the post was created and last updated.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public class PostEvent implements Serializable {
    private Long postId;
    private Long userId;
    private String content;
    private String mediaUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Default constructor for PostEvent.
     */
    public PostEvent() {
    }

    /**
     * Constructs a new {@code PostEvent} with the specified parameters.
     *
     * @param postId the unique identifier for the post
     * @param userId the ID of the user who created the post
     * @param content the content of the post
     * @param mediaUrl the URL of the associated media (optional)
     * @param createdAt the timestamp when the post was created
     * @param updatedAt the timestamp when the post was last updated
     */
    public PostEvent(Long postId, Long userId, String content, String mediaUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Gets the unique identifier for the post.
     *
     * @return the post ID
     */
    public Long getPostId() {
        return postId;
    }

    /**
     * Sets the unique identifier for the post.
     *
     * @param postId the post ID to set
     */
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    /**
     * Gets the ID of the user who created the post.
     *
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who created the post.
     *
     * @param userId the user ID to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the content of the post.
     *
     * @return the content of the post
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the post.
     *
     * @param content the content of the post to set
     */
    public void setContent(String content) {
        this.content = content;
    }


    /**
     * Gets the URL of the associated media (if any).
     *
     * @return the media URL
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * Sets the URL of the associated media.
     *
     * @param mediaUrl the media URL to set
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    /**
     * Gets the timestamp when the post was created.
     *
     * @return the creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp when the post was created.
     *
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp when the post was last updated.
     *
     * @return the update timestamp
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the timestamp when the post was last updated.
     *
     * @param updatedAt the update timestamp to set
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns a string representation of the PostEvent.
     * <p>
     * The string includes the post's ID, user ID, content, media URL, and timestamps for
     * creation and update.
     * </p>
     *
     * @return a string representation of the post event
     */
    @Override
    public String toString() {
        return "PostEvent{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
