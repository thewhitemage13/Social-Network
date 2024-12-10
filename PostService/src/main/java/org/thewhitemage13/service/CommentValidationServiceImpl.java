package org.thewhitemage13.service;

import org.springframework.stereotype.Service;
import org.thewhitemage13.clients.CommentClient;
import org.thewhitemage13.interfaces.CommentValidationServiceInterface;

/**
 * Service implementation for validating comments related to posts.
 * <p>
 * This service uses the {@link CommentClient} to interact with an external service
 * for counting the number of comments associated with a post. The service ensures
 * that the number of comments is validated and provides a fallback mechanism in case
 * of errors during the external call.
 * </p>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
public class CommentValidationServiceImpl implements CommentValidationServiceInterface {
    private final CommentClient commentClient;

    /**
     * Constructs a new {@code CommentValidationServiceImpl} with the specified {@link CommentClient}.
     * <p>
     * The constructor injects the {@link CommentClient}, which is used to communicate
     * with the external comment service to retrieve the count of comments for a given post.
     * </p>
     *
     * @param commentClient the {@link CommentClient} used to interact with the comment service
     */
    public CommentValidationServiceImpl(CommentClient commentClient) {
        this.commentClient = commentClient;
    }

    /**
     * Counts the number of comments associated with a specific post.
     * <p>
     * This method communicates with the external comment service via the {@link CommentClient}
     * to retrieve the number of comments for the post identified by {@code postId}. If the
     * external service call fails, it returns 0 as a fallback.
     * </p>
     *
     * @param postId the ID of the post whose comments are to be counted
     * @return the number of comments for the post, or 0 if an error occurs during the service call
     */
    @Override
    public Long countCommentValidation(Long postId) {
        Long comments;
        try {
            comments = commentClient.getCommentCountByPostId(postId).getBody();
        } catch (Exception e) {
            comments = 0L;
        }
        return comments;
    }
}
