package org.thewhitemage13.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for exposing public user details.
 * <p>
 * This class encapsulates the public-facing user information, such as username,
 * profile picture, post statistics, follower/following counts, and a list of media URLs.
 * It is used in scenarios where limited user details need to be shared,
 * such as in public profiles or when fetching summary data.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Includes basic user details like username and profile picture URL.</li>
 *     <li>Provides counts for posts, followers, and following.</li>
 *     <li>Contains a list of URLs for media posts associated with the user.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
public class OpenUserDTO implements Serializable {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The URL of the user's profile picture.
     * <p>
     * This field is expected to contain a valid HTTP or HTTPS URL.
     * </p>
     */
    private String profilePictureUrl;


    /**
     * The total number of posts made by the user.
     */
    private Long countPosts;

    /**
     * The total number of users the user is following.
     */
    private Long countFollowing;

    /**
     * The total number of followers the user has.
     */
    private Long countFollowers;

    /**
     * A list of URLs for media posts associated with the user.
     * <p>
     * This list contains links to media files (e.g., images, videos) uploaded by the user.
     * </p>
     */
    private List<String> mediaPostsUrl = new ArrayList<>();

    /**
     * Constructs a new {@code OpenUserDTO} with the specified details.
     *
     * @param username          the username of the user
     * @param countFollowers    the number of followers the user has
     * @param countPosts        the total number of posts made by the user
     * @param countFollowing    the total number of users the user is following
     * @param mediaPostsUrl     a list of media post URLs associated with the user
     * @param profilePictureUrl the URL of the user's profile picture
     */
    public OpenUserDTO(String username, Long countFollowers, Long countPosts, Long countFollowing, List<String> mediaPostsUrl, String profilePictureUrl) {
        this.username = username;
        this.countFollowers = countFollowers;
        this.countPosts = countPosts;
        this.countFollowing = countFollowing;
        this.mediaPostsUrl = mediaPostsUrl;
        this.profilePictureUrl = profilePictureUrl;
    }

    /**
     * Provides a string representation of the OpenUserDTO object.
     * <p>
     * This representation includes key user details for logging and debugging purposes.
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "OpenUserDTO{" +
                "username='" + username + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", countPosts=" + countPosts +
                ", countFollowing=" + countFollowing +
                ", countFollowers=" + countFollowers +
                ", mediaPostsUrl=" + mediaPostsUrl +
                '}';
    }
}
