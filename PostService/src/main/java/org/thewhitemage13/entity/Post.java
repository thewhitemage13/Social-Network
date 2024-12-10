package org.thewhitemage13.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity class representing a Post in the system.
 * <p>
 * This class is a JPA entity that maps to the "posts" table in the database.
 * It stores key information about a post, including its ID, the user ID of the author,
 * the content, associated media, and timestamps for creation and updates.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identification of posts with auto-generated IDs.</li>
 *     <li>Association of posts with specific users via user IDs.</li>
 *     <li>Support for optional media URLs linked to posts.</li>
 *     <li>Automatic tracking of creation and update timestamps.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
@Entity
public class Post {

    /**
     * Unique identifier for the post.
     * <p>
     * This field is the primary key in the "posts" table and is automatically
     * generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    /**
     * Identifier of the user who created the post.
     * <p>
     * This is a foreign key mapping to the "user_id" column in the "posts" table.
     * </p>
     */
    @Column(nullable = false, name = "user_id")
    private Long userId;

    /**
     * The main content of the post.
     */
    private String content;

    /**
     * The URL of the media associated with the post, if any.
     */
    @Column(name = "media_url")
    private String mediaUrl;

    /**
     * The timestamp when the post was created.
     * <p>
     * Automatically set when a new post is created.
     * </p>
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the post was last updated.
     * <p>
     * Updated whenever the post's content or media is modified.
     * </p>
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Constructs a new {@code Post} with the specified user ID, content, media URL, and creation timestamp.
     *
     * @param userId    the unique identifier of the user who created the post
     * @param content   the content of the post
     * @param mediaUrl  the URL of the associated media, if any
     * @param createdAt the timestamp of when the post was created
     */
    public Post( Long userId, String content, String mediaUrl, LocalDateTime createdAt) {
        this.userId = userId;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.createdAt = createdAt;
    }

    /**
     * Returns a string representation of the Post entity.
     * <p>
     * Includes the post ID, user ID, content, media URL, and timestamps in a formatted string.
     * </p>
     *
     * @return a string representation of this entity
     */
    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
