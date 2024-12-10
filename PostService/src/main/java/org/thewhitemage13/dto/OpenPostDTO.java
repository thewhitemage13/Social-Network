package org.thewhitemage13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for viewing an open post.
 * <p>
 * This class encapsulates the details required for displaying an open post,
 * including the username of the post's creator, the post content, media URL, likes,
 * and comments count.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Provides metadata such as username and media URL.</li>
 *     <li>Includes engagement metrics like the number of likes and comments.</li>
 *     <li>Facilitates a structured representation of posts for clients.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpenPostDTO {

    /**
     * The username of the person who created the post.
     */
    private String username;

    /**
     * The URL of the media associated with the post, if any.
     */
    private String mediaUrl;

    /**
     * The main content of the post.
     */
    private String content;

    /**
     * The number of likes the post has received.
     */
    private Long likes;

    /**
     * The number of comments on the post.
     */
    private Long comments;

    /**
     * Returns a string representation of the OpenPostDTO.
     * <p>
     * Includes username, media URL, content, likes, and comments in a formatted string.
     * </p>
     *
     * @return a string representation of this DTO
     */
    @Override
    public String toString() {
        return "OpenPostDTO{" +
                "username='" + username + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", comments=" + comments +
                '}';
    }
}
