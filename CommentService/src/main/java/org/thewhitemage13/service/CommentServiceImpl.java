package org.thewhitemage13.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.CommentEvent;
import org.thewhitemage13.dto.CommentCreateDto;
import org.thewhitemage13.entity.Comment;
import org.thewhitemage13.exceptions.CommentNotFoundException;
import org.thewhitemage13.interfaces.CommentServiceInterface;
import org.thewhitemage13.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the {@link CommentServiceInterface} that provides services related to handling comments.
 * <p>
 * This service handles CRUD operations on comments, including adding, updating, deleting, and retrieving comments.
 * It also interacts with the Kafka message broker to send events when comments are created, updated, or deleted.
 * </p>
 * <p>
 * Additionally, the service utilizes caching mechanisms to optimize the retrieval of comments and comment counts.
 * </p>
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Creates, updates, and deletes comments, while synchronizing with Kafka for event-driven architecture.</li>
 *     <li>Caches comment data to reduce database load.</li>
 *     <li>Performs validation of posts and users before adding or updating comments.</li>
 *     <li>Deletes all comments related to a specific user or post.</li>
 *     <li>Handles comment existence verification.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentServiceInterface {
    private final CommentRepository commentRepository;
    private final ValidationServiceImpl validationServiceImpl;
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    /**
     * Constructs a new {@code CommentServiceImpl} with the specified dependencies.
     *
     * @param commentRepository the repository used to interact with the comments database
     * @param validationServiceImpl the service used for validating posts and users
     * @param kafkaTemplate the Kafka template used for sending events
     */
    public CommentServiceImpl
            (
                    CommentRepository commentRepository,
                    ValidationServiceImpl validationServiceImpl,
                    KafkaTemplate<Long, Object> kafkaTemplate
            ) {
        this.commentRepository = commentRepository;
        this.validationServiceImpl = validationServiceImpl;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Retrieves the user ID associated with a specific comment ID.
     * <p>
     * This method utilizes caching to improve performance. The result is cached using the comment ID as the key.
     * </p>
     *
     * @param commentId the ID of the comment whose user ID is to be retrieved
     * @return the user ID associated with the given comment ID
     */
    @Override
    @Cacheable(value = "comments", key = "#commentId")
    public Long getUserIdByCommentId(Long commentId) {
        return commentRepository.findById(commentId).get().getUserId();
    }

    /**
     * Deletes all comments associated with a specific post ID.
     * <p>
     * This method retrieves all comments for the given post and deletes them one by one, using the {@link #deleteComment(Long)} method.
     * </p>
     * <p>
     * This method evicts the cache to ensure that the comment data is refreshed after the deletion.
     * </p>
     *
     * @param postId the ID of the post whose comments are to be deleted
     * @throws CommentNotFoundException if no comments are found for the specified post ID
     */
    @Override
    @CacheEvict(value = "comments", allEntries = true)
    public void deleteAllByPostId(Long postId) throws CommentNotFoundException {
        List<Comment> deleteAll = commentRepository.findByPostId(postId);

        for (Comment comment : deleteAll) {
            deleteComment(comment.getCommentId());
        }
    }

    /**
     * Retrieves the count of comments associated with a specific post ID.
     * <p>
     * This method uses caching to store the count of comments for the given post ID.
     * </p>
     *
     * @param postId the ID of the post whose comment count is to be retrieved
     * @return the number of comments for the specified post ID
     */
    @Override
    @Cacheable(value = "commentsCount", key = "#postId")
    public Long getCountOfCommentsByPostId(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    /**
     * Deletes all comments associated with a specific user ID.
     * <p>
     * This method retrieves all comments for the given user and deletes them one by one, using the {@link #deleteComment(Long)} method.
     * </p>
     * <p>
     * This method evicts the cache to ensure that the comment data is refreshed after the deletion.
     * </p>
     *
     * @param userId the ID of the user whose comments are to be deleted
     * @throws CommentNotFoundException if no comments are found for the specified user ID
     */
    @Override
    @CacheEvict(value = "comments", allEntries = true)
    public void deleteAllByUserId(Long userId) throws CommentNotFoundException {
        List<Comment> deleteAll = commentRepository.findByUserId(userId);

        for (Comment comment : deleteAll) {
            deleteComment(comment.getCommentId());
        }
    }

    /**
     * Verifies if a comment with the specified ID exists.
     * <p>
     * This method checks the existence of a comment in the database using its ID.
     * </p>
     *
     * @param commentId the ID of the comment to verify
     * @return {@code true} if the comment exists; {@code false} otherwise
     */
    @Override
    public boolean commentVerification(Long commentId) {
        return commentRepository.existsById(commentId);
    }

    /**
     * Adds a new comment to the system.
     * <p>
     * This method validates the post and user before creating a new comment. The comment is then saved to the database
     * and an event is sent to Kafka indicating that a comment has been created.
     * </p>
     * <p>
     * This method evicts the cache to ensure that the comment data is refreshed.
     * </p>
     *
     * @param commentCreateDto the data transfer object containing the information for the new comment
     */
    @Override
    @CacheEvict(value = "comments", allEntries = true)
    public void addComment(CommentCreateDto commentCreateDto) {
        validationServiceImpl.validatePost(commentCreateDto);
        validationServiceImpl.validateUser(commentCreateDto);

        Comment comment = new Comment();

        comment.setPostId(commentCreateDto.getPostId());
        comment.setContent(commentCreateDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUserId(commentCreateDto.getUserId());

        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);

        CommentEvent commentEvent = new CommentEvent
                (
                        comment.getCommentId(),
                        comment.getPostId(),
                        comment.getUserId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                );
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("comment.created", comment.getCommentId(), commentEvent);
//            return null;
//        });

        kafkaTemplate.send("comment.created", comment.getCommentId(), commentEvent);
    }

    /**
     * Updates an existing comment identified by its ID.
     * <p>
     * This method updates the content of the comment and the updated timestamp, saving the changes to the database.
     * An event is then sent to Kafka to indicate that a comment has been updated.
     * </p>
     * <p>
     * This method puts the updated comment in the cache to reflect the changes.
     * </p>
     *
     * @param commentId the ID of the comment to be updated
     * @param commentCreateDto the data transfer object containing the updated comment information
     * @throws CommentNotFoundException if the comment with the specified ID is not found
     */
    @Override
    @CachePut(value = "comments", key = "#commentId")
    public void updateComment(Long commentId ,CommentCreateDto commentCreateDto) throws CommentNotFoundException {
        Comment update = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id = %s not found".formatted(commentId)));

        update.setUpdatedAt(LocalDateTime.now());
        update.setContent(commentCreateDto.getContent());

        commentRepository.save(update);

        CommentEvent commentEvent = new CommentEvent
                (
                        update.getCommentId(),
                        update.getPostId(),
                        update.getUserId(),
                        update.getContent(),
                        update.getCreatedAt(),
                        update.getUpdatedAt()
                );
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("comment.updated", update.getCommentId(), commentEvent);
//            return null;
//        });

        kafkaTemplate.send("comment.updated", update.getCommentId(), commentEvent);
    }

    /**
     * Deletes a comment identified by its ID.
     * <p>
     * This method deletes the comment from the database and sends an event to Kafka indicating that the comment has been deleted.
     * </p>
     * <p>
     * This method evicts the comment from the cache to ensure the data is updated.
     * </p>
     *
     * @param commentId the ID of the comment to be deleted
     * @throws CommentNotFoundException if the comment with the specified ID is not found
     */
    @Override
    @CacheEvict(value = "comments", key = "#commentId")
    public void deleteComment(Long commentId) throws CommentNotFoundException {
        Comment deleteComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id = %s not found".formatted(commentId)));
        commentRepository.delete(deleteComment);

        CommentEvent commentEvent = new CommentEvent
                (
                        deleteComment.getCommentId(),
                        deleteComment.getPostId(),
                        deleteComment.getUserId(),
                        deleteComment.getContent(),
                        deleteComment.getCreatedAt(),
                        deleteComment.getUpdatedAt()
                );
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("comment.deleted", deleteComment.getCommentId(), commentEvent);
//            return null;
//        });

        kafkaTemplate.send("comment.deleted", deleteComment.getCommentId(), commentEvent);
    }

    /**
     * Retrieves all comments associated with a specific post ID.
     * <p>
     * This method retrieves the list of comments for the given post ID, or throws an exception if no comments are found.
     * </p>
     * <p>
     * The result is cached to optimize subsequent retrievals.
     * </p>
     *
     * @param postId the ID of the post whose comments are to be retrieved
     * @return a list of comments associated with the specified post ID
     * @throws CommentNotFoundException if no comments are found for the specified post ID
     */
    @Override
    @Cacheable(value = "comments", key = "#postId")
    public List<Comment> getAllByPostId(Long postId) throws CommentNotFoundException {
        return commentRepository.findAllByPostId(postId)
                .orElseThrow(() -> new CommentNotFoundException("Comment with post id = %s not found"
                        .formatted(postId)));
    }
}
