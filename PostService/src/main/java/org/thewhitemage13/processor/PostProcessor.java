package org.thewhitemage13.processor;

import org.springframework.stereotype.Component;
import org.thewhitemage13.PostEvent;
import org.thewhitemage13.dto.CreatePostDTO;
import org.thewhitemage13.entity.Post;
import org.thewhitemage13.interfaces.PostProcessorInterface;

import java.util.ArrayList;
import java.util.List;


/**
 * Processor responsible for converting {@link Post} entities into {@link CreatePostDTO}
 * and generating {@link PostEvent} for post-related actions.
 * <p>
 * This component handles the transformation of post entities into DTOs for use in
 * the service layer and also prepares post events that encapsulate post details
 * for event-driven workflows such as publishing or deleting posts.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Component
public class PostProcessor implements PostProcessorInterface {

    /**
     * Converts a list of {@link Post} entities to a list of {@link CreatePostDTO}.
     * <p>
     * This method iterates through the provided list of {@code Post} objects and
     * maps each post's attributes (e.g., media URL, content, and user ID) into a
     * {@link CreatePostDTO}. This transformation is necessary for handling post
     * data in a DTO format that can be used by the frontend or other layers of the system.
     * </p>
     *
     * @param posts a list of {@link Post} entities to be converted to DTOs
     * @return a list of {@link CreatePostDTO} containing the post data
     */
    @Override
    public List<CreatePostDTO> getPostDTOS(List<Post> posts) {
        List<CreatePostDTO> createPostDTOS = new ArrayList<>();
        for (Post post : posts) {
            CreatePostDTO createPostDTO = new CreatePostDTO();
            createPostDTO.setMediaUrl(post.getMediaUrl());
            createPostDTO.setContent(post.getContent());
            createPostDTO.setUserId(post.getUserId());
            createPostDTOS.add(createPostDTO);
        }
        return createPostDTOS;
    }

    /**
     * Converts a {@link Post} entity to a {@link PostEvent} for post-related events.
     * <p>
     * This method takes a {@code Post} entity and generates a {@link PostEvent},
     * which contains key details about the post, such as post ID, user ID,
     * content, media URL, and timestamps. The resulting event object is typically
     * used for event-driven architectures to notify other services about post changes
     * or deletions.
     * </p>
     *
     * @param deletePost the {@link Post} entity to be converted into an event
     * @return a {@link PostEvent} containing the relevant post details
     */
    @Override
    public PostEvent getPostEvent(Post deletePost) {
        return new PostEvent
                (
                        deletePost.getPostId(),
                        deletePost.getUserId(),
                        deletePost.getContent(),
                        deletePost.getMediaUrl(),
                        deletePost.getCreatedAt(),
                        deletePost.getUpdatedAt()
                );
    }
}
