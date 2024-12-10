package org.thewhitemage13.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.LikeEvent;
import org.thewhitemage13.dto.CreateLikeComment;
import org.thewhitemage13.dto.CreateLikePost;
import org.thewhitemage13.entity.Like;
import org.thewhitemage13.exception.LikeNotFoundException;
import org.thewhitemage13.interfaces.LikeServiceInterface;
import org.thewhitemage13.repository.LikeRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service implementation for managing likes on posts and comments.
 * <p>
 * This service handles the creation, deletion, and counting of likes for posts and comments. It interacts with the {@link LikeRepository}
 * to perform CRUD operations on likes, and uses Kafka to send events related to like actions (created, deleted).
 * </p>
 * <p>
 * The service leverages caching to improve performance for like-related queries, using the {@link Cacheable} and {@link CacheEvict} annotations.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Supports liking of posts and comments.</li>
 *     <li>Enables cache eviction for like-related queries.</li>
 *     <li>Uses Kafka for publishing events on like creation and deletion.</li>
 *     <li>Handles batch deletion of likes by post, comment, or user.</li>
 * </ul>
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
@Service
@Transactional
public class LikeServiceImpl implements LikeServiceInterface {
    private final LikeRepository likeRepository;
    private final ValidationServiceImpl validationServiceImpl;
    private final KafkaTemplate<Long, Object> kafkaTemplate;

