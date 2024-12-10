package org.thewhitemage13.interfaces;

import org.thewhitemage13.dto.CreateLikeComment;
import org.thewhitemage13.dto.CreateLikePost;
import org.thewhitemage13.exceptions.LikeNotFoundException;

/**
 * Interface that defines the contract for the like service operations.
 * <p>
 * This interface provides methods to manage and track "likes" for posts and comments,
 * as well as operations to delete likes associated with posts, comments, and users.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Methods for deleting all likes associated with a post, comment, or user.</li>
 *     <li>Methods for counting and showing like sums for posts and comments.</li>
 *     <li>Methods to add likes for posts and comments.</li>
 *     <li>Support for handling "like" removal and retrieval operations.</li>
 * </ul>
 *
 * <h2>Usage Example:</h2>
 * <pre>{@code
 * LikeServiceInterface likeService = new LikeServiceImpl();
 * likeService.postLike(new CreateLikePost(userId, postId));
 * }</pre>
 *
 * @see CreateLikePost
 * @see CreateLikeComment
 * @see LikeNotFoundException
 *
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface LikeServiceInterface {

     /**
      * Deletes all likes associated with a specific comment.
      * <p>
      * This method removes all likes that are linked to a given comment ID.
      * </p>
      *
      * @param commentId the ID of the comment for which all likes will be deleted
      * @throws LikeNotFoundException if no likes are found for the specified comment
      */
     void deleteAllByCommentId(Long commentId) throws LikeNotFoundException;

     /**
      * Retrieves the total number of likes associated with a specific post.
      * <p>
      * This method returns the count of likes for a given post ID.
      * </p>
      *
      * @param postId the ID of the post for which the like count will be retrieved
      * @return the total number of likes associated with the specified post
      */
     Long getPostLikeCount(Long postId);

     /**
      * Deletes all likes associated with a specific post.
      * <p>
      * This method removes all likes that are linked to a given post ID.
      * </p>
      *
      * @param postId the ID of the post for which all likes will be deleted
      * @throws LikeNotFoundException if no likes are found for the specified post
      */
     void deleteAllByPostId(Long postId) throws LikeNotFoundException;

     /**
      * Deletes all likes associated with a specific user.
      * <p>
      * This method removes all likes that belong to a given user ID.
      * </p>
      *
      * @param userId the ID of the user for which all likes will be deleted
      * @throws LikeNotFoundException if no likes are found for the specified user
      */
     void deleteAllByUserId(Long userId) throws LikeNotFoundException;

     /**
      * Adds a like for a specific post.
      * <p>
      * This method adds a like for the given post based on the information in the {@link CreateLikePost} DTO.
      * </p>
      *
      * @param createLikePost the {@link CreateLikePost} object containing the user and post information
      */
     void postLike(CreateLikePost createLikePost);

     /**
      * Adds a like for a specific comment.
      * <p>
      * This method adds a like for the given comment based on the information in the {@link CreateLikeComment} DTO.
      * </p>
      *
      * @param createLikeComment the {@link CreateLikeComment} object containing the user and comment information
      */
     void commentLike(CreateLikeComment createLikeComment);

     /**
      * Deletes a specific like by its ID.
      * <p>
      * This method removes a like with the specified like ID.
      * </p>
      *
      * @param likeId the ID of the like to be deleted
      * @throws LikeNotFoundException if no like is found for the specified like ID
      */
     void deleteLike(Long likeId) throws LikeNotFoundException;

     /**
      * Retrieves the total like sum for a specific post.
      * <p>
      * This method returns the cumulative like count for a post.
      * </p>
      *
      * @param postId the ID of the post for which the like sum will be retrieved
      * @return the total sum of likes for the specified post
      */
     Long showPostLikeSum(Long postId);

     /**
      * Retrieves the total like sum for a specific comment.
      * <p>
      * This method returns the cumulative like count for a comment.
      * </p>
      *
      * @param commentId the ID of the comment for which the like sum will be retrieved
      * @return the total sum of likes for the specified comment
      */
     Long showCommentLikeSum(Long commentId);
}
