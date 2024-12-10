package org.thewhitemage13.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for creating a new post.
 * <p>
 * This class is used to encapsulate the data required to create a new post,
 * including the user ID, content, and an optional media URL.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates user-provided data for creating posts.</li>
 *     <li>Ensures mandatory fields such as user ID and content are present.</li>
 *     <li>Limits the size of content and media URLs to prevent overly large inputs.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreatePostDTO {

    /**
     * The unique identifier of the user creating the post.
     * <p>
     * Must be a non-null value greater than or equal to 1.
     * </p>
     */
    @NotNull(message = "User ID cannot be null")
    @Min(value = 1, message = "User ID must be at least 1")
    private Long userId;

    /**
     * The content of the post.
     * <p>
     * This field is mandatory and must not exceed 500 characters.
     * </p>
     */
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 500, message = "Content must not exceed 500 characters")
    private String content;

    /**
     * The optional media URL associated with the post.
     * <p>
     * If provided, the media URL must not exceed 255 characters.
     * </p>
     */
    @Size(max = 255, message = "Media URL must not exceed 255 characters")
    private String mediaUrl;

    /**
     * Returns a string representation of the CreatePostDTO.
     * <p>
     * Includes user ID, content, and media URL in a formatted string.
     * </p>
     *
     * @return a string representation of this DTO
     */
    @Override
    public String toString() {
        return "CreatePostDTO{" +
                "userId=" + userId +
                ", content='" + content + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                '}';
    }
}
