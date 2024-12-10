package org.thewhitemage13.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a "like" entity in the system.
 * <p>
 * This class is a JPA entity that maps to the "likes" table in the database.
 * It records information about a "like" action, including the user who performed the action,
 * and whether the like is associated with a post or a comment.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports likes on both posts and comments via {@code postId} and {@code commentId} fields.</li>
 *     <li>Tracks the creation timestamp of the like action.</li>
 *     <li>Uses JPA annotations for ORM mapping.</li>
 *     <li>Reduces boilerplate code with Lombok annotations for getters, setters, and constructors.</li>
 * </ul>
 *
 * <h2>Annotations:</h2>
 * <ul>
 *     <li>{@link Entity}: Marks this class as a JPA entity.</li>
 *     <li>{@link Table}: Maps the entity to the "likes" table in the database.</li>
 *     <li>{@link Id}: Specifies the primary key of the entity.</li>
 *     <li>{@link GeneratedValue}: Configures auto-generation of the primary key value.</li>
 *     <li>{@link Column}: Maps fields to database columns and defines constraints.</li>
 *     <li>{@link lombok.Getter}, {@link lombok.Setter}: Automatically generates getters and setters.</li>
 *     <li>{@link lombok.NoArgsConstructor}, {@link lombok.AllArgsConstructor}: Generates constructors for the entity.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
@Entity
public class Like {

    /**
     * Unique identifier for the like action.
     * <p>
     * This field is the primary key of the "likes" table and is auto-generated.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    /**
     * Identifier of the user who performed the like action.
     * <p>
     * This field maps to the "user_id" column in the "likes" table.
     * </p>
     */
    @Column(name = "user_id")
    private Long userId;


    /**
     * Identifier of the post that was liked.
     * <p>
     * This field is optional and maps to the "post_id" column in the "likes" table.
     * If the like is for a comment, this field will be {@code null}.
     * </p>
     */
    @Column(name = "post_id", nullable = true)
    private Long postId;

    /**
     * Identifier of the comment that was liked.
     * <p>
     * This field is optional and maps to the "comment_id" column in the "likes" table.
     * If the like is for a post, this field will be {@code null}.
     * </p>
     */
    @Column(name = "comment_id", nullable = true)
    private Long commentId;

    /**
     * Timestamp indicating when the like action was created.
     * <p>
     * This field stores the date and time of the like action creation.
     * </p>
     */
    private LocalDateTime createdAt;
}