    /**
     * Constructs a new {@code LikeServiceImpl} with the specified dependencies.
     *
     * @param likeRepository      the repository for managing likes in the database
     * @param validationServiceImpl the validation service for validating like requests
     * @param kafkaTemplate       the Kafka template for sending like-related events
     */
    public LikeServiceImpl(LikeRepository likeRepository, ValidationServiceImpl validationServiceImpl, KafkaTemplate<Long, Object> kafkaTemplate) {
        this.likeRepository = likeRepository;
        this.validationServiceImpl = validationServiceImpl;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Deletes all likes associated with a given comment.
     * <p>
     * This method retrieves all likes for the specified comment and deletes each one, triggering cache eviction
     * for the "postLikes" cache.
     * </p>
     *
     * @param commentId the ID of the comment whose likes are to be deleted
     * @throws LikeNotFoundException if no likes are found for the given comment
     */
    @Override
    @CacheEvict(value = "postLikes", key = "#commentId")
    public void deleteAllByCommentId(Long commentId) throws LikeNotFoundException {
        List<Like> deleteAll = likeRepository.findAllByCommentId(commentId);
        for (Like like : deleteAll) {
            deleteLike(like.getLikeId());
        }
    }

    /**
     * Retrieves the count of likes for a given post, using caching to improve performance.
     *
     * @param postId the ID of the post whose like count is to be retrieved
     * @return the total number of likes for the post
     */
    @Override
    @Cacheable(value = "postLikes", key = "#postId")
    public Long getPostLikeCount(Long postId) {
        return likeRepository.countAllByPostId(postId);
    }

    /**
     * Deletes all likes associated with a given post.
     * <p>
     * This method retrieves all likes for the specified post and deletes each one, triggering cache eviction
     * for all entries related to "postLikes".
     * </p>
     *
     * @param postId the ID of the post whose likes are to be deleted
     * @throws LikeNotFoundException if no likes are found for the given post
     */
    @Override
    @CacheEvict(value = "postLikes", allEntries = true)
    public void deleteAllByPostId(Long postId) throws LikeNotFoundException {
        List<Like> deleteAll = likeRepository.findAllByPostId(postId);
        for (Like like : deleteAll) {
            deleteLike(like.getLikeId());
        }
    }

    /**
     * Deletes all likes associated with a given user.
     * <p>
     * This method retrieves all likes for the specified user and deletes each one, triggering cache eviction
     * for all entries related to "postLikes".
     * </p>
     *
     * @param userId the ID of the user whose likes are to be deleted
     * @throws LikeNotFoundException if no likes are found for the given user
     */
    @Override
    @CacheEvict(value = "postLikes", allEntries = true)
    public void deleteAllByUserId(Long userId) throws LikeNotFoundException {
        List<Like> deleteLike = likeRepository.findAllByUserId(userId);
        for (Like like : deleteLike) {
            deleteLike(like.getLikeId());
        }
    }

    /**
     * Posts a like for a given post, validating the like and publishing an event.
     * <p>
     * This method validates the like request, saves the like entity, and then sends a Kafka event notifying that a like was created.
     * It also triggers cache eviction for the related post's like count.
     * </p>
     *
     * @param createLikePost the data required to create a new like for a post
     */
    @Override
    @CacheEvict(value = "postLikes", key = "#createLikePost.postId", condition = "#createLikePost.postId != null")
    public void postLike(CreateLikePost createLikePost) {
        validationServiceImpl.validatePostLike(createLikePost);

        Like like = new Like();

        like.setPostId(createLikePost.getPostId());
        like.setUserId(createLikePost.getUserId());
        like.setCreatedAt(LocalDateTime.now());

        likeRepository.save(like);

        LikeEvent likeEvent = new LikeEvent
                (
                        like.getLikeId(),
                        like.getUserId(),
                        like.getPostId(),
                        like.getCommentId(),
                        like.getCreatedAt()
                );
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("post.like.created",likeEvent.getLikeId(), likeEvent);
//            return null;
//        });

        kafkaTemplate.send("post.like.created",likeEvent.getLikeId(), likeEvent);
    }

    /**
     * Posts a like for a given comment, validating the like and publishing an event.
     * <p>
     * This method validates the like request, saves the like entity, and then sends a Kafka event notifying that a like was created.
     * It also triggers cache eviction for the related comment's like count.
     * </p>
     *
     * @param createLikeComment the data required to create a new like for a comment
     */
    @Override
    @CacheEvict(value = "postLikes", key = "#createLikeComment.commentId", condition = "#createLikeComment.commentId != null")
    public void commentLike(CreateLikeComment createLikeComment) {
        validationServiceImpl.validateCommentLike(createLikeComment);

        Like like = new Like();

        like.setCommentId(createLikeComment.getCommentId());
        like.setUserId(createLikeComment.getUserId());
        like.setCreatedAt(LocalDateTime.now());

        likeRepository.save(like);

        LikeEvent likeEvent = new LikeEvent
                (
                        like.getLikeId(),
                        like.getUserId(),
                        like.getPostId(),
                        like.getCommentId(),
                        like.getCreatedAt()
                );
//        kafkaTemplate.executeInTransaction(operations -> {
//            operations.send("comment.like.created",likeEvent.getLikeId(), likeEvent);
//            return null;
//        });

        kafkaTemplate.send("comment.like.created",likeEvent.getLikeId(), likeEvent);

    }

    /**
     * Deletes a like by its ID and sends a Kafka event notifying that the like was deleted.
     * <p>
     * This method removes the like entity from the repository and publishes an event to Kafka indicating the like's deletion.
     * It triggers cache eviction for the associated like.
     * </p>
     *
     * @param likeId the ID of the like to be deleted
     * @throws LikeNotFoundException if no like is found for the given ID
     */
    @Override
    @CacheEvict(value = "postLikes", key = "#likeId")
    public void deleteLike(Long likeId) throws LikeNotFoundException {
        Like deleteLike = likeRepository.findById(likeId).orElseThrow(() -> new LikeNotFoundException("Like with id = %s not found".formatted(likeId)));
        likeRepository.delete(deleteLike);

        LikeEvent likeEvent = new LikeEvent
                (
                        deleteLike.getLikeId(),
                        deleteLike.getUserId(),
                        deleteLike.getPostId(),
                        deleteLike.getCommentId(),
                        deleteLike.getCreatedAt()
                );

        if (deleteLike.getPostId() == null)
        {
//            kafkaTemplate.executeInTransaction(operations -> {
//                operations.send("comment.like.deleted", deleteLike.getCommentId(), likeEvent);
//                return null;
//            });

            kafkaTemplate.send("comment.like.deleted", deleteLike.getCommentId(), likeEvent);
        }
        else {
//            kafkaTemplate.executeInTransaction(operations -> {
//                operations.send("post.like.deleted", deleteLike.getCommentId(), likeEvent);
//                return null;
//            });

            kafkaTemplate.send("post.like.deleted", deleteLike.getCommentId(), likeEvent);
        }
    }

    /**
     * Retrieves the sum of likes for a given post.
     * <p>
     * This method counts the total number of likes for a specified post using caching.
     * </p>
     *
     * @param postId the ID of the post whose like sum is to be retrieved
     * @return the total number of likes for the post
     */
    @Override
    @Cacheable(value = "postLikeSum", key = "#postId")
    public Long showPostLikeSum(Long postId) {
        List<Like> postLike = likeRepository.findAllByPostId(postId);
        return (long) postLike.size();
    }

    /**
     * Retrieves the sum of likes for a given comment.
     * <p>
     * This method counts the total number of likes for a specified comment using caching.
     * </p>
     *
     * @param commentId the ID of the comment whose like sum is to be retrieved
     * @return the total number of likes for the comment
     */
    @Override
    @Cacheable(value = "commentLikeSum", key = "#commentId")
    public Long showCommentLikeSum(Long commentId) {
        List<Like> commentLike = likeRepository.findAllByCommentId(commentId);
        return (long) commentLike.size();
    }
}
