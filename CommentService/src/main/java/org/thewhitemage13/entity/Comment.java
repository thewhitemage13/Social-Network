package org.thewhitemage13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a comment entity in the system.
 * <p>
 * This class is a JPA entity that maps to the "comments" table in the database.
 * It stores key information about a comment, including its unique identifier,
 * the post it belongs to, the user who created it, the content, and timestamps
 * for when it was created and last updated.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Unique identifier for each comment.</li>
 *     <li>References to the post and user associated with the comment.</li>
 *     <li>Stores the content of the comment.</li>
 *     <li>Keeps track of creation and last update timestamps.</li>
 *     <li>Uses JPA annotations for database mapping and Lombok annotations
 *         for reducing boilerplate code.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@Entity
public class Comment {

    /**
     * Unique identifier for the comment.
     * <p>
     * This field is the primary key and is auto-generated using the IDENTITY strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    /**
     * The unique identifier of the post to which this comment belongs.
     * <p>
     * Maps to the "post_id" column in the "comments" table.
     * </p>
     */
    @Column(nullable = false, name = "post_id")
    private Long postId;

    /**
     * The unique identifier of the user who created this comment.
     * <p>
     * Maps to the "user_id" column in the "comments" table.
     * </p>
     */
    @Column(nullable = false, name = "user_id")
    private Long userId;


    /**
     * The textual content of the comment.
     */
    private String content;

    /**
     * The timestamp indicating when the comment was created.
     * <p>
     * Maps to the "created_at" column in the "comments" table.
     * </p>
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating when the comment was last updated.
     * <p>
     * Maps to the "updated_at" column in the "comments" table.
     * </p>
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Returns a string representation of the {@code Comment} entity.
     * <p>
     * Includes all the fields such as {@code commentId}, {@code postId}, {@code userId},
     * {@code content}, {@code createdAt}, and {@code updatedAt}.
     * </p>
     *
     * @return a string representation of the comment
     */
    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
