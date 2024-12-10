package org.thewhitemage13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for creating a like on a post.
 * <p>
 * This class encapsulates the necessary information for associating
 * a "like" with a specific post and user.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Encapsulates the unique identifiers for the user and the post.</li>
 *     <li>Facilitates the transfer of "like" creation data between layers of the application.</li>
 *     <li>Uses Lombok annotations to reduce boilerplate code.</li>
 * </ul>
 *
 * <h2>Annotations:</h2>
 * <ul>
 *     <li>{@link lombok.AllArgsConstructor}: Generates a constructor with parameters for all fields.</li>
 *     <li>{@link lombok.NoArgsConstructor}: Generates a default no-argument constructor.</li>
 *     <li>{@link lombok.Getter}: Generates getter methods for all fields.</li>
 *     <li>{@link lombok.Setter}: Generates setter methods for all fields.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateLikePost {

    /**
     * The unique identifier of the user who liked the post.
     */
    private Long userId;

    /**
     * The unique identifier of the post that was liked.
     */
    private Long postId;
}
