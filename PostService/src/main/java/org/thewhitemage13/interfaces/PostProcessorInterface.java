package org.thewhitemage13.interfaces;

import org.thewhitemage13.PostEvent;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.entity.Post;

import java.util.List;

/**
 * Interface for processing posts and converting them to different representations.
 * <p>
 * This interface defines methods for converting a list of {@link Post} entities
 * into a list of {@link CreatePostDTO} data transfer objects and for generating
 * {@link PostEvent} instances based on a {@link Post}. These methods are crucial
 * for transforming post-related data into the formats required for various
 * operations, such as event publishing or API responses.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface PostProcessorInterface {

    /**
     * Converts a list of {@link Post} entities into a list of {@link CreatePostDTO} objects.
     * <p>
     * This method processes a list of {@link Post} objects and maps each one to a
     * {@link CreatePostDTO}, which is a data transfer object used to transfer post data
     * in a simplified and standardized manner.
     * </p>
     *
     * @param posts the list of {@link Post} entities to be converted
     * @return a list of {@link CreatePostDTO} objects representing the posts
     */
    List<CreatePostDTO> getPostDTOS(List<Post> posts);

    /**
     * Generates a {@link PostEvent} for a given post to be deleted.
     * <p>
     * This method creates a {@link PostEvent} instance that encapsulates the details
     * of the specified {@link Post} that is being deleted. The event can be used for
     * publishing or triggering necessary actions related to the post deletion.
     * </p>
     *
     * @param deletePost the {@link Post} entity to be deleted
     * @return a {@link PostEvent} representing the event of deleting the post
     */
    PostEvent getPostEvent(Post deletePost);
}
