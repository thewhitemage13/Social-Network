package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.OpenPostDTO;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.dto.UpdatePostDTO;
import org.thewhitemage13.exceptions.PostNotFoundException;

import java.util.List;

/**
 * Interface for managing posts in the system.
 * <p>
 * This interface defines the core operations for managing posts, including creating,
 * updating, deleting, and retrieving posts. It also includes methods for verifying
 * post existence, retrieving post URLs, and counting posts for a specific user.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PostServiceInterface {


    /**
     * Verifies whether a post exists based on its ID.
     * <p>
     * This method checks if a post with the given {@code postId} exists in the system.
     * It returns {@code true} if the post is found, otherwise {@code false}.
     * </p>
     *
     * @param postId the ID of the post to be verified
     * @return {@code true} if the post exists, {@code false} otherwise
     */
    boolean postVerification(Long postId);

    /**
     * Creates a new post based on the provided {@link CreatePostDTO}.
     * <p>
     * This method processes the data in the {@link CreatePostDTO} and creates
     * a new post in the system.
     * </p>
     *
     * @param createPostDTO the data transfer object containing the post details
     */
    void createPost(CreatePostDTO createPostDTO);

    /**
     * Updates an existing post for the given user.
     * <p>
     * This method updates an existing post based on the {@link UpdatePostDTO}.
     * It throws a {@link PostNotFoundException} if the post to be updated is not found.
     * </p>
     *
     * @param userId the ID of the user who owns the post
     * @param updatePostDTO the data transfer object containing the updated post details
     * @throws PostNotFoundException if the post to be updated is not found
     */
    void updatePost(Long userId, UpdatePostDTO updatePostDTO) throws PostNotFoundException;

    /**
     * Deletes a post for the given user.
     * <p>
     * This method deletes the post associated with the {@code userId}. If the
     * post does not exist, a {@link PostNotFoundException} will be thrown.
     * </p>
     *
     * @param userId the ID of the user who owns the post
     * @throws PostNotFoundException if the post to be deleted is not found
     */
    void deletePost(Long userId) throws PostNotFoundException;

    /**
     * Retrieves all posts associated with a specific user.
     * <p>
     * This method returns a list of {@link CreatePostDTO} objects representing
     * the posts of the user with the specified {@code userId}. It throws
     * a {@link PostNotFoundException} if no posts are found.
     * </p>
     *
     * @param userId the ID of the user whose posts are to be retrieved
     * @return a list of {@link CreatePostDTO} objects representing the user's posts
     * @throws PostNotFoundException if no posts are found for the user
     */
    List<CreatePostDTO> getPostsByUserId(Long userId) throws PostNotFoundException;

    /**
     * Retrieves all posts in the system.
     * <p>
     * This method returns a list of {@link CreatePostDTO} objects representing
     * all posts in the system.
     * </p>
     *
     * @return a list of {@link CreatePostDTO} objects representing all posts
     */
    List<CreatePostDTO> getAllPosts();

    /**
     * Retrieves the user ID associated with a specific post.
     * <p>
     * This method returns the user ID of the owner of the post with the specified {@code postId}.
     * </p>
     *
     * @param postId the ID of the post whose user ID is to be retrieved
     * @return the user ID of the post owner
     */
    Long getUserIdByPostId(Long postId);


    /**
     * Deletes all posts associated with a specific user.
     * <p>
     * This method deletes all posts for the user with the given {@code userId}.
     * It throws a {@link PostNotFoundException} if no posts are found for the user.
     * </p>
     *
     * @param userId the ID of the user whose posts are to be deleted
     * @throws PostNotFoundException if no posts are found for the user
     */
    void deleteAllByUserId(Long userId) throws PostNotFoundException;

    /**
     * Retrieves all URLs associated with the posts of a specific user.
     * <p>
     * This method returns a list of URLs associated with the posts of the
     * user identified by {@code userId}.
     * </p>
     *
     * @param userId the ID of the user whose post URLs are to be retrieved
     * @return a list of URLs associated with the user's posts
     */
    List<String> getUrlsByUserId(Long userId);

    /**
     * Counts the number of posts created by a specific user.
     * <p>
     * This method returns the total number of posts created by the user with
     * the specified {@code userId}.
     * </p>
     *
     * @param userId the ID of the user whose post count is to be retrieved
     * @return the number of posts created by the user
     */
    Integer getCountPostByUserId(Long userId);

    /**
     * Retrieves all open posts associated with a specific user.
     * <p>
     * This method returns a list of {@link OpenPostDTO} objects representing
     * the posts that are open or visible for the user identified by {@code userId}.
     * </p>
     *
     * @param userId the ID of the user whose open posts are to be retrieved
     * @return a list of {@link OpenPostDTO} objects representing the user's open posts
     */
    List<OpenPostDTO> openAllPostsByUserId(Long userId);

    /**
     * Retrieves a specific open post based on its post ID.
     * <p>
     * This method returns an {@link OpenPostDTO} representing the open post
     * with the specified {@code postId}. It throws a {@link PostNotFoundException}
     * if the post is not found.
     * </p>
     *
     * @param postId the ID of the post to be retrieved
     * @return an {@link OpenPostDTO} representing the open post
     * @throws PostNotFoundException if the post with the specified ID is not found
     */
    OpenPostDTO openPost(Long postId) throws PostNotFoundException;
}
