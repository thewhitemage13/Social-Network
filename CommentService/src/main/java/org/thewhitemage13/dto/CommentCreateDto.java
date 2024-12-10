package org.thewhitemage13.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a new comment.
 * <p>
 * This class encapsulates the data required to create a comment, including the post ID,
 * the user ID of the comment's author, and the content of the comment.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Contains fields for the target post, author, and comment content.</li>
 *     <li>Uses Lombok annotations to generate boilerplate code such as getters, setters,
 *         constructors, and more.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDto {


    /**
     * The unique identifier of the post to which this comment belongs.
     */
    private Long postId;

    /**
     * The unique identifier of the user who created the comment.
     */
    private Long userId;

    /**
     * The textual content of the comment.
     */
    private String content;
}
