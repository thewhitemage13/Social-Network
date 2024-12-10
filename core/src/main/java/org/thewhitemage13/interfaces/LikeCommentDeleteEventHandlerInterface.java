package org.thewhitemage13.interfaces;

import org.thewhitemage13.LikeEvent;

/**
 * Interface defining a handler for like comment deletion events.
 * <p>
 * Implementations of this interface handle actions that need to occur
 * when a like on a comment is deleted. The method {@code likeCommentDeleted}
 * is invoked with the details of the like event.
 * </p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>React to like deletion events on comments in the system.</li>
 *     <li>Provide custom logic for handling workflows related to like deletion on comments.</li>
 * </ul>
 *
 * @see LikeEvent
 * @author Mukhammed Lolo
 * @version 1.0.0
 */
public interface LikeCommentDeleteEventHandlerInterface {

    /**
     * Handles the event triggered when a like is deleted from a comment.
     *
     * @param likeEvent the event containing details about the deleted like on the comment
     */
    void likeCommentDeleted(LikeEvent likeEvent);
}
