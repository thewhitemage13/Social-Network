package org.thewhitemage13.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for updating an existing post.
 * <p>
 * This class encapsulates the data required to update an existing post,
 * including the post content and an optional media URL.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Validates user-provided data for updating posts.</li>
 *     <li>Ensures that content is not blank and adheres to size constraints.</li>
 *     <li>Limits the size of the media URL to prevent overly large inputs.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UpdatePostDTO {

    /**
     * The updated content of the post.
     * <p>
     * This field is mandatory and must not exceed 500 characters.
     * </p>
     */
    @NotBlank(message = "Content cannot be blank")
    @Size(max = 500, message = "Content must not exceed 500 characters")
    private String content;

    /**
     * The updated media URL associated with the post, if any.
     * <p>
     * If provided, the media URL must not exceed 255 characters.
     * </p>
     */
    @Size(max = 255, message = "Media URL must not exceed 255 characters")
    private String mediaUrl;

    /**
     * Returns a string representation of the UpdatePostDTO.
     * <p>
     * Includes content and media URL in a formatted string.
     * </p>
     *
     * @return a string representation of this DTO
     */
    @Override
    public String toString() {
        return "UpdatePostDTO{" +
                "content='" + content + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                '}';
    }
}
